package me.chat.common.oauth.userinfo

import org.springframework.security.core.authority.SimpleGrantedAuthority

data class GoogleUserInfo(
    private val attributes: Map<String, Any>,
) : OAuth2UserInfo {
    override val id: String = attributes["sub"] as String
    override val email: String = attributes["email"] as String

    override fun getName(): String = attributes["name"] as String

    override fun getAttributes(): Map<String, Any> = attributes

    override fun getAuthorities(): List<SimpleGrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_USER"))
}
