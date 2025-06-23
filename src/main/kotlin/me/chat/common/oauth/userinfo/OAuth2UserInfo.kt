package me.chat.common.oauth.userinfo

import org.springframework.security.oauth2.core.user.OAuth2User

interface OAuth2UserInfo : OAuth2User {
    val id: String
    val email: String
}
