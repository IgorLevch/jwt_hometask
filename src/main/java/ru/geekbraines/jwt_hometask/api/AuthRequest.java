package ru.geekbraines.jwt_hometask.api;

import lombok.Getter;

@Getter
public class AuthRequest {

    private String username;
    private String password;

}
