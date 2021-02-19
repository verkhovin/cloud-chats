package ru.verkhovin.cloudchats.model.data

import org.litote.kmongo.Id

data class Workspace(
  var applications: List<Application>,
  var clientId: String,
  var clientSecret: String,
  var name: String,
  var owner: Id<Account>,
  var admins: List<Id<Account>> = listOf(owner)
) : Entity<Workspace>()
