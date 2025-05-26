package me.chat.user.infrastructure.repository

import me.chat.user.domain.entity.Member
import org.springframework.data.mongodb.repository.MongoRepository

interface MemberRepository : MongoRepository<Member, String> {
    fun findByName(name: String): Member?
    fun existsByName(name: String): Boolean
}

