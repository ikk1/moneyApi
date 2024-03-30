package com.junior.money.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.dto.AuthenticationDto;
import com.junior.money.api.dto.LoginResponseDto;
import com.junior.money.api.dto.RegisterDto;
import com.junior.money.api.exception.UserAuthenticationException;
import com.junior.money.api.helper.mappers.UserMapper;
import com.junior.money.api.infra.security.TokenService;
import com.junior.money.api.models.User;
import com.junior.money.api.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService,
            UserMapper userMapper, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken userAndPassword = new UsernamePasswordAuthenticationToken(
                authenticationDto.login(), authenticationDto.password());
        Authentication authenticate = authenticationManager.authenticate(userAndPassword);
        String token = tokenService.generateToken((User) authenticate.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {

        if (userService.findByLogin(registerDto.login()) != null)
            throw new UserAuthenticationException("Login already exists");

        String encodedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        User userEntity = userMapper.toEntity(registerDto);
        userEntity.setPassword(encodedPassword);
        userService.register(userEntity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
