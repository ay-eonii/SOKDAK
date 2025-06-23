package me.chat.common.oauth.userinfo

enum class SocialProvider(
    private val nameAttributeKey: String,
    private val creator: (Map<String, Any>) -> OAuth2UserInfo,
) {
    GOOGLE(
        "sub",
        ::GoogleUserInfo,
    ),
    ;

    fun getNameAttributeKey() = nameAttributeKey

    fun createUserInfo(attributes: Map<String, Any>): OAuth2UserInfo = creator(attributes)

    companion object {
        fun from(registrationId: String) =
            entries.find { it.name.equals(registrationId, true) }
                ?: throw IllegalArgumentException()
    }
}
