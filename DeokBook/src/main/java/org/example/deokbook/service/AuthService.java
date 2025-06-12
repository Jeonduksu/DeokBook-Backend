package org.example.deokbook.service;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.Repository.AdminRepository;
import org.example.deokbook.dto.LoginRequest;
import org.example.deokbook.dto.LoginResponse;
import org.example.deokbook.entity.Admin;
import org.example.deokbook.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        System.out.println("로그인 시도 username = " + loginRequest.getUsername());
        Admin admin = adminRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    System.out.println("관리자를 찾을 수 없습니다.");
                    return new IllegalArgumentException("관리자를 찾을 수 없습니다.");
                });

        System.out.println("관리자 존재: " + admin.getUsername());

        boolean matches = passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword());
        System.out.println("비밀번호 매칭 결과: " + matches);
        if (!matches) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        String token = jwtUtil.generateJwtToken(admin.getUsername());
        System.out.println("토큰 생성 완료");

        return new LoginResponse(token, admin.getUsername());
    }

}
