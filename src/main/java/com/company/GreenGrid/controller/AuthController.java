package com.company.GreenGrid.controller;


import com.company.GreenGrid.dtos.LoginRequestDto;
import com.company.GreenGrid.dtos.LoginResponseDto;
import com.company.GreenGrid.dtos.SignUpDto;
import com.company.GreenGrid.dtos.UserDto;
import com.company.GreenGrid.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        System.out.println("Signup API called for email: " + signUpDto.getEmail());
        return new ResponseEntity<>(authService.signup(signUpDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
                                                  HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String tokens[] = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // Save Refresh Token in cookie named "refreshToken" (NOT "token")
        Cookie refreshCookie = new Cookie("refreshToken", tokens[1]);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);
        httpServletResponse.addCookie(refreshCookie);

        return ResponseEntity.ok(new LoginResponseDto(tokens[0]));
    }



    //login and signup
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        String accessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
}
