package com.example.siculi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.siculi.Model.AuthModel;
import com.example.siculi.Util.AuthInterface;
import com.example.siculi.Util.DataApi;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivty extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin;
    AuthInterface authInterface;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        authInterface = DataApi.getClient().create(AuthInterface.class);
        sharedPreferences = getSharedPreferences("data_user", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("logged_in", false)) {
            if (sharedPreferences.getString("role", null).equals("3")) {
                startActivity(new Intent(LoginActivty.this, KaryawanMainActivity.class));
                finish();
            }
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Email tidak boleh kosong");
                    etEmail.requestFocus();
                }else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Password tidak boleh kosong");
                    etPassword.requestFocus();
                }else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivty.this);
                    alert.setTitle("Login").setMessage("Authentification...").setCancelable(false);
                    AlertDialog pd = alert.create();
                    pd.show();

                    authInterface.login(
                            etEmail.getText().toString(),
                            etPassword.getText().toString()
                    ).enqueue(new Callback<AuthModel>() {
                        @Override
                        public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                            if (response.isSuccessful() && response.body().getStatus() == 200) {
                                pd.dismiss();

                                if (response.body().getRole().equals("3")) { // karyawan
                                    editor.putBoolean("logged_in", true);
                                    editor.putString("user_id", response.body().getUserId());
                                    editor.putString("role", response.body().getRole());
                                    editor.putInt("atasan", response.body().getAtasan());
                                    editor.apply();
                                    startActivity(new Intent(LoginActivty.this, KaryawanMainActivity.class));
                                    finish();
                                }


                            }else {
                                pd.dismiss();
                                Toasty.error(LoginActivty.this, response.body().getMessage(), Toasty.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<AuthModel> call, Throwable t) {
                            pd.dismiss();
                            Toasty.error(LoginActivty.this, "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

                        }
                    });
                }





            }
        });

    }
}