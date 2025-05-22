package me.chat.message.api.controller

import me.chat.message.domain.service.ChatService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController(private val chatService: ChatService) {

    @GetMapping("/")
    fun chatPage(): String {
        return "chat"
    }

    @GetMapping("/chat")
    fun getChatPage(model: Model): String {
        model.addAttribute("messages", chatService.getAllMessages())
        return "chat"
    }
}
