package com.example.dai_android_grupo_4.auth.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.data.api.model.RegisterRequest;
import com.example.dai_android_grupo_4.data.api.model.AuthResponse;
import com.example.dai_android_grupo_4.data.api.repository.ApiRepositoryImpl;
import com.example.dai_android_grupo_4.data.api.repository.AuthCallback;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    @Inject
    ApiRepositoryImpl apiRepository;

    private TextInputEditText nameEditText;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auth_fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameEditText = view.findViewById(R.id.nameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        MaterialButton registerButton = view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            String nombre = nameEditText.getText() != null ? nameEditText.getText().toString() : "";
            String email = emailEditText.getText() != null ? emailEditText.getText().toString() : "";
            String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterRequest request = new RegisterRequest(email, nombre, password);

            apiRepository.register(request, new AuthCallback() {
                @Override
                public void onSuccess(AuthResponse response) {
                    Toast.makeText(getContext(), "Registro exitoso: " + response.getMensaje(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("password", password);

                    Navigation.findNavController(view)
                            .navigate(R.id.action_registerFragment_to_verifyCodeFragment, bundle);
                }

                @Override
                public void onError(Throwable t) {
                    Toast.makeText(getContext(), "Error en registro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
