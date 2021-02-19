package ru.verkhovin.cloudchats.model.data

import org.litote.kmongo.Id

data class Chat(
  var name: String,
  val workspaceId: Id<Workspace>,
  var tags: Set<String>,
  val users: Set<Id<User>> = emptySet()
) : Entity<Chat>()
