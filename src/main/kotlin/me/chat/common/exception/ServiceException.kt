package me.chat.common.exception

import org.springframework.http.HttpStatus

class ServiceException(
    val httpStatus: HttpStatus,
    override val message: String
) : RuntimeException()
