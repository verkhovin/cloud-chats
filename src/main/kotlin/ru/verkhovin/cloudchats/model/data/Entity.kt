package ru.verkhovin.cloudchats.model.data

import org.litote.kmongo.Id
import org.litote.kmongo.newId

abstract class Entity<T>(
  val _id: Id<T> = newId()
) {
  val id
    get() = _id.toString()
}
