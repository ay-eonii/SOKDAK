package me.chat.api.controller

import me.chat.api.request.ChatRequest
import me.chat.domain.service.ChatService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class ChatController(private val chatService: ChatService) {

    @GetMapping("/chat")
    fun getChatPage(model: Model): String {
        model.addAttribute("messages", chatService.getAllMessages())
        return "chat"
    }

    @PostMapping("/chat")
    fun sendMessage(
        @RequestBody request: ChatRequest,
    ) {
        chatService.addMessage(request)
    }
}
