package ru.verkhovin.cloudchats.security

import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import ru.verkhovin.cloudchats.exception.UnauthorizedException
import ru.verkhovin.cloudchats.model.dto.AccountAuthDetails
import ru.verkhovin.cloudchats.model.dto.TokenType
import ru.verkhovin.cloudchats.security.account.AccountPrincipal
import ru.verkhovin.cloudchats.security.token.AUTHORITIES_CLAIMS
import ru.verkhovin.cloudchats.security.token.TYPE_CLAIMS
import ru.verkhovin.cloudchats.service.AccountSecurityService
import ru.verkhovin.cloudchats.service.JwtTokenService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


const val AUTHORIZATION_HEADER_NAME = "Authorization"
const val TOKEN_PREFIX = "Bearer "

class JWTAuthenticationFilter(
    private val jwtTokenService: JwtTokenService,
) : OncePerRequestFilter() {


  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
    try {
      request.getHeader(AUTHORIZATION_HEADER_NAME)
          ?.replace(TOKEN_PREFIX, "")?.let { token ->
            val accountAuthDetails = jwtTokenService.validateTokenAndExtractAccountDetails(token, TokenType.ACCESS)
            authenticate(accountAuthDetails)
          }
    } catch (e: ExpiredJwtException) {
      throw UnauthorizedException(e.message!!)
    } catch (e: UnsupportedJwtException) {
      throw UnauthorizedException(e.message!!)
    } catch (e: MalformedJwtException) {
      throw UnauthorizedException(e.message!!)
    }
    chain.doFilter(request, response);
  }

  private fun authenticate(accountAuthDetails: AccountAuthDetails) {
    val principal = AccountPrincipal.create(accountAuthDetails.accountDetails,
        accountAuthDetails.authorities.map { role: String -> SimpleGrantedAuthority(role) })
    val auth = UsernamePasswordAuthenticationToken(
        principal,
        null,
        principal.authorities
    )
    SecurityContextHolder.getContext().authentication = auth
  }


}