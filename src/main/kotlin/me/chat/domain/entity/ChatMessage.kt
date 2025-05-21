package me.chat.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chat")
data class ChatMessage(
    @Id
    val id: String? = null,
    val content: String,
    val sender: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
