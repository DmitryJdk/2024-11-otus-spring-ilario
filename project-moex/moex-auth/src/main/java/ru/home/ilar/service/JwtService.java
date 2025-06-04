package ru.home.ilar.service;

public interface JwtService {

    String generateToken(Long userId);

}
