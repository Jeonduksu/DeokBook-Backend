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
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        Admin admin = adminRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("관리자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        String token = jwtUtil.generateJwtToken(admin.getUsername());
        return new LoginResponse(token, admin.getUsername());
    }
}
