package me.chat.message.infrastructure.repository

import me.chat.message.domain.entity.ChatMessage
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatMessageRepository : MongoRepository<ChatMessage, String> {
    fun findByMemberId(memberId: String): MutableList<ChatMessage>
}
