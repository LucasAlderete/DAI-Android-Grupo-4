package com.example.dai_android_grupo_4.booking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.booking.ui.fragments.BookingHistoryFragment;
import com.example.dai_android_grupo_4.booking.ui.fragments.BookingListFragment;
import com.example.dai_android_grupo_4.lessons.ui.LessonActivity;
import com.example.dai_android_grupo_4.profile.ui.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookingActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        setupBottomNavigation();

        // Cargar el fragment inicial basado en el parámetro recibido
        if (savedInstanceState == null) {
            String selectedTab = getIntent().getStringExtra("selected_tab");
            if ("history".equals(selectedTab)) {
                loadFragment(new BookingHistoryFragment());
                bottomNavigationView.setSelectedItemId(R.id.nav_booking_history);
            } else {
                loadFragment(new BookingListFragment());
                bottomNavigationView.setSelectedItemId(R.id.nav_my_bookings);
            }
        }
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, LessonActivity.class));
            return true;
        } else if (itemId == R.id.nav_my_bookings) {
            selectedFragment = new BookingListFragment();
        } else if (itemId == R.id.nav_booking_history) {
            selectedFragment = new BookingHistoryFragment();
        } else if (itemId == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }

        if (selectedFragment != null) {
            loadFragment(selectedFragment);
            return true;
        }

        return false;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        // No agregar al back stack para evitar problemas de navegación
        // transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        // Como no usamos back stack para fragments, simplemente cerrar la activity
        super.onBackPressed();
    }
}
