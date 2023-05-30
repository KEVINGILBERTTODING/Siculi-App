package com.example.siculi.AdminFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.siculi.Model.AdminModel;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeFragment extends Fragment {
    TextView tvName, tvEmail;
    ImageView ivProfile;
    SharedPreferences sharedPreferences;
    AdminInterface adminInterface;
    ImageButton btnKaryawan, btnAtasan, btnCuti, btnCutiAtasan, btnIzinKaryawan, btnIzinAtasan;
    String userId;
    TextView tvTotalCutiSetuju, tvTotalCutiDiTolak, tvTotalCutiTangguhkan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
       sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);

       tvEmail = view.findViewById(R.id.tvEmail);
       ivProfile = view.findViewById(R.id.ivProfile);
       tvName = view.findViewById(R.id.tvName);
       tvName.setText("Hi, " +sharedPreferences.getString("nama", null));
       adminInterface = DataApi.getClient().create(AdminInterface.class);
       tvTotalCutiSetuju = view.findViewById(R.id.tvCutiSetuju);
       tvTotalCutiDiTolak = view.findViewById(R.id.tvCutiTolak);
       btnIzinAtasan = view.findViewById(R.id.btnIzinAtasan);
       btnIzinKaryawan = view.findViewById(R.id.btnIzinKaryawan);
       tvTotalCutiTangguhkan = view.findViewById(R.id.tvDitangguhkan);
       btnKaryawan = view.findViewById(R.id.btnKaryawan);
       btnAtasan = view.findViewById(R.id.btnAtasan);
       btnCuti = view.findViewById(R.id.btnCutiKaryawan);
       btnCutiAtasan = view.findViewById(R.id.btnCutiAtasan);
       userId = sharedPreferences.getString("user_id", null);

       getTotalAlCuti("Disetujui", tvTotalCutiSetuju);
       getTotalAlCuti("Ditangguhkan", tvTotalCutiTangguhkan);
       getTotalAlCuti("Ditolak", tvTotalCutiDiTolak);

       getMyProfile();
       btnKaryawan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               replace(new AdminKaryawanFragment());
           }
       });

        btnAtasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminAtasanFragment());
            }
        });


        btnCuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminCutiKaryawanFragment());
            }
        });

        btnCutiAtasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminCutiAtasanFragment());
            }
        });

        btnIzinKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminIzinKaryawanFragment());
            }
        });

        btnIzinAtasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminIzinAtasanFragment());
            }
        });
       return view;
    }

    private void getTotalAlCuti(String status, TextView tvTotal) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getAllCuti(status).enqueue(new Callback<List<CutiModel>>() {
            @Override
            public void onResponse(Call<List<CutiModel>> call, Response<List<CutiModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    pd.dismiss();
                    tvTotal.setText(response.body().size() + " Cuti");

                }else {
                    pd.dismiss();
                    tvTotal.setText("0 Cuti");
                }
            }

            @Override
            public void onFailure(Call<List<CutiModel>> call, Throwable t) {
                pd.dismiss();
                System.err.println(Toasty.error(getContext(), "Gagal memuat data total cuti", Toasty.LENGTH_SHORT));
                tvTotal.setText("0 Cuti");

            }
        });


    }
    private void getMyProfile() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getMyProfile(userId).enqueue(new Callback<AdminModel>() {
            @Override
            public void onResponse(Call<AdminModel> call, Response<AdminModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pd.dismiss();
                    tvEmail.setText(response.body().getEmail());
                    Glide.with(getContext())
                            .load(response.body().getFoto())
                            .dontAnimate()
                            .skipMemoryCache(true)
                            .fitCenter()
                            .centerCrop()
                            .override(200, 200)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(ivProfile);



                }else {
                    pd.dismiss();
                    System.err.println(Toasty.error(getContext(), "Gagal memuat data", Toasty.LENGTH_SHORT));
                }
            }

            @Override
            public void onFailure(Call<AdminModel> call, Throwable t) {
                pd.dismiss();
                System.err.println(Toasty.error(getContext(), "Gagal memuat data total cuti", Toasty.LENGTH_SHORT));


            }
        });


    }

    private void replace(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdmin, fragment)
                .addToBackStack(null).commit();
    }
}