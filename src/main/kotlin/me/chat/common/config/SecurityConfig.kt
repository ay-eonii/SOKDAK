package me.chat.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint

@Configuration
@EnableWebSecurity
class SecurityConfig {

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

            // 인증이 필요한 요청이 들어오면 "/" 로 redirect
            .exceptionHandling {
                it
                    .authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
                    .accessDeniedPage("/")    // 권한은 있지만 접근이 막혔을 때도 "/"
            }

            // 커스텀 로그인 폼
            .formLogin {
                it
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/chat/me", true)
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
}
