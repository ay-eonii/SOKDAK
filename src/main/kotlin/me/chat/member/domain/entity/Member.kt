package me.chat.member.domain.entity

import me.chat.common.oauth.userinfo.SocialProvider
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "member")
data class Member(
    @Id
    val id: String? = null,
    val name: String,
    val email: String,
    val provider: SocialProvider,
    val providerId: String,
)
