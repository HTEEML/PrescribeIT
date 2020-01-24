package com.jarifjak.prescribeit.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jarifjak.prescribeit.fragment.DoctorFragment;
import com.jarifjak.prescribeit.fragment.MedicalFileFragment;
import com.jarifjak.prescribeit.fragment.SettingsFragment;
import com.jarifjak.prescribeit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DoctorFragment(),null).commit();
        bottomNavigation.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;

        switch (item.getItemId()) {

            case R.id.nav_doctor:
                selectedFragment = new DoctorFragment();
                break;

            case R.id.nav_medical_file:
                selectedFragment = new MedicalFileFragment();
                break;

            case R.id.nav_settings:
                selectedFragment = new SettingsFragment();
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment, null)
                .commit();

        return true;

    }

}
