package me.chat.common.argumentresolver

import me.chat.common.jwt.AccessTokenProvider
import me.chat.user.domain.entity.User
import me.chat.user.domain.service.UserService
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LoginUserArgumentResolver(
    private val userService: UserService,
    private val accessTokenProvider: AccessTokenProvider
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter, mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?
    ): User {
        val authorizationHeader: String? = webRequest.getHeader("Authorization")
        if (authorizationHeader.isNullOrBlank()) {
            throw IllegalArgumentException()
        }

        val token: String = extractToken(authorizationHeader)
        val id: String = accessTokenProvider.getClaimId(token)
        return userService.getUser(id)
    }

    private fun extractToken(authorizationHeader: String): String {
        return authorizationHeader.substring(7)
    }
}
