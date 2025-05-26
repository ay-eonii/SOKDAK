package me.chat.member.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "member")
data class Member(
    @Id
    val id: String? = null,
    val name: String,
    val password: String
)
