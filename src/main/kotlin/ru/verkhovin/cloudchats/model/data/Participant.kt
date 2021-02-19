package ru.verkhovin.cloudchats.model.data

import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class Participant (
  val _id: Id<Participant> = newId(),
  val externalId: String,
  val workspace: Id<Workspace>,
  var username: String,
  var displayName: String,
)