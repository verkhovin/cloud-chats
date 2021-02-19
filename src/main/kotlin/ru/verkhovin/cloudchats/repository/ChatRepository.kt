package ru.verkhovin.cloudchats.repository

import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.id.WrappedObjectId
import org.springframework.stereotype.Component
import ru.verkhovin.cloudchats.model.data.Chat

@Component
class ChatRepository(
  chatCollection: MongoCollection<Chat>
) : Repository<Chat>(chatCollection) {
  fun getListByWorkspaceId(workspaceId: String): List<Chat> =
    collection.find(Chat::workspaceId eq WrappedObjectId(workspaceId)).toList()
}
