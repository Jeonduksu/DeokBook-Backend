package org.example.deokbook.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.user.entity.User;
import org.example.deokbook.domain.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

}
