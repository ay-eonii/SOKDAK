package me.chat.infrastructure.repository

import me.chat.domain.entity.ChatMessage
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatMessageRepository : MongoRepository<ChatMessage, String> {
}
