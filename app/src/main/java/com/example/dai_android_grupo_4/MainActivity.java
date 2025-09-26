package com.example.dai_android_grupo_4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.material.button.MaterialButton;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dai_android_grupo_4.auth.ui.AuthActivity;
import com.example.dai_android_grupo_4.core.repository.TokenRepository;
import com.example.dai_android_grupo_4.services.ReservaService;

import com.example.dai_android_grupo_4.profile.ui.ProfileActivity;

import java.util.List;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ActivityLifecycle";

    @Inject
    ReservaService reservaService;

    @Inject
    TokenRepository tokenRepository;

    private ListView listView;
    private List<String> reservaDisplayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "⭐ onCreate: La Activity está siendo creada");
        setContentView(R.layout.activity_main);

        MaterialButton btnReservas = findViewById(R.id.btnReservas);
        btnReservas.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReservasActivity.class);
            startActivity(intent);
        });

        MaterialButton btnNewReservas = findViewById(R.id.btnNewReservas);
        if (btnNewReservas != null) {
            btnNewReservas.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, com.example.dai_android_grupo_4.booking.ui.BookingActivity.class);
                startActivity(intent);
            });
        }

        MaterialButton btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });


        MaterialButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            tokenRepository.clearToken();
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "⭐ onStart: La Activity está a punto de hacerse visible");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "⭐ onResume: La Activity es visible y tiene el foco");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "⭐ onPause: La Activity está perdiendo el foco");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "⭐ onStop: La Activity ya no es visible");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "⭐ onRestart: La Activity está volviendo a empezar después de detenerse");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "⭐ onDestroy: La Activity está siendo destruida");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "⭐ onSaveInstanceState: Guardando el estado de la Activity");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "⭐ onRestoreInstanceState: Restaurando el estado guardado de la Activity");
    }
}
