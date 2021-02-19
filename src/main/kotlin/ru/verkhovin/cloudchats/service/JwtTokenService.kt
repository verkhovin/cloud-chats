package ru.verkhovin.cloudchats.service

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.stereotype.Service
import ru.verkhovin.cloudchats.exception.UnauthorizedException
import ru.verkhovin.cloudchats.model.dto.AccountAuthDetails
import ru.verkhovin.cloudchats.model.dto.AccountDetails
import ru.verkhovin.cloudchats.model.dto.TokenType
import ru.verkhovin.cloudchats.security.token.AUTHORITIES_CLAIMS
import ru.verkhovin.cloudchats.security.token.TYPE_CLAIMS

@Service
class JwtTokenService(
    private val jwtParser: JwtParser,
    private val accountSecurityService: AccountSecurityService
) {

  fun validateTokenAndExtractAccountDetails(token: String, expectedType: TokenType): AccountAuthDetails {
    try {
      val claims = jwtParser.parseClaimsJws(token).body
      if (claims[TYPE_CLAIMS] != expectedType.toString() || claims[AUTHORITIES_CLAIMS] == null) {
        throw UnauthorizedException("Bad token")
      }
      return AccountAuthDetails(
          accountSecurityService.getAccountDetailsById(claims.subject),
          claims["authorities"] as List<String>
      );
    } catch (e: ExpiredJwtException) {
      throw UnauthorizedException(e.message!!)
    } catch (e: UnsupportedJwtException) {
      throw UnauthorizedException(e.message!!)
    } catch (e: MalformedJwtException) {
      throw UnauthorizedException(e.message!!)
    }
  }
}