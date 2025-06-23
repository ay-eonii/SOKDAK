package me.chat.member.infrastructure.repository

import me.chat.common.oauth.userinfo.SocialProvider
import me.chat.member.domain.entity.Member
import org.springframework.data.mongodb.repository.MongoRepository

interface MemberRepository : MongoRepository<Member, String> {
    fun findByProviderAndProviderId(provider: SocialProvider, providerId: String): Member?
}

