package ru.verkhovin.cloudchats.api

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.noContent
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.verkhovin.cloudchats.exception.UnauthorizedException
import ru.verkhovin.cloudchats.model.data.ACCOUNT_ROLE
import ru.verkhovin.cloudchats.model.dto.*
import ru.verkhovin.cloudchats.security.token.TokenGenerator
import ru.verkhovin.cloudchats.service.AccountSecurityService
import ru.verkhovin.cloudchats.service.AccountService
import ru.verkhovin.cloudchats.service.JwtTokenService

@RestController
@RequestMapping("/account")
class AccountController(private val accountService: AccountService,
                        private val accountSecurityService: AccountSecurityService,
                        private val tokenGenerator: TokenGenerator,
                        private val jwtTokenService: JwtTokenService) {
  @PostMapping("/register")
  fun registerAccount(@RequestBody accountRegisterRequest: AccountRegisterRequest): ResponseEntity<Any> {
    accountService.register(accountRegisterRequest)
    return noContent().build()
  }

  @PostMapping("/login")
  fun login(@RequestBody accountLoginRequest: AccountLoginRequest): TokenPair {
    if (accountSecurityService.validateAccount(accountLoginRequest.username, accountLoginRequest.password)) {
      val accountDetails = accountSecurityService.getAccountDetails(accountLoginRequest.username)
      return buildTokenPair(accountDetails)
    }
    throw UnauthorizedException("Bad login")
  }

  @PostMapping("/token/refresh")
  fun refreshTokens(@RequestBody tokenRefreshRequest: TokenRefreshRequest): TokenPair {
    val authDetails = jwtTokenService.validateTokenAndExtractAccountDetails(tokenRefreshRequest.token, TokenType.REFRESH)
    return buildTokenPair(authDetails.accountDetails)
  }

  private fun buildTokenPair(accountDetails: AccountDetails): TokenPair {
    val authorities = listOf(SimpleGrantedAuthority(ACCOUNT_ROLE))
    return tokenGenerator.buildTokenPair(accountDetails, authorities)
  }

}
