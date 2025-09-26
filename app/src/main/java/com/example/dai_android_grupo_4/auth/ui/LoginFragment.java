package com.example.dai_android_grupo_4.auth.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.core.repository.TokenRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    @Inject
    TokenRepository tokenRepository;
    
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

        userEditText = view.findViewById(R.id.userEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        MaterialButton loginButton = view.findViewById(R.id.loginButton);
        MaterialButton registerButton = view.findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> {
            String exampleToken = "jwt_token_example_" + System.currentTimeMillis();
            tokenRepository.saveToken(exampleToken);
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_pokemonListActivity);
        });
        
        registerButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }
}
