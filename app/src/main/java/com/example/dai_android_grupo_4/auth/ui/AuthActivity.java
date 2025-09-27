package com.example.dai_android_grupo_4.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.MainActivity;
import com.example.dai_android_grupo_4.core.repository.TokenRepository;
import com.example.dai_android_grupo_4.data.api.model.TokenValidationResponse;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class AuthActivity extends AppCompatActivity {

    private static final String TAG = "AuthActivity";
    private NavController navController;

    @Inject
    TokenRepository tokenRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Si ya hay token guardado → validar token
        if (tokenRepository.hasToken()) {
            validateTokenAndProceed();
            return;
        }

        setupAuthFlow();
    }

    private void validateTokenAndProceed() {
        Call<TokenValidationResponse> call = tokenRepository.validateToken();
        if (call == null) {
            setupAuthFlow();
            return;
        }

        call.enqueue(new Callback<TokenValidationResponse>() {
            @Override
            public void onResponse(Call<TokenValidationResponse> call, Response<TokenValidationResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isValid()) {
                    Log.d(TAG, "Token válido, navegando a MainActivity");
                    navigateToMainActivity();
                } else {
                    Log.d(TAG, "Token inválido, limpiando y mostrando login");
                    tokenRepository.clearToken();
                    setupAuthFlow();
                }
            }

            @Override
            public void onFailure(Call<TokenValidationResponse> call, Throwable t) {
                Log.e(TAG, "Error validando token", t);
                tokenRepository.clearToken();
                setupAuthFlow();
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupAuthFlow() {
        setContentView(R.layout.auth_activity);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.auth_nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }
}
