package com.company.GreenGrid.service;


import com.company.GreenGrid.dtos.SignUpDto;
import com.company.GreenGrid.dtos.UserDto;
import com.company.GreenGrid.entities.User;
import com.company.GreenGrid.enums.Roles;
import com.company.GreenGrid.exceptions.ResourceNotFoundException;
import com.company.GreenGrid.exceptions.RuntimeConflictException;
import com.company.GreenGrid.repository.UserRepository;
import com.company.GreenGrid.security.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public String[] login(String email,String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken, refreshToken};

    }

    @Transactional
    public UserDto signup(SignUpDto signUpDto){
        User user = userRepository.findByEmail(signUpDto.getEmail()).orElse(null);

        if(user!=null){
            throw new RuntimeConflictException("cannot signup, User already exists with email"+signUpDto.getEmail());
        }

        User mappedUser = modelMapper.map(signUpDto, User.class);
        mappedUser.setRoles(Set.of(Roles.INDIVIDUALS));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }



    public String refreshToken(String refreshToken){
        Long userId = jwtService.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with id:  "+userId));
        return jwtService.generateAccessToken(user);
    }
}
