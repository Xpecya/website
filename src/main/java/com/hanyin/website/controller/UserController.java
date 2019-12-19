package com.hanyin.website.controller;

import com.hanyin.website.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(String username, String password) {
        return userService.login(username, password);
    }

    @GetMapping("/logout")
    public String logout(String sessionId) {
        return userService.logout(sessionId);
    }
}
