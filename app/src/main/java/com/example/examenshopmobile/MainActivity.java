package com.example.examenshopmobile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private NavigationBarView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_profile) {
                    selectedFragment = new ProfileFragment();
                } else if (itemId == R.id.navigation_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.navigation_cart) {
                    selectedFragment = new CartFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });

        // Показать главный фрагмент по умолчанию
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
    }

    public void switchToProfileTab() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
    }
}
