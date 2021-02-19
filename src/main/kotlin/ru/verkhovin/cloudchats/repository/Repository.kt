package ru.verkhovin.cloudchats.repository

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.ReplaceOptions
import org.litote.kmongo.eq
import org.litote.kmongo.findOneById
import org.litote.kmongo.id.WrappedObjectId
import ru.verkhovin.cloudchats.exception.EntityNotFoundException
import ru.verkhovin.cloudchats.model.data.Entity

abstract class Repository<T : Entity<T>>(protected val collection: MongoCollection<T>) {
  fun save(entity: T) = collection.save(entity).let {
    entity._id.toString()
  }

  fun findById(id: String) = collection.findOneById(WrappedObjectId<T>(id))
}

fun <T : Entity<*>> MongoCollection<T>.save(entity: T) =
  this.replaceOne(Entity<T>::_id eq entity._id, entity, ReplaceOptions().upsert(true))

inline fun <reified T : Entity<T>> Repository<T>.getById(id: String): T =
  this.findById(id) ?: throw EntityNotFoundException(T::class.simpleName!!, "id", id)

