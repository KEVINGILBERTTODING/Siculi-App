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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.siculi.AdminAdapter.AdminCutiKaryawanAdapter;
import com.example.siculi.AdminAdapter.AdminKaryawanAdapter;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.Model.KaryawanModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCutiKaryawanFragment extends Fragment {

    RecyclerView rvCuti;
    SearchView searchView;
    AdminCutiKaryawanAdapter adminCutiKaryawanAdapter;
    List<CutiModel> cutiModelList;
    AdminInterface adminInterface;
    LinearLayoutManager linearLayoutManager;
    TextView tvEmpty;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_cuti_karyawan, container, false);

        tvEmpty = view.findViewById(R.id.tvEmpty);
        searchView = view.findViewById(R.id.searchBar);
        rvCuti = view.findViewById(R.id.rvCuti);
        adminInterface = DataApi.getClient().create(AdminInterface.class);

        getDataCutiKaryawan();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        return view;
    }

    private void getDataCutiKaryawan(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getAllCutiKaryawan().enqueue(new Callback<List<CutiModel>>() {
            @Override
            public void onResponse(Call<List<CutiModel>> call, Response<List<CutiModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    cutiModelList = response.body();
                    adminCutiKaryawanAdapter = new AdminCutiKaryawanAdapter(getContext(), cutiModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    rvCuti.setLayoutManager(linearLayoutManager);
                    rvCuti.setAdapter(adminCutiKaryawanAdapter);
                    rvCuti.setHasFixedSize(true);
                    tvEmpty.setVisibility(View.GONE);
                    pd.dismiss();
                }else {
                    pd.dismiss();
                    tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<CutiModel>> call, Throwable t) {
                pd.dismiss();
                tvEmpty.setVisibility(View.VISIBLE);
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

            }
        });
    }

    private void filter(String text) {
        ArrayList filteredList = new ArrayList<>();
        for (CutiModel item : cutiModelList) {
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adminCutiKaryawanAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
        }else {
            adminCutiKaryawanAdapter.filter(filteredList);
        }
    }
}