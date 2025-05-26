package me.chat.user.domain.service

import me.chat.common.jwt.AccessTokenProvider
import me.chat.user.api.request.AuthRequest
import me.chat.user.api.response.TokenResponse
import me.chat.user.domain.entity.User
import me.chat.user.infrastructure.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val accessTokenProvider: AccessTokenProvider
) {

    fun signUp(request: AuthRequest): TokenResponse {
        val password = passwordEncoder.encode(request.password)
        val user = User(
            name = request.name,
            password = password
        )
        userRepository.insert(user)

        val accessToken: String = accessTokenProvider.create(user)
        return TokenResponse(accessToken)
    }

    fun login(request: AuthRequest): TokenResponse {
        val user: User = userRepository.findByName(request.name) ?: throw IllegalArgumentException()
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException()
        }

        val accessToken: String = accessTokenProvider.create(user)
        return TokenResponse(accessToken)
    }

    fun getUser(name: String): User {
        return userRepository.findByName(name) ?: throw IllegalArgumentException()
    }
}
