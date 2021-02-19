package ru.verkhovin.cloudchats.model.data

import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class User(
  val _id: Id<User> = newId(),
  val workspaceId: Id<Workspace>,
  val internalId: String,
  var name: String,
  var imageUrl: String,
)
