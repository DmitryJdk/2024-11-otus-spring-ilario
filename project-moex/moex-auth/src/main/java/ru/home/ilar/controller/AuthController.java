package ru.home.ilar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.home.ilar.dto.UserDto;
import ru.home.ilar.repository.UserRepository;
import ru.home.ilar.service.JwtService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/login")
    public Mono<ResponseEntity<?>> login(@RequestBody UserDto userDto) {
        return userRepository.findByUsername(userDto.username())
                .flatMap(user -> {
                    if (passwordEncoder.matches(userDto.password(), user.getPassword())) {
                        String token = jwtService.generateToken(user.getId());
                        return Mono.just(ResponseEntity.ok(Map.of("token", token)));
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
