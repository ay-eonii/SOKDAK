package me.chat.common.security

import me.chat.user.infrastructure.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = memberRepository.findByName(username)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다: $username")

        return User(
            user.name,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        )
    }
}
