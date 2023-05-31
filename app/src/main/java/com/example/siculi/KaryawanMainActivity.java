package com.example.siculi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.siculi.AdminFragment.AdminProfileFragment;
import com.example.siculi.KaryawanFragment.KaryawanCutiFragment;
import com.example.siculi.KaryawanFragment.KaryawanHomeFragment;
import com.example.siculi.KaryawanFragment.KaryawanIzinFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class KaryawanMainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karyawan_main);
        bottomNavigationView  = findViewById(R.id.bottomBar);

        replace(new KaryawanHomeFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new KaryawanHomeFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuIzin) {
                    replace(new KaryawanIzinFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuCuti) {
                    replace(new KaryawanCutiFragment());
                    return true;
                }
                return false;
            }
        });

    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameKaryawan, fragment)
                .commit();
    }
}