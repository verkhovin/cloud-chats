package ru.verkhovin.cloudchats.model.dto.chat

data class ChatDto(
  val name: String,
  val tags: List<String>,
  val users: List<String>
)
