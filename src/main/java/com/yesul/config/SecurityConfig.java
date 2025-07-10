package com.yesul.config;

import com.yesul.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.yesul.user.service.CustomOAuth2UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService adminUserDetailsService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService oAuth2MemberService;
    private final SystemMonitoringFilter systemMonitoringFilter;

    // @RequiredArgsConstructor 제거, @Qualifier로 직접 명시, Ambiguty 처리
    public SecurityConfig(
            @Qualifier("userDetailsServiceImpl") UserDetailsService adminUserDetailsService,
            CustomUserDetailsService customUserDetailsService,
            CustomOAuth2UserService oAuth2MemberService,
            SystemMonitoringFilter systemMonitoringFilter) {
        this.adminUserDetailsService = adminUserDetailsService;
        this.customUserDetailsService = customUserDetailsService;
        this.oAuth2MemberService = oAuth2MemberService;
        this.systemMonitoringFilter = systemMonitoringFilter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .userDetailsService(adminUserDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/admin/login", "/asserts/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(systemMonitoringFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .failureHandler((request, response, exception) -> {
                            exception.printStackTrace();
                            response.sendRedirect("/admin/login?error");
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        return http.build();
    }

    // 일반 유저 Security 설정
    @Bean
    @Order(2)
    public SecurityFilterChain allFilterChain(HttpSecurity http, DaoAuthenticationProvider daoAuth) throws Exception {
        http
                .authenticationProvider(daoAuth)
                .securityMatcher("/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                // 로그인 이후 구글 devtool
                                "/.well-known/appspecific/**",
                                // User단
                                "/user/verify-email",
                                "/user/regist",
                                "/user/regist-process",
                                "/user/user-regist-mail",
                                "/reset-password",
                                "/password-reset-complete",

                                "/", "/main", "/user/assets/**", "/community/**", "/error",
                                "/assets/**",
                                "/asserts/**",
                                "/images/**",
                                "/login",
                                "/logout",
                                "/login/oauth2/**",
                                "/oauth2/**",
                                "/post-images/upload",
                                "/alcohols/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(systemMonitoringFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .failureHandler((request, response, exception) -> {
                            exception.printStackTrace();
                            response.sendRedirect("/login?error");
                        })
                        .defaultSuccessUrl("/user/profile")
                        .permitAll()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .defaultSuccessUrl("/user/profile")
                        .failureHandler((request, response, exception) -> {
                            exception.printStackTrace();
                            response.sendRedirect("/login?error");
                        })
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2MemberService)
                        )
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}