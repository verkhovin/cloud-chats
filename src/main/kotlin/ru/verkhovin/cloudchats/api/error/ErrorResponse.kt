package ru.verkhovin.cloudchats.api.error

data class ErrorResponse(
  val message: String,
  val code: ErrorCode
)

enum class ErrorCode {
  NOT_FOUND
}
