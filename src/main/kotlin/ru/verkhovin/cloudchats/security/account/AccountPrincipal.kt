package ru.verkhovin.cloudchats.security.account

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import ru.verkhovin.cloudchats.exception.UnauthorizedException
import ru.verkhovin.cloudchats.model.data.ACCOUNT_ROLE
import ru.verkhovin.cloudchats.model.data.Account
import ru.verkhovin.cloudchats.model.dto.AccountDetails


class AccountPrincipal(
    val id: String,
    val email: String,
    private val authorities: MutableCollection<out GrantedAuthority>
) : UserDetails {
  val details: AccountDetails = AccountDetails(id, email)

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

  override fun getPassword(): String = throw NotImplementedError()

  override fun getUsername(): String = email

  override fun isAccountNonExpired() = true

  override fun isAccountNonLocked() = true

  override fun isCredentialsNonExpired() = true

  override fun isEnabled(): Boolean = true

  companion object {
    val details
      get() = current().details

    fun create(accountDetails: AccountDetails, authorities: Collection<out GrantedAuthority>): AccountPrincipal {
      return AccountPrincipal(
          accountDetails.id,
          accountDetails.email,
          authorities.toMutableList()
      )
    }

    private fun current(): AccountPrincipal = SecurityContextHolder.getContext().authentication?.principal
        as AccountPrincipal? ?: throw UnauthorizedException("Bad authorization")

    fun hasCurrent() = SecurityContextHolder.getContext().authentication.principal != "anonymousUser"

  }
}
