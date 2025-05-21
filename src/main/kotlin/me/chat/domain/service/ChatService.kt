package me.chat.domain.service

import me.chat.api.request.ChatRequest
import me.chat.domain.entity.ChatMessage
import me.chat.infrastructure.repository.ChatMessageRepository
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatMessageRepository: ChatMessageRepository
) {

    fun addMessage(request: ChatRequest) {
        val chatMessage = request.toChatMessage()
        chatMessageRepository.insert(chatMessage)
    }

    fun getAllMessages(): List<ChatMessage> {
        return chatMessageRepository.findAll()
    }
}
