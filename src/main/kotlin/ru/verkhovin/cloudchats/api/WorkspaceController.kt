package ru.verkhovin.cloudchats.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.verkhovin.cloudchats.model.dto.WorkspaceSaveDto
import ru.verkhovin.cloudchats.security.account.AccountPrincipal
import ru.verkhovin.cloudchats.security.permission.WorkspaceAdmin
import ru.verkhovin.cloudchats.service.WorkspaceService

@RestController
@RequestMapping("/workspaces")
//@Secured(ACCOUNT_ROLE)
class WorkspaceController(
    private val workspaceService: WorkspaceService
) {
  @PostMapping
  fun createWorkspace(@RequestBody workspaceSaveDto: WorkspaceSaveDto): ResponseEntity<Any> {
    val workspaceId = workspaceService.createWorkspace(workspaceSaveDto, AccountPrincipal.details)
    return ResponseEntity.created(locationHeader("/workspaces/$workspaceId")).build()
  }

  @GetMapping
  //TODO pagination
  fun getWorkspaces() = workspaceService.getWorkspaces(AccountPrincipal.details)

  @GetMapping("/{id}")
  @WorkspaceAdmin
  fun getWorkspace(@PathVariable id: String) = workspaceService.getWorkspace(id)

  @PutMapping("/{id}")
  @WorkspaceAdmin
  fun updateWorkspace(@PathVariable id: String, @RequestBody workspaceSaveDto: WorkspaceSaveDto) {
    workspaceService.updateWorkspace(id, workspaceSaveDto)
  }
}
