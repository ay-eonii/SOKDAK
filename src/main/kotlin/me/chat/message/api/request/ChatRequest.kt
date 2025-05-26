package me.chat.message.api.request

import me.chat.message.domain.entity.ChatMessage

data class ChatRequest(
    val content: String,
    val sender: String
) {
    fun toChatMessage(memberId: String?): ChatMessage {
        return ChatMessage(
            memberId = memberId,
            content = this.content,
            sender = this.sender
        )
    }
}
