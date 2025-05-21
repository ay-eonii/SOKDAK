package me.chat.api.request

import me.chat.domain.entity.ChatMessage

data class ChatRequest(
    val content: String,
    val sender: String
) {
    fun toChatMessage(): ChatMessage {
        return ChatMessage(
            content = this.content,
            sender = this.sender
        )
    }
}
