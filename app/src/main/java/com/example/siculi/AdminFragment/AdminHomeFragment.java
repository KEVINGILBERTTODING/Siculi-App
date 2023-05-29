package com.example.siculi.AdminFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    TextView tvName;
    SharedPreferences sharedPreferences;
    AdminInterface adminInterface;
    TextView tvTotalCutiSetuju, tvTotalCutiDiTolak, tvTotalCutiTangguhkan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
       sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);

       tvName = view.findViewById(R.id.tvName);
       tvName.setText("Hi, " +sharedPreferences.getString("nama", null));
       adminInterface = DataApi.getClient().create(AdminInterface.class);
       tvTotalCutiSetuju = view.findViewById(R.id.tvCutiSetuju);
       tvTotalCutiDiTolak = view.findViewById(R.id.tvCutiTolak);
       tvTotalCutiTangguhkan = view.findViewById(R.id.tvDitangguhkan);
       getTotalAlCuti("Disetujui", tvTotalCutiSetuju);
       getTotalAlCuti("Ditangguhkan", tvTotalCutiTangguhkan);
       getTotalAlCuti("Ditolak", tvTotalCutiDiTolak);


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
}