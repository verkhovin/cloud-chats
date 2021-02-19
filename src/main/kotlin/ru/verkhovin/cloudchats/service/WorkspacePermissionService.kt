package ru.verkhovin.cloudchats.service

import org.litote.kmongo.Id
import org.springframework.stereotype.Component
import ru.verkhovin.cloudchats.model.data.Account
import ru.verkhovin.cloudchats.model.dto.AccountDetails
import ru.verkhovin.cloudchats.repository.WorkspaceRepository

@Component
class WorkspacePermissionService(private val workspaceRepository: WorkspaceRepository) {
  fun hasAccessToWorkspace(workspaceId: String, accountDetails: AccountDetails): Boolean {
    val workspace = workspaceRepository.findById(workspaceId) ?: return false
    return workspace.owner.toString() == accountDetails.id
        || workspace.admins.map(Id<Account>::toString).contains(accountDetails.id)
  }
}
