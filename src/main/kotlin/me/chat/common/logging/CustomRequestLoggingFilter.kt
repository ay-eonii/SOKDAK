package me.chat.common.logging

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.filter.CommonsRequestLoggingFilter

class CustomRequestLoggingFilter : CommonsRequestLoggingFilter() {

    init {
        isIncludeQueryString = true
        isIncludePayload = true
        isIncludeClientInfo = true
        maxPayloadLength = 10000
        setAfterMessagePrefix("request = [")
    }

    override fun shouldLog(request: HttpServletRequest): Boolean {
        return !EXCLUDE_URI_PREFIX.any { prefix -> request.requestURI.startsWith(prefix) }
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        logger.info(message)
    }

    companion object {
        private val EXCLUDE_URI_PREFIX: Set<String> = setOf(
            "/js",
            "/css",
            "/favicon"
        )
    }
}
