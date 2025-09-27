package com.example.dai_android_grupo_4.core.interceptor;

import com.example.dai_android_grupo_4.core.repository.TokenRepository;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class AuthInterceptor implements Interceptor {

    private final TokenRepository tokenRepository;

    @Inject
    public AuthInterceptor(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // TODO: TEMPORAL - Reemplazar con token real cuando esté implementado
        String hardcodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc20uYWxkZXJldGVAZ21haWwuY29tIiwiaWF0IjoxNzU4OTQ3MjMxLCJleHAiOjE3NTkwMzM2MzF9.XSDjYTlpCWtlOCzlHQhGN8p87LnwJ_Wj9Uymf7tLoYQ"; // ← PEGA TU TOKEN AQUÍ
        tokenRepository.saveToken(hardcodedToken);

        // Obtener el token (comentado temporalmente)
        String token = tokenRepository.getToken();

        // Si no hay token, enviar request sin modificar
        if (token == null || token.isEmpty()) {
            return chain.proceed(originalRequest);
        }

        // Agregar header Authorization con el token
        Request newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return chain.proceed(newRequest);
    }
}