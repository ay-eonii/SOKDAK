package me.chat.member.api.controller

import me.chat.member.api.request.SignUpForm
import me.chat.member.domain.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class LoginViewController(private val memberService: MemberService) {

    @GetMapping("/login")
    fun login(model: Model): String {
        model.addAttribute("signUpForm", SignUpForm())
        return "auth/login"
    }

    @GetMapping("/signup")
    fun signupForm(model: Model): String {
        model.addAttribute("signUpForm", SignUpForm())
        return "auth/signup"
    }

    @PostMapping("/signup")
    fun signUp(
        @ModelAttribute request: SignUpForm,
        model: Model
    ): String {
        val result: Boolean = memberService.signUp(request)
        if (result) {
            return "redirect:/auth/login?signupSuccess"
        }
        model.addAttribute("error", "이미 사용 중인 이름입니다.")
        model.addAttribute("signUpForm", request)
        return "auth/signup"
    }
}
