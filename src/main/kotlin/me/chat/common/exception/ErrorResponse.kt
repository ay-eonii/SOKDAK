package me.chat.common.exception

data class ErrorResponse(
    val status: Int,
    val message: String,
    val error: String
)
