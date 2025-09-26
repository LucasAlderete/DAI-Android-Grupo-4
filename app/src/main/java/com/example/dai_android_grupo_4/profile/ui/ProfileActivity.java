package com.example.dai_android_grupo_4.profile.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.profile.model.Usuario;
import com.example.dai_android_grupo_4.profile.ui.viewmodel.ProfileViewModel;
import com.example.dai_android_grupo_4.core.repository.TokenRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {

    private ImageView imgFotoPerfil;
    private EditText editNombre, editEmail;
    private Button btnGuardar;
    private ProfileViewModel viewModel;

    @Inject
    TokenRepository tokenRepository;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        editNombre = findViewById(R.id.editNombre);
        editEmail = findViewById(R.id.editEmail);
        btnGuardar = findViewById(R.id.btnGuardar);

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Obtener token real
        token = tokenRepository.getToken();
        if (token == null) {
            Toast.makeText(this, "No hay usuario logueado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Observers
        viewModel.getUsuarioLive().observe(this, this::mostrarUsuario);
        viewModel.getErrorLive().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        );

        // Cargar perfil
        viewModel.cargarPerfil(token);

        // Guardar cambios
        btnGuardar.setOnClickListener(v -> {
            Usuario usuario = new Usuario(
                    null,
                    editNombre.getText().toString(),
                    editEmail.getText().toString(),
                    null
            );
            viewModel.actualizarPerfil(token, usuario);
        });
    }

    private void mostrarUsuario(Usuario usuario) {
        editNombre.setText(usuario.getNombre());
        editEmail.setText(usuario.getEmail());
        if (usuario.getFotoUrl() != null && !usuario.getFotoUrl().isEmpty()) {
            Glide.with(this).load(usuario.getFotoUrl()).into(imgFotoPerfil);
        } else {
            imgFotoPerfil.setImageResource(R.drawable.ic_default_avatar);
        }
    }
}
