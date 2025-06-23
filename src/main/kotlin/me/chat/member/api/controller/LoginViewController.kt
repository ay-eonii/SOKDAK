package me.chat.member.api.controller

import me.chat.member.api.request.SignUpForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginViewController {

    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("signUpForm", SignUpForm())
        return "auth/login"
    }
}
