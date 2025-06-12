// 1. SecurityConfig.java - 더 명확한 패턴 매칭
package org.example.deokbook.config;

import org.example.deokbook.filter.JwtAuthenticationFilter;
import org.example.deokbook.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authz -> authz
                        // 🔓 공개 엔드포인트 (인증 불필요)
                        .requestMatchers("/api/auth/**").permitAll()              // 인증 관련
                        .requestMatchers("/api/public/**").permitAll()            // 공개 API
                        .requestMatchers("/h2-console/**").permitAll()            // H2 콘솔
                        .requestMatchers("/error").permitAll()                    // 에러 페이지
                        .requestMatchers("/actuator/**").permitAll()              // 액추에이터 (개발용)

                        // 🔒 보호된 엔드포인트 (JWT 토큰 필요)
                        .requestMatchers("/api/users/**").authenticated()         // 사용자 관리
                        .requestMatchers("/api/books/**").authenticated()         // 도서 관리
                        .requestMatchers("/api/loans/**").authenticated()         // 대출 관리

                        // 기타 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                .headers().frameOptions().sameOrigin()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}