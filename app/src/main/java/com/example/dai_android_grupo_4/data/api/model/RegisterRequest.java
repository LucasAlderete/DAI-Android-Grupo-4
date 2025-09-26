package com.example.dai_android_grupo_4.data.api.model;

public class RegisterRequest {
    private String email;
    private String nombre;
    private String password;

    public RegisterRequest(String email, String nombre, String password) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
    }
    // getters y setters
}
