package me.chat.message.domain.service

import me.chat.message.api.request.ChatRequest
import me.chat.message.domain.entity.ChatMessage
import me.chat.message.infrastructure.repository.ChatMessageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChatService(
    private val chatMessageRepository: ChatMessageRepository
) {

    fun addMyMessage(request: ChatRequest, userId: String) {
        val chatMessage = request.toChatMessage(userId)
        chatMessageRepository.insert(chatMessage)
    }

    fun addAnyMessage(request: ChatRequest) {
        val chatMessage = request.toChatMessage(COMMON_MEMBER)
        chatMessageRepository.insert(chatMessage)
    }

    @Transactional(readOnly = true)
    fun getAnyMessages(): List<ChatMessage> {
        return chatMessageRepository.findByMemberId(COMMON_MEMBER)
    }

    @Transactional(readOnly = true)
    fun getMyMessages(memberId: String): List<ChatMessage> {
        return chatMessageRepository.findByMemberId(memberId)
    }

    companion object {
        private const val COMMON_MEMBER: String = "whoever"
    }
}
