package ru.verkhovin.cloudchats.model.dto

data class AccountRegisterRequest(
  val email: String,
  val password: String
)
