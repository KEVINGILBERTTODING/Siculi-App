package com.example.siculi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.siculi.AdminFragment.AdminCutiKaryawanFragment;
import com.example.siculi.AdminFragment.AdminHomeFragment;
import com.example.siculi.AdminFragment.AdminIzinKaryawanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminMainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuHome) {
                    replace(new AdminHomeFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuCuti) {
                    replace(new AdminCutiKaryawanFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuIzin) {
                    replace(new AdminIzinKaryawanFragment());
                    return true;
                }else if (item.getItemId() == R.id.menuProfile) {
                    replace(new AdminCutiKaryawanFragment());
                    return true;
                }
                return false;
            }
        });
        replace(new AdminHomeFragment());


    }

    private void replace(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment)
                .commit();
    }
}