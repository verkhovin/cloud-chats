package ru.verkhovin.cloudchats.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.verkhovin.cloudchats.model.dto.chat.ChatSaveDto
import ru.verkhovin.cloudchats.security.permission.RequestedEntityType.CHAT
import ru.verkhovin.cloudchats.security.permission.WorkspaceAdmin
import ru.verkhovin.cloudchats.service.ChatService

@RestController
@RequestMapping("/chats")
class ChatController(private val chatService: ChatService) {
  @PostMapping
  @WorkspaceAdmin(idParameterName = "workspaceId")
  fun createChat(@RequestBody chatSaveDto: ChatSaveDto, @RequestParam workspaceId: String): ResponseEntity<Any> {
    val id = chatService.createChat(chatSaveDto, workspaceId)
    return ResponseEntity.created(locationHeader("/chats/$id")).build();
  }

  @GetMapping
  @WorkspaceAdmin(idParameterName = "workspaceId")
  //TODO Pagination
  fun getWorkspaceChats(@RequestParam workspaceId: String) = chatService.getWorkspaceChats(workspaceId)

  @GetMapping("/{id}")
  @WorkspaceAdmin(CHAT)
  fun getChat(@PathVariable id: String) = chatService.getChat(id)

  @PutMapping("/{id}")
  @WorkspaceAdmin(CHAT)
  fun updateChat(@PathVariable id: String, @RequestBody chatSaveDto: ChatSaveDto) = chatService.updateChat(id, chatSaveDto)


}
