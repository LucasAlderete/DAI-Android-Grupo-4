package com.example.dai_android_grupo_4.booking.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.dai_android_grupo_4.R;
import com.example.dai_android_grupo_4.booking.ui.fragments.BookingListFragment;
import com.example.dai_android_grupo_4.booking.ui.fragments.BookingHistoryFragment;
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
        
        // Cargar el fragment inicial
        if (savedInstanceState == null) {
            loadFragment(new BookingListFragment());
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
        if (itemId == R.id.nav_my_bookings) {
            selectedFragment = new BookingListFragment();
        } else if (itemId == R.id.nav_booking_history) {
            selectedFragment = new BookingHistoryFragment();
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
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
