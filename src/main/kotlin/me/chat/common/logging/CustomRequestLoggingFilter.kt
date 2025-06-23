package me.chat.common.logging

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.web.filter.CommonsRequestLoggingFilter

class CustomRequestLoggingFilter : CommonsRequestLoggingFilter() {
    private val log = LoggerFactory.getLogger(CustomRequestLoggingFilter::class.java)

    init {
        isIncludeQueryString = true
        isIncludePayload = true
        maxPayloadLength = 10000
        setAfterMessagePrefix("")
        setAfterMessageSuffix("")
    }

    override fun shouldLog(request: HttpServletRequest): Boolean {
        return !EXCLUDE_URI_PREFIX.any { prefix -> request.requestURI.startsWith(prefix) }
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        val user = request.userPrincipal?.name ?: "ANONYMOUS"
        log.info("request = [{}, user={}]", message, user)
    }

    companion object {
        private val EXCLUDE_URI_PREFIX: Set<String> = setOf(
            "/js",
            "/css",
            "/favicon",
            "/.well-known"
        )
    }
}
