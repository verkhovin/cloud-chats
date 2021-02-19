package ru.verkhovin.cloudchats.model.dto

data class TokenPair(
    val accessToken: Token,
    val refreshToken: Token
)

data class Token(
    val value: String,
    val expiresIn: Int
)

enum class TokenType {
  ACCESS, REFRESH
}