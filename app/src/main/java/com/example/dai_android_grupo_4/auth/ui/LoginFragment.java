package com.example.dai_android_grupo_4.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.core.repository.TokenRepository;
import com.example.dai_android_grupo_4.data.api.model.LoginRequest;
import com.example.dai_android_grupo_4.data.api.model.AuthResponse;
import com.example.dai_android_grupo_4.data.api.repository.ApiRepositoryImpl;
import com.example.dai_android_grupo_4.data.api.repository.AuthCallback;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    @Inject
    TokenRepository tokenRepository;

    @Inject
    ApiRepositoryImpl apiRepository;

    private TextInputEditText userEditText;
    private TextInputEditText passwordEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auth_fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ✅ Si ya hay token, saltamos login directamente
        if (tokenRepository.getToken() != null) {
            Intent intent = new Intent(getActivity(), com.example.dai_android_grupo_4.ReservasActivity.class);
            startActivity(intent);
            requireActivity().finish();
            return;
        }

        userEditText = view.findViewById(R.id.userEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        MaterialButton loginButton = view.findViewById(R.id.loginButton);
        MaterialButton registerButton = view.findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> {
            String email = userEditText.getText() != null ? userEditText.getText().toString() : "";
            String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginRequest request = new LoginRequest(email, password);

            apiRepository.login(request, new AuthCallback() {
                @Override
                public void onSuccess(AuthResponse response) {
                    // ✅ Guardar token
                    tokenRepository.saveToken(response.getToken());
                    Toast.makeText(getContext(), "Login exitoso", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), com.example.dai_android_grupo_4.MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                }

                @Override
                public void onError(Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });

        registerButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }
}
