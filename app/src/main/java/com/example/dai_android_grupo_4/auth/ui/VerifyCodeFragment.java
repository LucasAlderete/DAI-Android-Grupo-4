package com.example.dai_android_grupo_4.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.MainActivity;
import com.example.dai_android_grupo_4.data.api.model.AuthResponse;
import com.example.dai_android_grupo_4.data.api.model.VerifyOtpRequest;
import com.example.dai_android_grupo_4.data.api.model.OtpRequest;
import com.example.dai_android_grupo_4.data.api.repository.ApiRepositoryImpl;
import com.example.dai_android_grupo_4.data.api.repository.AuthCallback;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VerifyCodeFragment extends Fragment {

    @Inject
    ApiRepositoryImpl apiRepository;

    private TextInputEditText codeEditText;
    private MaterialButton verifyButton;
    private Button resendButton;

    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auth_fragment_verify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        codeEditText = view.findViewById(R.id.codeEditText);
        verifyButton = view.findViewById(R.id.verifyButton);
        resendButton = view.findViewById(R.id.resendButton);

        if (getArguments() != null) {
            email = getArguments().getString("email");
        }

        verifyButton.setOnClickListener(v -> {
            String code = codeEditText.getText() != null ? codeEditText.getText().toString() : "";

            if (code.isEmpty()) {
                Toast.makeText(getContext(), "Ingresa el código", Toast.LENGTH_SHORT).show();
                return;
            }

            VerifyOtpRequest request = new VerifyOtpRequest(email, code);
            apiRepository.verifyOtp(request, new AuthCallback() {
                @Override
                public void onSuccess(AuthResponse response) {
                    Toast.makeText(getContext(), "Código verificado correctamente", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                }

                @Override
                public void onError(Throwable t) {
                    Toast.makeText(getContext(), "Error al verificar código: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        resendButton.setOnClickListener(v -> {
            OtpRequest request = new OtpRequest(email, "1");

            apiRepository.resendOtp(request, new AuthCallback() {
                @Override
                public void onSuccess(AuthResponse response) {
                    Toast.makeText(getContext(), "Código reenviado a tu correo", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable t) {
                    Toast.makeText(getContext(), "Error al reenviar código: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
