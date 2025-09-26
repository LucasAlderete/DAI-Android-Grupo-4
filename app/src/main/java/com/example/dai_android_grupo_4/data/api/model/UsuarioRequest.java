package com.example.dai_android_grupo_4.data.api.model;

public class UsuarioRequest {
    private String nombre;
    private String email;
    private String password;
    private String fotoUrl;

    public UsuarioRequest(String nombre, String email, String password, String fotoUrl) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fotoUrl = fotoUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
