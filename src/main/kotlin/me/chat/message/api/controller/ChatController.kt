package me.chat.message.api.controller

import me.chat.message.api.request.ChatRequest
import me.chat.message.domain.service.ChatService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(private val chatService: ChatService) {

    @PostMapping("/chat/me")
    fun sendMessage(
        @RequestBody request: ChatRequest,
        @AuthenticationPrincipal user: User,
    ) {
        chatService.addMyMessage(request, user.username)
    }

    @PostMapping("/chat/any")
    fun sendMessage(
        @RequestBody request: ChatRequest
    ) {
        chatService.addAnyMessage(request)
    }
}
