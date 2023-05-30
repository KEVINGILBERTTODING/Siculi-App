package com.example.siculi.KaryawanFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.siculi.AdminFragment.AdminAtasanFragment;
import com.example.siculi.AdminFragment.AdminCutiAtasanFragment;
import com.example.siculi.AdminFragment.AdminCutiKaryawanFragment;
import com.example.siculi.AdminFragment.AdminIzinAtasanFragment;
import com.example.siculi.AdminFragment.AdminIzinKaryawanFragment;
import com.example.siculi.AdminFragment.AdminKaryawanFragment;
import com.example.siculi.AdminFragment.AdminProfileFragment;
import com.example.siculi.Model.AdminModel;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.Model.KaryawanModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;
import com.example.siculi.Util.KaryawanInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryawanHomeFragment extends Fragment {
    TextView tvName, tvEmail;
    ImageView ivProfile;
    SharedPreferences sharedPreferences;
    KaryawanInterface karyawanInterface;
    ImageButton btnCutiKaryawan, btnIzinKaryawan;
    String userId;
    TextView tvTotalCutiSetuju, tvTotalCutiDiTolak, tvTotalCutiTangguhkan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_karyawan_home, container, false);
       sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);

       tvEmail = view.findViewById(R.id.tvEmail);
       ivProfile = view.findViewById(R.id.ivProfile);
       tvName = view.findViewById(R.id.tvName);

       tvTotalCutiSetuju = view.findViewById(R.id.tvCutiSetuju);
       tvTotalCutiDiTolak = view.findViewById(R.id.tvCutiTolak);
       btnIzinKaryawan = view.findViewById(R.id.btnIzinKaryawan);
       btnCutiKaryawan = view.findViewById(R.id.btnCutiKaryawan);
       tvTotalCutiTangguhkan = view.findViewById(R.id.tvDitangguhkan);
       userId = sharedPreferences.getString("user_id", null);
       karyawanInterface = DataApi.getClient().create(KaryawanInterface.class);

       getTotalAlCuti("Disetujui", tvTotalCutiSetuju);
       getTotalAlCuti("Ditangguhkan", tvTotalCutiTangguhkan);
       getTotalAlCuti("Ditolak", tvTotalCutiDiTolak);

       getMyProfile();


        btnIzinKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new KaryawanIzinFragment());
            }
        });

        btnCutiKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new KaryawanCutiFragment());
            }
        });


        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminProfileFragment());
            }
        });
       return view;
    }

    private void getTotalAlCuti(String status, TextView tvTotal) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        karyawanInterface.getCutiByStatus(userId, status).enqueue(new Callback<List<CutiModel>>() {
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

        karyawanInterface.getMyProfile(userId).enqueue(new Callback<KaryawanModel>() {
            @Override
            public void onResponse(Call<KaryawanModel> call, Response<KaryawanModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pd.dismiss();
                    tvEmail.setText(response.body().getEmail());
                    tvName.setText("Hai, " + response.body().getNama());
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
            public void onFailure(Call<KaryawanModel> call, Throwable t) {
                pd.dismiss();
                System.err.println(Toasty.error(getContext(), "Gagal memuat data total cuti", Toasty.LENGTH_SHORT));


            }
        });


    }

    private void replace(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameKaryawan, fragment)
                .addToBackStack(null).commit();
    }
}