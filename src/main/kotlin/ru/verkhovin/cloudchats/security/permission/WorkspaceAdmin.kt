package ru.verkhovin.cloudchats.security.permission

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import ru.verkhovin.cloudchats.exception.EntityNotFoundException
import ru.verkhovin.cloudchats.repository.ChatRepository
import ru.verkhovin.cloudchats.repository.getById
import ru.verkhovin.cloudchats.security.account.AccountPrincipal
import ru.verkhovin.cloudchats.service.WorkspacePermissionService

@Target(AnnotationTarget.FUNCTION)
annotation class WorkspaceAdmin(
  val requestedEntity: RequestedEntityType = RequestedEntityType.WORKSPACE,
  val idParameterName: String = "id"
)

enum class RequestedEntityType {
  WORKSPACE, CHAT
}

@Aspect
@Component
class SecurityChecks(private val chatRepository: ChatRepository, private val workspacePermissionService: WorkspacePermissionService) {

  @Around("@annotation(workspaceAdminAnnotation)")
  fun workspaceAdmin(joinPoint: ProceedingJoinPoint, workspaceAdminAnnotation: WorkspaceAdmin): Any? {
    val requestedEntityType = workspaceAdminAnnotation.requestedEntity
    val entityId = extractEntityId(joinPoint, workspaceAdminAnnotation.idParameterName)

    val workspaceId = when (requestedEntityType) {
      RequestedEntityType.WORKSPACE -> entityId //entityId is workspaceId
      RequestedEntityType.CHAT -> getWorkspaceIdFromChat(entityId) //entityId is chatId
    }

    if (!hasPermissionToWorkspace(workspaceId)) {
      throw EntityNotFoundException(requestedEntityType.name, "id", entityId)
    }

    return joinPoint.proceed()
  }

  private fun getWorkspaceIdFromChat(chatId: String): String {
    return chatRepository.getById(chatId).workspaceId.toString()
  }

  private fun hasPermissionToWorkspace(workspaceId: String): Boolean {
    val details = AccountPrincipal.details
    return workspacePermissionService.hasAccessToWorkspace(workspaceId, details)
  }

  private fun extractEntityId(joinPoint: ProceedingJoinPoint, fieldName: String): String {
    val signature = joinPoint.signature as MethodSignature
    val idParamIndex = listOf(*signature.parameterNames).indexOf(fieldName)
    val args = joinPoint.args
    return if (idParamIndex > args.size - 1) {
      throw RuntimeException(String.format("Parameter %s didn't found for method %s",
          fieldName, signature.name))
    } else args[idParamIndex] as String
  }
}