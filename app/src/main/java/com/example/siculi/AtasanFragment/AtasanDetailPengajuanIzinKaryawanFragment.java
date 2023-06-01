package com.example.siculi.AtasanFragment;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.siculi.Model.ResponseModel;
import com.example.siculi.R;
import com.example.siculi.Util.AtasanInterface;
import com.example.siculi.Util.DataApi;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtasanDetailPengajuanIzinKaryawanFragment extends Fragment {
    CardView cvStatus;
    TextView tvStatus;
    EditText etNip, etNama, etJamMulai, etJamSelesai, etJenisLIzin,
    etKeperluan, etTglIzin;
    Button btnKembali, btnSetuju, btnTolak;
    String idIzin, userId;
    SharedPreferences sharedPreferences;
    AtasanInterface atasanInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atasan_detail_pengajuan_izin_fragment, container, false);

        cvStatus = view.findViewById(R.id.cvStatus);
        tvStatus = view.findViewById(R.id.tvStatus);
        etNip = view.findViewById(R.id.etNip);
        etNama = view.findViewById(R.id.etNama);
        etJamMulai = view.findViewById(R.id.etJamMulai);
        etJamSelesai = view.findViewById(R.id.etJamSelesai);
        etJenisLIzin = view.findViewById(R.id.etJenisIzin);
        etKeperluan = view.findViewById(R.id.etKeperluan);
        etTglIzin = view.findViewById(R.id.etTglIzin);
        btnSetuju = view.findViewById(R.id.btnSetuju);
        btnTolak = view.findViewById(R.id.btnTolak);
        btnKembali = view.findViewById(R.id.btnKembali);
        atasanInterface = DataApi.getClient().create(AtasanInterface.class);
        sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);

        idIzin = getArguments().getString("id");
        etNama.setText(getArguments().getString("nama"));
        etNip.setText(getArguments().getString("nip"));
        etJamMulai.setText(getArguments().getString("jam_mulai"));
        etJamSelesai.setText(getArguments().getString("jam_selesai"));
        etJenisLIzin.setText(getArguments().getString("jenis"));
        etKeperluan.setText(getArguments().getString("keperluan"));
        tvStatus.setText(getArguments().getString("status"));
        etTglIzin.setText(getArguments().getString("tgl_izin"));

        btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateIzin("Ditolak");
            }
        });

        btnSetuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateIzin("Disetujui");
            }
        });




        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void validateIzin(String status) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Validasi Izin...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        atasanInterface.validateIzinKaryawan(idIzin, status).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getStatus() == 200) {
                    pd.dismiss();
                    Toasty.success(getContext(), "Berhasil validasi izin", Toasty.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }else {
                    pd.dismiss();
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.dismiss();
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
            }
        });
    }
}