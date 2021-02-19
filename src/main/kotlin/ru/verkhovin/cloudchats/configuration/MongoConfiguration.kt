package ru.verkhovin.cloudchats.configuration

import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.verkhovin.cloudchats.model.data.Account
import ru.verkhovin.cloudchats.model.data.Chat
import ru.verkhovin.cloudchats.model.data.Workspace

@Configuration
class MongoConfiguration {
  @Bean
  fun mongoDatabase(
      @Value("\${mongodb.uri}") uri: String,
      @Value("\${mongodb.db-name}") dbName: String
  ): MongoDatabase = KMongo.createClient(uri)
      .getDatabase(dbName)

  @Bean
  fun accountCollection(mongoDatabase: MongoDatabase) =
      mongoDatabase.getCollection<Account>()

  @Bean
  fun workspaceCollection(mongoDatabase: MongoDatabase) =
      mongoDatabase.getCollection<Workspace>()

  @Bean
  fun chatCollection(mongoDatabase: MongoDatabase) = mongoDatabase.getCollection<Chat>()
}
