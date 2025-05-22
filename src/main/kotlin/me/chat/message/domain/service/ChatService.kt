package me.chat.message.domain.service

import me.chat.message.api.request.ChatRequest
import me.chat.message.domain.entity.ChatMessage
import me.chat.message.infrastructure.repository.ChatMessageRepository
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
