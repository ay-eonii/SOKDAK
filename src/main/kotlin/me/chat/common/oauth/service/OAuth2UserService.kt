package me.chat.common.oauth.service

import me.chat.common.oauth.userinfo.SocialProvider
import me.chat.member.domain.entity.Member
import me.chat.member.infrastructure.repository.MemberRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserService(
    private val memberRepository: MemberRepository,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private val defaultOAuth2UserService: DefaultOAuth2UserService = DefaultOAuth2UserService()

    override fun loadUser(request: OAuth2UserRequest?): OAuth2User {
        requireNotNull(request) { "OAuth2 request is null" }

        val oauthUser = defaultOAuth2UserService.loadUser(request)
        val provider = SocialProvider.from(request.clientRegistration.registrationId)
        val userInfo = provider.createUserInfo(oauthUser.attributes)

        memberRepository.findByProviderAndProviderId(provider, userInfo.id)
            ?: memberRepository.save(
                Member(
                    provider = provider,
                    providerId = userInfo.id,
                    email = userInfo.email,
                    name = userInfo.name
                )
            )

        return DefaultOAuth2User(
            userInfo.authorities,
            userInfo.attributes,
            provider.getNameAttributeKey()
        )
    }
}
