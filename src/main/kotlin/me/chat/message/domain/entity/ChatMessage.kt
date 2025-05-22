package me.chat.message.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneId

@Document(collection = "chat")
data class ChatMessage(
    @Id
    val id: String? = null,
    val content: String,
    val sender: String,
    val timestamp: LocalDateTime = LocalDateTime.now(ASIA_SEOUL)
) {
    companion object {
        private val ASIA_SEOUL: ZoneId = ZoneId.of("Asia/Seoul")
    }
}
