package com.example.siculi.AdminFragment;

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
import com.example.siculi.Model.AtasanModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDetailAtasanFragment extends Fragment {
    ImageView ivProfile;
    TextView tvNama, tvEmail, etStatus;
    EditText etNip, etJabatan, etUnitKerja;
    Button btnEdit, btnKembali;
    String atasanId;
    AdminInterface adminInterface;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_detail_atasan, container, false);
        ivProfile = view.findViewById(R.id.ivProfile);
        tvNama = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        etNip = view.findViewById(R.id.etNip);
        etJabatan = view.findViewById(R.id.etJabatan);
        etUnitKerja = view.findViewById(R.id.etUnitKerja);
        etStatus = view.findViewById(R.id.etStatus);
        btnEdit = view.findViewById(R.id.btnUbah);
        adminInterface = DataApi.getClient().create(AdminInterface.class);
        btnKembali = view.findViewById(R.id.btnKembali);



        atasanId = getArguments().getString("atasan_id");



        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AdminEditPegawaiFragment();
                Bundle bundle = new Bundle();
                bundle.putString("atasan_id", atasanId);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameAdmin, fragment).addToBackStack(null)
                        .commit();
            }
        });

        getAtasanDetail();






        return view;
    }

    private void getAtasanDetail() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getAtasanById(atasanId).enqueue(new Callback<AtasanModel>() {
            @Override
            public void onResponse(Call<AtasanModel> call, Response<AtasanModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvNama.setText(response.body().getNama());
                    tvEmail.setText(response.body().getEmail());
                    etNip.setText(response.body().getNik());
                    etJabatan.setText(response.body().getJabatan());
                    etUnitKerja.setText(response.body().getGolongan());
                    etStatus.setText(response.body().getStatus());

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
            public void onFailure(Call<AtasanModel> call, Throwable t) {
                pd.dismiss();
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

            }
        });


    }
}