package com.example.siculi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.siculi.KaryawanFragment.KaryawanHomeFragment;

public class KaryawanMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karyawan_main);

        replace(new KaryawanHomeFragment());

    }

    private void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameKaryawan, fragment)
                .commit();
    }
}