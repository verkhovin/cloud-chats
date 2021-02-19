package ru.verkhovin.cloudchats.model.dto

import ru.verkhovin.cloudchats.model.data.Application

data class ApplicationDto(
  val name: String,
  var id: String?
)

fun List<Application>.dtos() = this.asSequence()
  .map { ApplicationDto(it.name, it.id) }
  .toList()

