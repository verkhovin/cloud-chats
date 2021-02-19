package ru.verkhovin.cloudchats.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration
import ru.verkhovin.cloudchats.model.dto.TokenType

@Configuration
@ConfigurationProperties("security")
class SecurityParams {

  var accessToken: TokenParams = TokenParams()
  var refreshToken: TokenParams = TokenParams()

  class TokenParams {
    lateinit var secret: String
    var expiresIn: Int = 0
  }

  fun getTokenParams(tokenType: TokenType) = when(tokenType) {
    TokenType.ACCESS -> accessToken;
    TokenType.REFRESH -> refreshToken;
  }
}

