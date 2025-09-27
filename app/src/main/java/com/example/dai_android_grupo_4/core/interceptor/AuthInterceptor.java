package com.example.dai_android_grupo_4.core.interceptor;

import com.example.dai_android_grupo_4.core.repository.TokenRepository;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class AuthInterceptor implements Interceptor {

    private final Provider<TokenRepository> tokenRepositoryProvider;

    @Inject
    public AuthInterceptor(Provider<TokenRepository> tokenRepositoryProvider) {
        this.tokenRepositoryProvider = tokenRepositoryProvider;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String token = tokenRepositoryProvider.get().getToken();

        if (token == null || token.isEmpty()) {
            return chain.proceed(originalRequest);
        }

        Request newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return chain.proceed(newRequest);
    }
}
