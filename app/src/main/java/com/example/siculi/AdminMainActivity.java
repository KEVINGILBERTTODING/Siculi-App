package com.example.siculi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.siculi.AdminFragment.AdminHomeFragment;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        replace(new AdminHomeFragment());
    }

    private void replace(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment)
                .commit();
    }
}