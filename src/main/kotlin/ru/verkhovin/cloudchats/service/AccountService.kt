package ru.verkhovin.cloudchats.service

import org.springframework.stereotype.Service
import ru.verkhovin.cloudchats.configuration.PasswordHashGenerator
import ru.verkhovin.cloudchats.model.data.Account
import ru.verkhovin.cloudchats.model.dto.AccountRegisterRequest
import ru.verkhovin.cloudchats.repository.AccountRepository

@Service
class AccountService(
  private val accountRepository: AccountRepository,
  private val hashPassword: PasswordHashGenerator,
) {
  fun register(accountRegisterRequest: AccountRegisterRequest) = accountRepository.save(
    Account(
      accountRegisterRequest.email,
      hashPassword(accountRegisterRequest.password),
    )
  )
}
