package ru.verkhovin.cloudchats.service

import org.springframework.stereotype.Service
import ru.verkhovin.cloudchats.configuration.PasswordHashValidator
import ru.verkhovin.cloudchats.exception.EntityNotFoundException
import ru.verkhovin.cloudchats.model.data.Account
import ru.verkhovin.cloudchats.model.dto.AccountDetails
import ru.verkhovin.cloudchats.repository.AccountRepository
import ru.verkhovin.cloudchats.repository.getById

@Service
class AccountSecurityService(
  private val accountRepository: AccountRepository,
  private val validatePassword: PasswordHashValidator
) {
  fun validateAccount(email: String, password: String) = try {
    validatePassword(password, accountRepository.findByEmail(email).passwordHash)
  } catch (e: EntityNotFoundException) {
    false
  }

  fun getAccountDetails(email: String): AccountDetails = accountRepository.findByEmail(email).toAccountDetails()

  fun getAccountDetailsById(id: String): AccountDetails = accountRepository.getById(id).toAccountDetails()

  fun getDetailsByRefreshToken(token: String): AccountDetails = accountRepository.findByRefreshToken(token).toAccountDetails()

  fun updateRefreshToken(email: String, token: String) {
    val account = accountRepository.findByEmail(email)
    account.refreshToken = token
    accountRepository.save(account)
  }

  private fun Account.toAccountDetails() = AccountDetails(id, email)
}
