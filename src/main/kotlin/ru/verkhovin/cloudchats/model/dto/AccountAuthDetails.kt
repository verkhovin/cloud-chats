package ru.verkhovin.cloudchats.model.dto

data class AccountAuthDetails(
    val accountDetails: AccountDetails,
    val authorities: List<String>
)
