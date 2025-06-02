package me.chat.message.infrastructure.repository

import me.chat.message.domain.entity.ChatMessage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatMessageRepository : MongoRepository<ChatMessage, String> {
    fun findByMemberIdOrderByIdDesc(memberId: String, pageable: Pageable): Page<ChatMessage>
    fun findByMemberIdAndIdLessThanOrderByIdDesc(commonMember: String, id: String, ofSize: Pageable): Page<ChatMessage>
}
