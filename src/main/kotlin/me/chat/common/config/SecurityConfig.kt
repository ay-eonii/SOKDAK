package me.chat.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }

            // 세션 기반(Form Login)을 쓰려면 아래 줄 대신 IF_REQUIRED(default)로 두세요.
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }

            // URL 접근 권한
            .authorizeHttpRequests {
                it
                    .requestMatchers("/", "/signup", "/login", "/chat/any", "/css/**", "/js/**").permitAll()
                    .anyRequest().authenticated()
            }

            // 커스텀 로그인 폼
            .formLogin {
                it
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/chat/room", true)
                    .failureUrl("/login?error")
                    .permitAll()
            }

            // 로그아웃
            .logout {
                it
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
            }

        return http.build()
    }

    @Bean
    fun authProvider(): DaoAuthenticationProvider {
        return DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailsService)
            setPasswordEncoder(passwordEncoder)
        }
    }
}
