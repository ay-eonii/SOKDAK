package me.chat.message.api.controller

import me.chat.message.api.request.ChatRequest
import me.chat.message.domain.service.ChatService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(private val chatService: ChatService) {

    @PostMapping("/chat")
    fun sendMessage(
        @RequestBody request: ChatRequest
    ) {
        chatService.addMessage(request)
    }
}
