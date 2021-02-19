package ru.verkhovin.cloudchats.configuration

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.mindrot.jbcrypt.BCrypt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.verkhovin.cloudchats.security.JWTAuthenticationFilter
import ru.verkhovin.cloudchats.service.JwtTokenService

typealias PasswordHashGenerator = (String) -> String
typealias PasswordHashValidator = (String, String) -> Boolean

@Configuration
class SecurityConfiguration {
  @Bean
  fun passwordHashGenerator(): PasswordHashGenerator = { BCrypt.hashpw(it, BCrypt.gensalt()) }

  @Bean
  fun passwordHashValidator(): PasswordHashValidator = { password, hash -> BCrypt.checkpw(password, hash) }

  @Bean
  fun jwtParser(securityParams: SecurityParams): JwtParser = Jwts.parserBuilder().setSigningKey(
      Keys.hmacShaKeyFor(securityParams.accessToken.secret.toByteArray())
  ).build()

  @Bean
  fun jwtAuthenticationFilter(jwtTokenService: JwtTokenService): JWTAuthenticationFilter {
    return JWTAuthenticationFilter(jwtTokenService)
  }
}