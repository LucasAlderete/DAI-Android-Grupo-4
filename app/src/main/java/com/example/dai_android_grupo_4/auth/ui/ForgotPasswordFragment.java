package com.example.dai_android_grupo_4.auth.ui;

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
import androidx.navigation.fragment.NavHostFragment;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.data.api.model.OtpRequest;
import com.example.dai_android_grupo_4.data.api.repository.ApiRepositoryImpl;
import com.example.dai_android_grupo_4.data.api.repository.AuthCallback;
import com.example.dai_android_grupo_4.data.api.model.AuthResponse;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ForgotPasswordFragment extends Fragment {

    @Inject
    ApiRepositoryImpl apiRepository;

    private TextInputEditText emailEditText;
    private MaterialButton sendButton, backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auth_fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailEditText = view.findViewById(R.id.emailEditText);
        sendButton = view.findViewById(R.id.sendButton);
        backButton = view.findViewById(R.id.backButton);

        sendButton.setOnClickListener(v -> {
            String email = emailEditText.getText() != null ? emailEditText.getText().toString() : "";

            if (email.isEmpty()) {
                Toast.makeText(getContext(), "Ingresa tu email", Toast.LENGTH_SHORT).show();
                return;
            }

            OtpRequest request = new OtpRequest(email, "1"); // dummy password

            apiRepository.resendOtp(request, new AuthCallback() {
                @Override
                public void onSuccess(AuthResponse response) {
                    Toast.makeText(getContext(), "Código enviado a tu correo", Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);

                    NavHostFragment.findNavController(ForgotPasswordFragment.this)
                            .navigate(R.id.action_forgotPasswordFragment_to_verifyCodeFragment, bundle);
                }

                @Override
                public void onError(Throwable t) {
                    Toast.makeText(getContext(), "Error al enviar código: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        backButton.setOnClickListener(v ->
                NavHostFragment.findNavController(ForgotPasswordFragment.this)
                        .navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        );
    }
}
