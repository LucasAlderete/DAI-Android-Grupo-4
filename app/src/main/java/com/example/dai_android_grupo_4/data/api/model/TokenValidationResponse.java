package com.example.dai_android_grupo_4.data.api.model;

import com.google.gson.annotations.SerializedName;

public class TokenValidationResponse {

    @SerializedName("valid")
    private boolean valid;

    @SerializedName("email")
    private String email;

    @SerializedName("error")
    private String error;

    public TokenValidationResponse() {}

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}