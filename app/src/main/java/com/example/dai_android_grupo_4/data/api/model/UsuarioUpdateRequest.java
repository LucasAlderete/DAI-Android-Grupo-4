package com.example.dai_android_grupo_4.data.api.model;

import com.google.gson.annotations.SerializedName;

public class UsuarioUpdateRequest {

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("email")
    private String email;

    @SerializedName("fotoUrl")
    private String fotoUrl;

    public UsuarioUpdateRequest() {}

    public UsuarioUpdateRequest(String nombre, String email, String fotoUrl) {
        this.nombre = nombre;
        this.email = email;
        this.fotoUrl = fotoUrl;
    }

    // getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
}

