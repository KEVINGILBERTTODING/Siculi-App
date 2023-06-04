package com.example.siculi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.siculi.KetuaFragment.KetuaDaftarIzinKaryawanFragment;
import com.example.siculi.KetuaFragment.KetuaHomeFragment;
import com.example.siculi.KetuaFragment.KetuaPengajuanCutiKaryawanFragment;
import com.example.siculi.KetuaFragment.KetuaProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class KetuaMainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ketua_main);
        bottomNavigationView = findViewById(R.id.bottomBar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new KetuaHomeFragment());
                    return true;
                }else  if (item.getItemId() == R.id.menuCuti) {
                    replace(new KetuaPengajuanCutiKaryawanFragment());
                    return true;
                }else  if (item.getItemId() == R.id.menuIzin) {
                    replace(new KetuaDaftarIzinKaryawanFragment());
                    return true;
                }else  if (item.getItemId() == R.id.menuProfile) {
                replace(new KetuaProfileFragment());
                return true;
            }
                return false;
            }

        });
        replace(new KetuaHomeFragment());
    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameKetua, fragment).commit();
    }
}