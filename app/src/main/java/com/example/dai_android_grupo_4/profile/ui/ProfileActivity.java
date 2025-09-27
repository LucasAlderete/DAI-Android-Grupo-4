package com.example.dai_android_grupo_4.profile.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.auth.ui.AuthActivity;
import com.example.dai_android_grupo_4.core.repository.TokenRepository;
import com.example.dai_android_grupo_4.data.api.ApiService;
import com.example.dai_android_grupo_4.data.api.model.UsuarioResponse;
import com.example.dai_android_grupo_4.data.api.model.UsuarioUpdateRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {

    @Inject ApiService apiService;
    @Inject TokenRepository tokenRepository;

    private ImageView imgProfile;
    private TextInputEditText edtNombre, edtEmail;
    private MaterialButton btnActualizar, btnLogout;

    private String token;
    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageUri;
    private static final String BASE_URL = "http://10.0.2.2:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Perfil");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imgProfile = findViewById(R.id.imgProfile);
        edtNombre = findViewById(R.id.edtNombre);
        edtEmail = findViewById(R.id.edtEmail);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnLogout = findViewById(R.id.btnLogout);

        token = "Bearer " + tokenRepository.getToken();

        cargarPerfil();

        // Al tocar la imagen abrir galería
        imgProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnActualizar.setOnClickListener(v -> actualizarPerfil());

        btnLogout.setOnClickListener(v -> {
            tokenRepository.clearToken();
            Toast.makeText(ProfileActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ProfileActivity.this, AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                    Glide.with(ProfileActivity.this)
                            .load(fotoUrl != null && !fotoUrl.isEmpty() ? BASE_URL + fotoUrl : R.drawable.ic_default_avatar)
                            .placeholder(R.drawable.ic_default_avatar)
                            .into(imgProfile);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                subirImagen(imageUri);
            }
        }
    }

    private void subirImagen(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            String fileName = UUID.randomUUID() + ".jpg";
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), inputStream.readAllBytes());
            MultipartBody.Part body = MultipartBody.Part.createFormData("imagen", fileName, requestFile);

            apiService.updateImagenPerfil(token, body).enqueue(new Callback<UsuarioResponse>() {
                @Override
                public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String fotoUrl = response.body().getFotoUrl();
                        Glide.with(ProfileActivity.this)
                                .load(fotoUrl != null && !fotoUrl.isEmpty() ? BASE_URL + fotoUrl : R.drawable.ic_default_avatar)
                                .placeholder(R.drawable.ic_default_avatar)
                                .into(imgProfile);
                        Toast.makeText(ProfileActivity.this, "Imagen actualizada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Error al subir imagen", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al leer la imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }
}
