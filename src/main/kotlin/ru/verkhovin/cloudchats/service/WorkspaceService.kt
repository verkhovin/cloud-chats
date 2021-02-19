package ru.verkhovin.cloudchats.service

import org.litote.kmongo.id.WrappedObjectId
import org.springframework.stereotype.Component
import ru.verkhovin.cloudchats.model.data.Application
import ru.verkhovin.cloudchats.model.data.Workspace
import ru.verkhovin.cloudchats.model.dto.*
import ru.verkhovin.cloudchats.repository.WorkspaceRepository
import ru.verkhovin.cloudchats.repository.getById
import java.util.*

@Component
class WorkspaceService(private val workspaceRepository: WorkspaceRepository) {
  fun createWorkspace(workspaceSaveDto: WorkspaceSaveDto, account: AccountDetails) = workspaceRepository.save(
    Workspace(
      applications(workspaceSaveDto.applications),
      workspaceSaveDto.clientId,
      workspaceSaveDto.clientSecret,
      workspaceSaveDto.name,
      WrappedObjectId(account.id)
    )
  )

  fun updateWorkspace(id: String, workspaceSaveDto: WorkspaceSaveDto) {
    workspaceRepository.getById(id).also { workspace ->
      workspace.name = workspaceSaveDto.name
      workspace.applications = applications(workspaceSaveDto.applications)
      workspace.clientId = workspaceSaveDto.clientId
      workspace.clientSecret = workspaceSaveDto.clientSecret
      workspaceRepository.save(workspace)
    }
  }

  fun getWorkspace(id: String): WorkspaceDto = workspaceDto(workspaceRepository.getById(id))

  fun getWorkspaces(accountDetails: AccountDetails) = workspaceRepository.getListByAccount(accountDetails)
    .map(this::workspaceDto)


  private fun workspaceDto(workspace: Workspace) = with(workspace) {
    WorkspaceDto(id, name, clientId, applications.dtos())
  }

  private fun applications(applications: List<ApplicationDto>?) = applications?.asSequence()
    ?.map { dto -> Application(dto.name, dto.id ?: UUID.randomUUID().toString()) }
    ?.toList()
    ?: emptyList() //TODO map { ... } -> extension method?
}
