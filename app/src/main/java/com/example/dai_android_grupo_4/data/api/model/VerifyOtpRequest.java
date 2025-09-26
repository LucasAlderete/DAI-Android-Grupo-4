package com.example.dai_android_grupo_4.data.api.model;

public class VerifyOtpRequest {
    private String email;
    private String codigo;

    public VerifyOtpRequest(String email, String codigo) {
        this.email = email;
        this.codigo = codigo;
    }
}
