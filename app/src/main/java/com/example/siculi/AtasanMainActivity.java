package com.example.siculi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.siculi.AtasanFragment.AtasanCutiFragment;
import com.example.siculi.AtasanFragment.AtasanHomeFragment;
import com.example.siculi.AtasanFragment.AtasanIzinFragment;
import com.example.siculi.AtasanFragment.AtasanProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AtasanMainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atasan_main);
        bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new AtasanHomeFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuCuti) {
                    replace(new AtasanCutiFragment());
                    return true;
                } else if (item.getItemId() == R.id.menuIzin) {
                    replace(new AtasanIzinFragment());
                    return true;
                }

                else if (item.getItemId() == R.id.menuProfile) {
                    replace(new AtasanProfileFragment());
                    return true;
                }
                return false;
            }
        });

        replace(new AtasanHomeFragment());
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAtasan, fragment).commit();
    }
}