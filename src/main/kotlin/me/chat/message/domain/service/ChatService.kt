package me.chat.message.domain.service

import me.chat.message.api.CursorPage
import me.chat.message.api.request.ChatRequest
import me.chat.message.domain.entity.ChatMessage
import me.chat.message.infrastructure.repository.ChatMessageRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class ChatService(
    private val chatMessageRepository: ChatMessageRepository
) {

    fun addMyMessage(request: ChatRequest, userId: String) {
        val chatMessage = request.toChatMessage(userId)
        chatMessageRepository.insert(chatMessage)
    }

    fun addAnyMessage(request: ChatRequest) {
        val chatMessage = request.toChatMessage(COMMON_MEMBER)
        chatMessageRepository.insert(chatMessage)
    }

    @Transactional(readOnly = true)
    fun getAnyMessages(cursor: String?): CursorPage {
        val page: Page<ChatMessage> = cursor?.let {
            chatMessageRepository.findByMemberIdAndIdLessThanOrderByIdDesc(
                COMMON_MEMBER,
                it,
                DEFAULT_PAGE
            )
        } ?: run {
            chatMessageRepository.findByMemberIdOrderByIdDesc(
                COMMON_MEMBER,
                DEFAULT_PAGE
            )
        }

        return CursorPage(page)
    }

    @Transactional(readOnly = true)
    fun getMyMessages(memberId: String, cursor: String?): CursorPage {
        val page: Page<ChatMessage> = cursor?.let {
            chatMessageRepository.findByMemberIdAndIdLessThanOrderByIdDesc(
                memberId,
                it,
                DEFAULT_PAGE
            )
        } ?: run {
            chatMessageRepository.findByMemberIdOrderByIdDesc(
                memberId,
                DEFAULT_PAGE
            )
        }

        return CursorPage(page)
    }

    companion object {
        private const val COMMON_MEMBER: String = "whoever"
        private val DEFAULT_PAGE: Pageable = Pageable.ofSize(30)
    }
}
