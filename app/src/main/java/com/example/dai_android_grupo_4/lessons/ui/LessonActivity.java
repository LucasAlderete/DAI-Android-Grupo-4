package com.example.dai_android_grupo_4.lessons.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.booking.ui.BookingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LessonActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clases);

        initViews();
        setupBottomNavigation();
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Ya estamos aquí
                return true;
            } else if (itemId == R.id.nav_my_bookings) {
                Intent intent = new Intent(this, BookingActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_booking_history) {
                Intent intent = new Intent(this, BookingActivity.class);
                intent.putExtra("selected_tab", "history");
                startActivity(intent);
                return true;
            } else if (itemId == R.id.nav_profile) {
                // TODO: Implementar navegación a Mi Perfil más tarde
                return true;
            }
            return false;
        });
    }
}