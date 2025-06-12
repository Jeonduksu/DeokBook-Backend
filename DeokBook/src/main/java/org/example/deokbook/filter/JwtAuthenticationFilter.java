package org.example.deokbook.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.deokbook.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // 🔍 디버깅용 로그
        logger.debug("JWT Filter - Processing: " + method + " " + requestURI);

        // Authorization 헤더에서 JWT 토큰 추출
        String authHeader = request.getHeader("Authorization");
        logger.debug("JWT Filter - Authorization header: " + authHeader);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.debug("JWT Filter - Extracted token: " + token.substring(0, Math.min(token.length(), 20)) + "...");

            try {
                // 토큰 유효성 검사
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.getUsernameFromToken(token);
                    logger.debug("JWT Filter - Valid token for user: " + username);

                    // SecurityContext에 인증 정보 설정
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    logger.debug("JWT Filter - Authentication set for user: " + username);
                } else {
                    logger.warn("JWT Filter - Invalid token");
                }
            } catch (Exception e) {
                logger.error("JWT Filter - Token validation failed: " + e.getMessage());
            }
        } else {
            logger.debug("JWT Filter - No Authorization header or not Bearer token");
        }

        filterChain.doFilter(request, response);
    }
}