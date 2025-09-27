package com.example.dai_android_grupo_4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dai_android_grupo_4.profile.ui.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ActivityLifecycle";

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "⭐ onCreate: La Activity está siendo creada");
        setContentView(R.layout.activity_main);

        setupBottomNavigation();

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
        
        // Sincronizar la navbar para asegurar que "Home" esté seleccionado
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
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

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        
        // Establecer "Home" como seleccionado por defecto en MainActivity
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            // Ya estamos en Home, mantener seleccionado
            return true;
        } else if (itemId == R.id.nav_my_bookings) {
            // Navegar a la actividad de reservas
            Intent intent = new Intent(MainActivity.this, com.example.dai_android_grupo_4.booking.ui.BookingActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.nav_booking_history) {
            // Navegar a la actividad de reservas con el historial seleccionado
            Intent intent = new Intent(MainActivity.this, com.example.dai_android_grupo_4.booking.ui.BookingActivity.class);
            intent.putExtra("selected_tab", "history");
            startActivity(intent);
            return true;
        } else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity.this, com.example.dai_android_grupo_4.profile.ui.ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        
        return false;
    }
}