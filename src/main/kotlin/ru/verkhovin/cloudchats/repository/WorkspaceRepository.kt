package ru.verkhovin.cloudchats.repository

import com.mongodb.client.MongoCollection
import org.litote.kmongo.contains
import org.litote.kmongo.eq
import org.litote.kmongo.id.WrappedObjectId
import org.litote.kmongo.or
import org.springframework.stereotype.Component
import ru.verkhovin.cloudchats.model.data.Account
import ru.verkhovin.cloudchats.model.data.Workspace
import ru.verkhovin.cloudchats.model.dto.AccountDetails

@Component
class WorkspaceRepository(
  workspaceCollection: MongoCollection<Workspace>
) : Repository<Workspace>(workspaceCollection) {
  fun getListByAccount(accountDetails: AccountDetails): List<Workspace> {
    val id = WrappedObjectId<Account>(accountDetails.id)
    return collection.find(
      or(
        Workspace::owner eq id,
        Workspace::admins contains id
      )
    ).toList()
  }
}
