package com.example.dai_android_grupo_4.core.repository;

public interface TokenRepository {  
    void saveToken(String token);
    String getToken();
    void clearToken();
    boolean hasToken();
}
