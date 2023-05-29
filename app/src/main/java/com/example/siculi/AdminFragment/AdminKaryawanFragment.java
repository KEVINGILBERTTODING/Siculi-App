package com.example.siculi.AdminFragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.siculi.AdminAdapter.AdminKaryawanAdapter;
import com.example.siculi.Model.KaryawanModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminKaryawanFragment extends Fragment {
    RecyclerView rvKaryawan;
    List<KaryawanModel> karyawanModelList;
    LinearLayoutManager linearLayoutManager;
    AdminKaryawanAdapter adminKaryawanAdapter;
    AdminInterface adminInterface;
    TextView tvEmpty;
    SearchView searchBar;
    FloatingActionButton btnInsert;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_karyawan, container, false);
        adminInterface = DataApi.getClient().create(AdminInterface.class);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        rvKaryawan = view.findViewById(R.id.rvKaryawan);
        searchBar = view.findViewById(R.id.searchBar);
        btnInsert = view.findViewById(R.id.btnKaryawan);
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

        adminInterface.getAllKaryawan().enqueue(new Callback<List<KaryawanModel>>() {
            @Override
            public void onResponse(Call<List<KaryawanModel>> call, Response<List<KaryawanModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    karyawanModelList = response.body();
                    adminKaryawanAdapter = new AdminKaryawanAdapter(getContext(), karyawanModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvKaryawan.setLayoutManager(linearLayoutManager);
                    rvKaryawan.setAdapter(adminKaryawanAdapter);
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

    private void filter(String text) {
        ArrayList filteredList = new ArrayList<>();
        for (KaryawanModel item : karyawanModelList) {
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adminKaryawanAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
        }else {
            adminKaryawanAdapter.filter(filteredList);
        }
    }
}