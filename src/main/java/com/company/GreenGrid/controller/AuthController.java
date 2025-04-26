package com.company.GreenGrid.controller;


import com.company.GreenGrid.dtos.SignUpDto;
import com.company.GreenGrid.dtos.UserDto;
import com.company.GreenGrid.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        return new ResponseEntity<>(authService.signup(signUpDto), HttpStatus.CREATED);
    }
}
