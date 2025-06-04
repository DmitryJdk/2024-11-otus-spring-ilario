package ru.home.ilar.security;

public interface JwtService {

    Long extractUserId(String token);

    boolean isValid(String token);
}
