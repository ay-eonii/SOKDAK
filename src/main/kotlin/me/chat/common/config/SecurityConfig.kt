package me.chat.common.config

import me.chat.common.oauth.service.OAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val oAuth2UserService: OAuth2UserService
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .rememberMe { it.disable() }
            .requestCache { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/",
                        "/signup",
                        "/login",
                        "/oauth2/**",
                        "/chat/any/**",
                        "/css/**",
                        "/js/**"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .exceptionHandling {
                it
                    .authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
                    .accessDeniedPage("/")    // 권한은 있지만 접근이 막혔을 때도 "/"
            }
            .oauth2Login {
                it
                    .userInfoEndpoint { endpoint -> endpoint.userService(oAuth2UserService) }
                    .loginPage("/login")
                    .defaultSuccessUrl("/chat/me", true)
                    .failureUrl("/login?error")
            }
            .logout {
                it
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
            }

        return http.build()
    }
}
