package org.example.deokbook.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.user.entity.User;
import org.example.deokbook.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    // 사용자 목록
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    
    //사용자 등록
    public User registerUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        return userRepository.save(user);
    }
    
    //사용자 수정
    public User update(Long id, User user) {
        User users = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        users.updateUser(user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getPhone(),
                user.getUserMemo(),
                user.getRule());

        return userRepository.save(users);
    }
    
    // 사용자 삭제
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
