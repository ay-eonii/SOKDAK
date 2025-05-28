package me.chat.message.api.controller

import me.chat.message.domain.service.ChatService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ChatViewController(
    private val chatService: ChatService
) {

    @GetMapping("/")
    fun chatPage(): String {
        return "index"
    }

    @GetMapping("/chat/any")
    fun getAnyChatPage(model: Model): String {
        model.addAttribute("messages", chatService.getAnyMessages())
        return "chat/any"
    }

    @GetMapping("/chat/me")
    fun getMyChatPage(
        @AuthenticationPrincipal user: User,
        model: Model
    ): String {
        val messages = chatService.getMyMessages(user.username)
        model.addAttribute("messages", messages)

        return "chat/me"
    }
}
