package me.chat.user.infrastructure.repository

import me.chat.user.domain.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findByName(name: String): User?
}

