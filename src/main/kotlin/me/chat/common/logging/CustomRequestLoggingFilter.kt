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

    override fun shouldLog(request: HttpServletRequest): Boolean = true

    override fun beforeRequest(request: HttpServletRequest, message: String) {
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        logger.info(message)
    }
}
