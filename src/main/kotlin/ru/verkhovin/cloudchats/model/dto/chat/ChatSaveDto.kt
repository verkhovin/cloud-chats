package ru.verkhovin.cloudchats.model.dto.chat

data class
ChatSaveDto(
  val name: String,
  val tags: Set<String>?
)
