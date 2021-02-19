package ru.verkhovin.cloudchats.model.data

data class Account(
  val email: String,
  val passwordHash: String,
  var refreshToken: String? = null
) : Entity<Account>()

const val ACCOUNT_ROLE = "ACCOUNT"
