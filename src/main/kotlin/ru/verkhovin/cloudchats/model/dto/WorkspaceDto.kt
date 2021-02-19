package ru.verkhovin.cloudchats.model.dto

data class WorkspaceDto(
  val id: String,
  val name: String,
  val clientId: String,
  val applications: List<ApplicationDto>?
)
