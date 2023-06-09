package com.example.siculi.AtasanFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siculi.AdminAdapter.AdminKaryawanAdapter;
import com.example.siculi.AdminFragment.AdminInsertPegawaiFragment;
import com.example.siculi.AtasanAdapter.AtasanKaryawanAdapter;
import com.example.siculi.Model.KaryawanModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.AtasanInterface;
import com.example.siculi.Util.DataApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtasanKaryawanFragment extends Fragment {
    RecyclerView rvKaryawan;
    List<KaryawanModel> karyawanModelList;
    LinearLayoutManager linearLayoutManager;
    AtasanKaryawanAdapter atasanKaryawanAdapter;
    AdminInterface adminInterface;
    AtasanInterface atasanInterface;
    SharedPreferences sharedPreferences;
    TextView tvEmpty;
    SearchView searchBar;
    FloatingActionButton btnInsert;
    String userId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atasan_karyawan, container, false);
        adminInterface = DataApi.getClient().create(AdminInterface.class);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        rvKaryawan = view.findViewById(R.id.rvKaryawan);
        searchBar = view.findViewById(R.id.searchBar);
        btnInsert = view.findViewById(R.id.btnKaryawan);
        sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        atasanInterface = DataApi.getClient().create(AtasanInterface.class);
        userId = sharedPreferences.getString("user_id", null);

        getDataKaryawan();

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameAdmin, new AdminInsertPegawaiFragment())
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void getDataKaryawan() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        atasanInterface.getAllKaryawan(userId).enqueue(new Callback<List<KaryawanModel>>() {
            @Override
            public void onResponse(Call<List<KaryawanModel>> call, Response<List<KaryawanModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    karyawanModelList = response.body();
                    atasanKaryawanAdapter = new AtasanKaryawanAdapter(getContext(), karyawanModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvKaryawan.setLayoutManager(linearLayoutManager);
                    rvKaryawan.setAdapter(atasanKaryawanAdapter);
                    rvKaryawan.setHasFixedSize(true);
                    tvEmpty.setVisibility(View.GONE);

                    pd.dismiss();
                }else {
                    pd.dismiss();
                    tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<KaryawanModel>> call, Throwable t) {
                pd.dismiss();
                tvEmpty.setVisibility(View.VISIBLE);
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnInsert.setVisibility(View.GONE);
    }

    private void filter(String text) {
        ArrayList filteredList = new ArrayList<>();
        for (KaryawanModel item : karyawanModelList) {
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        atasanKaryawanAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
        }else {
            atasanKaryawanAdapter.filter(filteredList);
        }
    }
}