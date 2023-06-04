package com.example.siculi.AtasanFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.siculi.AdminFragment.AdminEditPegawaiFragment;
import com.example.siculi.Model.KaryawanModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtasanDetailKaryawanFragment extends Fragment {
    ImageView ivProfile;
    TextView tvNama, tvEmail, tvSisaCuti;
    EditText etNip, etMasukKerja, etTelepon, etJabatan, etNamaAtasan,
    etUnitKerja, etStatus, etAlamat;
    Button btnEdit, btnKembali;
    String karyawanId;
    AdminInterface adminInterface;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_karyawan, container, false);
        ivProfile = view.findViewById(R.id.ivProfile);
        tvNama = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvSisaCuti = view.findViewById(R.id.tvSisaCuti);
        etNip = view.findViewById(R.id.etNip);
        etMasukKerja = view.findViewById(R.id.etMasukKerja);
        etTelepon = view.findViewById(R.id.etTelepon);
        etJabatan = view.findViewById(R.id.etJabatan);
        etNamaAtasan = view.findViewById(R.id.etNamaAtasan);
        etUnitKerja = view.findViewById(R.id.etUnitKerja);
        etStatus = view.findViewById(R.id.etStatus);
        etAlamat = view.findViewById(R.id.etAlamat);
        btnEdit = view.findViewById(R.id.btnUbah);
        adminInterface = DataApi.getClient().create(AdminInterface.class);
        btnKembali = view.findViewById(R.id.btnKembali);



        karyawanId = getArguments().getString("karyawan_id");

        btnEdit.setVisibility(View.GONE);


        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        getKaryawanDetail();






        return view;
    }

    private void getKaryawanDetail() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getKaryawanById(karyawanId).enqueue(new Callback<KaryawanModel>() {
            @Override
            public void onResponse(Call<KaryawanModel> call, Response<KaryawanModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvNama.setText(response.body().getNama());
                    tvEmail.setText(response.body().getEmail());
                    tvSisaCuti.setText(response.body().getSisa_cuti());
                    etNip.setText(response.body().getNik());
                    etMasukKerja.setText(response.body().getMasuk_kerja());
                    etTelepon.setText(response.body().getTelp());
                    etJabatan.setText(response.body().getJabatan());
                    etNamaAtasan.setText(response.body().getNama_atasan());
                    etUnitKerja.setText(response.body().getGolongan());
                    etStatus.setText(response.body().getStatus());
                    etAlamat.setText(response.body().getAlamat());

                    Glide.with(getContext())
                            .load(response.body().getFoto())
                            .override(250, 250)
                            .skipMemoryCache(true)
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .centerCrop()
                            .fitCenter()
                            .into(ivProfile);
                    pd.dismiss();

                }else {
                    pd.dismiss();
                    Toasty.error(getContext(), "Gagal memuat data karyawan", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KaryawanModel> call, Throwable t) {
                pd.dismiss();
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

            }
        });


    }
}