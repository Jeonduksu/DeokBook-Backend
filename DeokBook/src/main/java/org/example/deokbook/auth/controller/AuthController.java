package org.example.deokbook.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.auth.CustomUserDetails;
import org.example.deokbook.auth.dto.LoginRequest;
import org.example.deokbook.auth.dto.LoginResponse;
import org.example.deokbook.auth.service.CustomUserDetailsService;
import org.example.deokbook.domain.user.entity.User;
import org.example.deokbook.domain.user.service.UserService;
import org.example.deokbook.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            String email = user.getEmail();
            Character rule = user.getRule();

            String accessToken = jwtUtil.generateAccessToken(email, rule);
            String refreshToken = jwtUtil.generateRefreshToken(email, rule);

            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
        } catch (Exception e) {
            throw new RuntimeException("로그인 처리 중 오류 발생 : " + e.getMessage());
        }
    }
}
