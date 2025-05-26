package me.chat.member.domain.service

import me.chat.member.api.request.SignUpForm
import me.chat.member.domain.entity.Member
import me.chat.member.infrastructure.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun signUp(request: SignUpForm): Boolean {
        if (memberRepository.existsByName(request.username)) {
            return false
        }

        val password = passwordEncoder.encode(request.password)
        val member = Member(
            name = request.username,
            password = password
        )
        memberRepository.insert(member)
        return true
    }
}
