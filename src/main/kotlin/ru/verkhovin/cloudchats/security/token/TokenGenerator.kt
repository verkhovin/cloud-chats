package ru.verkhovin.cloudchats.security.token

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import ru.verkhovin.cloudchats.configuration.SecurityParams
import ru.verkhovin.cloudchats.model.dto.AccountDetails
import ru.verkhovin.cloudchats.model.dto.Token
import ru.verkhovin.cloudchats.model.dto.TokenPair
import ru.verkhovin.cloudchats.model.dto.TokenType
import java.util.*
import java.util.stream.Collectors

const val AUTHORITIES_CLAIMS = "authorities"
const val TYPE_CLAIMS = "type"

@Component
class TokenGenerator(private val securityParams: SecurityParams) {
  fun buildTokenPair(accountDetails: AccountDetails, grantedAuthorities: Collection<GrantedAuthority>): TokenPair {
    return TokenPair(
      buildToken(TokenType.ACCESS, accountDetails, grantedAuthorities),
      buildToken(TokenType.REFRESH, accountDetails, grantedAuthorities)
    )
  }

  private fun buildToken(type: TokenType, accountDetails: AccountDetails, grantedAuthorities: Collection<GrantedAuthority>) = Token(
      buildTokenValue(accountDetails, grantedAuthorities, type),
      securityParams.getTokenParams(type).expiresIn
  )

  private fun buildTokenValue(accountDetails: AccountDetails, grantedAuthorities: Collection<GrantedAuthority>, type: TokenType) =
      Jwts.builder()
          .setSubject(accountDetails.id)
          .claim(AUTHORITIES_CLAIMS,
              grantedAuthorities.stream()
                  .map(GrantedAuthority::getAuthority)
                  .collect(Collectors.toList()))
          .claim(TYPE_CLAIMS, type.toString())
          .setIssuedAt(Date(System.currentTimeMillis()))
          .setExpiration(Date(System.currentTimeMillis() + securityParams.getTokenParams(type).expiresIn * 1000))
          .signWith(Keys.hmacShaKeyFor(
              securityParams.getTokenParams(type).secret.toByteArray()
          )).compact()
}