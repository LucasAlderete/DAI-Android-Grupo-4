package com.example.dai_android_grupo_4.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.Fragment;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.MainActivity;
import com.example.dai_android_grupo_4.core.biometric.BiometricAuthenticator;

public class BiometricUnlockFragment extends Fragment {

    private BiometricAuthenticator biometricAuthenticator;
    private Button btnUsePassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_biometric_unlock, container, false);

        btnUsePassword = view.findViewById(R.id.btnUsePassword);

        biometricAuthenticator = new BiometricAuthenticator(
                requireActivity(),
                new BiometricAuthenticator.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        Toast.makeText(requireContext(), "Autenticación exitosa", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(requireActivity(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }

                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        Toast.makeText(requireContext(), "Error: " + errString, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        Toast.makeText(requireContext(), "Autenticación fallida", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        btnUsePassword.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Ingresar con usuario y contraseña", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.post(() -> biometricAuthenticator.authenticate());
    }
}
