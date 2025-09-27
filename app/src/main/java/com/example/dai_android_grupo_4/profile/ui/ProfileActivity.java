package com.example.dai_android_grupo_4.profile.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.data.api.ApiService;
import com.example.dai_android_grupo_4.data.api.model.UsuarioResponse;
import com.example.dai_android_grupo_4.data.api.model.UsuarioUpdateRequest;
import com.example.dai_android_grupo_4.core.repository.TokenRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {

    @Inject
    ApiService apiService;

    @Inject
    TokenRepository tokenRepository;

    private ImageView imgProfile;
    private TextInputEditText edtNombre, edtEmail;
    private MaterialButton btnActualizar;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgProfile = findViewById(R.id.imgProfile);
        edtNombre = findViewById(R.id.edtNombre);
        edtEmail = findViewById(R.id.edtEmail);
        btnActualizar = findViewById(R.id.btnActualizar);

        token = "Bearer " + tokenRepository.getToken(); // obtenemos token guardado

        cargarPerfil();

        btnActualizar.setOnClickListener(v -> actualizarPerfil());
    }

    private void cargarPerfil() {
        apiService.getPerfil(token).enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UsuarioResponse usuario = response.body();
                    edtNombre.setText(usuario.getNombre());
                    edtEmail.setText(usuario.getEmail());

                    String fotoUrl = usuario.getFotoUrl();
                    if (fotoUrl != null && !fotoUrl.isEmpty()) {
                        Glide.with(ProfileActivity.this)
                                .load(fotoUrl)
                                .placeholder(R.drawable.ic_default_avatar)
                                .into(imgProfile);
                    } else {
                        imgProfile.setImageResource(R.drawable.ic_default_avatar);
                    }

                } else {
                    Toast.makeText(ProfileActivity.this, "Error al cargar perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarPerfil() {
        String nombre = edtNombre.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        UsuarioUpdateRequest request = new UsuarioUpdateRequest();
        if (!nombre.isEmpty()) request.setNombre(nombre);
        if (!email.isEmpty()) request.setEmail(email);

        apiService.updatePerfil(token, request).enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ProfileActivity.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Error al actualizar perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
