package ru.verkhovin.cloudchats.service

import org.litote.kmongo.Id
import org.litote.kmongo.id.WrappedObjectId
import org.springframework.stereotype.Service
import ru.verkhovin.cloudchats.model.data.Chat
import ru.verkhovin.cloudchats.model.data.User
import ru.verkhovin.cloudchats.model.dto.chat.ChatDto
import ru.verkhovin.cloudchats.model.dto.chat.ChatSaveDto
import ru.verkhovin.cloudchats.repository.ChatRepository
import ru.verkhovin.cloudchats.repository.getById

@Service
class ChatService(private val chatRepository: ChatRepository) {
  fun createChat(chatSaveDto: ChatSaveDto, workspaceId: String): String {
    val id = chatRepository.save(
      Chat(
        chatSaveDto.name,
        WrappedObjectId(workspaceId),
        chatSaveDto.tags ?: emptySet(),
      )
    )
    return id
  }

  fun updateChat(chatId: String, chatSaveDto: ChatSaveDto) {
    val chat = chatRepository.getById(chatId)
    chat.name = chatSaveDto.name
    chat.tags = chatSaveDto.tags ?: chat.tags
    chatRepository.save(chat)
  }

  fun getChat(chatId: String): ChatDto {
    val chat = chatRepository.getById(chatId)
    return toDto(chat)
  }

  fun getWorkspaceChats(workspaceId: String): List<ChatDto> = chatRepository.getListByWorkspaceId(workspaceId)
    .map(this::toDto)

  private fun toDto(chat: Chat) = ChatDto(
    chat.name,
    chat.tags.toList(),
    chat.users.asSequence().map(Id<User>::toString).toList()
  )
}
