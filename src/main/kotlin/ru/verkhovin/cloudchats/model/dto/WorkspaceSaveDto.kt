package ru.verkhovin.cloudchats.model.dto

data class WorkspaceSaveDto(
  val name: String,
  val clientId: String,
  var clientSecret: String,
  val applications: List<ApplicationDto>?
)


