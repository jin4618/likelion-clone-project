package org.example.jelog.controller;

import lombok.RequiredArgsConstructor;
import org.example.jelog.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    @GetMapping("/check-username")
    public boolean checkUsername(@RequestParam String username) {
        return userService.isUserIdTaken(username);
    }

    @GetMapping("/check-email")
    public boolean checkEmail(@RequestParam String email) {
        return userService.isUserEmailTaken(email);
    }
}
