package me.chat.user.api.controller

import me.chat.common.response.RestResponse
import me.chat.user.api.request.AuthRequest
import me.chat.user.api.response.TokenResponse
import me.chat.user.domain.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun signUp(
        @RequestBody request: AuthRequest
    ): ResponseEntity<RestResponse<TokenResponse>> {
        val response: TokenResponse = userService.signUp(request)

        return ResponseEntity.ok(RestResponse(response))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: AuthRequest
    ): ResponseEntity<RestResponse<TokenResponse>> {
        val response: TokenResponse = userService.login(request)

        return ResponseEntity.ok(RestResponse(response))
    }
}
