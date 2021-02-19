package ru.verkhovin.cloudchats.model.data

import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.ZonedDateTime

data class Message(
  val _id: Id<Message> = newId(),
  val chatId: Id<Chat>,
  val authorUserId: Id<User>,
  val text: String,
  val sentAt: ZonedDateTime
)
