package ru.verkhovin.cloudchats.exception

import org.springframework.http.HttpStatus
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class EntityNotFoundException(entityName: String, val field: String, val value: String) : WebMappedException(
    HttpStatus.NOT_FOUND,
    "Entity ${entityName.toLowerCase().capitalize()} wasn't found with $field = $value"
) {
  val entityName = entityName.toLowerCase().capitalize()
}

fun <T : Any> KClass<T>.notFoundException(field: KProperty1<T, String?>, value: String) =
    EntityNotFoundException(this.simpleName!!, field.name, value)
