package me.chat.message.api.controller

import me.chat.message.api.CursorPage
import me.chat.message.domain.service.ChatService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class ChatViewController(
    private val chatService: ChatService
) {

    @GetMapping("/")
    fun chatPage(): String {
        return "index"
    }

    @GetMapping("/chat/any")
    fun getAnyChatPage(
        @RequestParam(required = false) cursor: String?,
        model: Model
    ): String {
        val page: CursorPage = chatService.getAnyMessages(cursor)
        model.addAttribute("messages", page.messages)
        model.addAttribute("nextCursor", page.nextCursor);
        return "chat/any"
    }

    @GetMapping("/chat/any/load")
    fun loadMoreMessages(
        @RequestParam cursor: String?,
        model: Model
    ): String {
        return populateChatModel(
            getPage = { chatService.getAnyMessages(cursor) },
            model = model,
            senderLabel = "익명"
        )
    }

    @GetMapping("/chat/me")
    fun getMyChatPage(
        @AuthenticationPrincipal user: User,
        @RequestParam(required = false) cursor: String?,
        model: Model,
    ): String {
        val page: CursorPage = chatService.getMyMessages(user.username, cursor)
        model.addAttribute("messages", page.messages)
        model.addAttribute("nextCursor", page.nextCursor);
        return "chat/me"
    }

    @GetMapping("/chat/me/load")
    fun loadMoreMyMessages(
        @AuthenticationPrincipal user: User,
        @RequestParam cursor: String?,
        model: Model
    ): String {
        return populateChatModel(
            getPage = { chatService.getMyMessages(user.username, cursor) },
            model = model,
            senderLabel = "나"
        )
    }

    private fun populateChatModel(
        getPage: () -> CursorPage,
        model: Model,
        senderLabel: String
    ): String {
        val page = getPage()
        model.addAttribute("messages", page.messages)
        model.addAttribute("nextCursor", page.nextCursor)
        model.addAttribute("senderLabel", senderLabel)
        return "fragments/message-list :: messageList"
    }
}
