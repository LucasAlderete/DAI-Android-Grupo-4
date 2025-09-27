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

        // Obtener el token
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