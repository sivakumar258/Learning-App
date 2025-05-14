package com.example.mytrail1;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;
import android.view.WindowManager;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Make the app full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initialize BottomNavigationView
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Retrieve data from Intent
        String emailid = getIntent().getStringExtra("emailid");
        String name = getIntent().getStringExtra("name");

        // Load the default fragment (HomeFragment)
        loadFragment(new HomeFragment(), null);

        // Set a listener for item selections
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                Bundle bundle = null;

                // Check which item was clicked and assign the corresponding fragment
                if (item.getItemId() == R.id.home) {
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.category) {
                    selectedFragment = new CategoryFragment();
                } else if (item.getItemId() == R.id.resources) {
                    selectedFragment = new ResourcesFragment();
                } else if (item.getItemId() == R.id.profile) {
                    selectedFragment = new ProfileFragment();

                    // Pass emailid and name to ProfileFragment
                    bundle = new Bundle();
                    bundle.putString("emailid", emailid);
                    bundle.putString("name", name);
                }

                // Replace the current fragment with the selected fragment
                loadFragment(selectedFragment, bundle);
                return true;
            }
        });
    }

    // Method to load a fragment with optional bundle
    private void loadFragment(Fragment fragment, Bundle bundle) {
        if (fragment != null) {
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
            fragmentTransaction.commit();
        }
    }
}
