package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.security.JwtTokenUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final JwtTokenUtil jwtTokenUtil;

    public LoginController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public ResponseEntity<?> login() {
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.createToken()).body("Show!");
    }
}
