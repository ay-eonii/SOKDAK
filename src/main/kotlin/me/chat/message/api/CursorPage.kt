package me.chat.message.api

import me.chat.message.domain.entity.ChatMessage
import org.springframework.data.domain.Page

data class CursorPage(
    val messages: List<ChatMessage>,
    val nextCursor: String? = null
) {
    constructor(page: Page<ChatMessage>) : this(
        messages = page.reversed(),
        nextCursor = page.lastOrNull()?.id
    )
}
