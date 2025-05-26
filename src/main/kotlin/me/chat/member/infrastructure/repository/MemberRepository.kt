package me.chat.member.infrastructure.repository

import me.chat.member.domain.entity.Member
import org.springframework.data.mongodb.repository.MongoRepository

interface MemberRepository : MongoRepository<Member, String> {
    fun findByName(name: String): Member?
    fun existsByName(name: String): Boolean
}

