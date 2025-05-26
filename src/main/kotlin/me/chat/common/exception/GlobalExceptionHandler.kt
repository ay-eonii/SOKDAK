package me.chat.common.exception

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ServiceException::class)
    fun handleServiceException(exception: ServiceException): ResponseEntity<ErrorResponse> {
        val stackTrace: Array<StackTraceElement> = exception.stackTrace
        val className = stackTrace[0].className
        val methodName = stackTrace[0].methodName
        val exceptionMessage: String = exception.message

        return ResponseEntity.status(exception.httpStatus)
            .body(ErrorResponse.create(exception, exception.httpStatus, exception.message))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ErrorResponse> {
        val stackTrace = exception.stackTrace
        val className = stackTrace[0].className
        val methodName = stackTrace[0].methodName
        val exceptionMessage = exception.message

        return ResponseEntity.internalServerError()
            .body(ErrorResponse.create(exception, HttpStatusCode.valueOf(500), "서버 오류가 발생했습니다."))
    }
}
