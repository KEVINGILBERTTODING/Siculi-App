package com.example.siculi.KaryawanFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siculi.AdminAdapter.AdminCutiKaryawanAdapter;
import com.example.siculi.KaryawanAdapter.KaryawanCutiAdapter;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;
import com.example.siculi.Util.KaryawanInterface;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryawanCutiFragment extends Fragment {

    RecyclerView rvCuti;
    SearchView searchView;
    KaryawanCutiAdapter karyawanCutiAdapter;
    List<CutiModel> cutiModelList;
    SharedPreferences sharedPreferences;
    KaryawanInterface karyawanInterface;
    LinearLayoutManager linearLayoutManager;
    TextView tvEmpty;
    com.getbase.floatingactionbutton.FloatingActionButton  fabFilter, fabInsertCuti;
    String userId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karyawan_cuti, container, false);

        tvEmpty = view.findViewById(R.id.tvEmpty);
        searchView = view.findViewById(R.id.searchBar);
        rvCuti = view.findViewById(R.id.rvCuti);
        fabFilter = view.findViewById(R.id.btnFilter2);
        sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);
        fabInsertCuti = view.findViewById(R.id.fabInsertCuti);
        karyawanInterface = DataApi.getClient().create(KaryawanInterface.class);
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

        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogFilter = new Dialog(getContext());
                dialogFilter.setContentView(R.layout.layout_filter);
                dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView tvTglMulai, tvTglSelesai;
                tvTglMulai = dialogFilter.findViewById(R.id.tvDateStart);
                tvTglSelesai = dialogFilter.findViewById(R.id.tvDateEnd);

                tvTglSelesai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDatePicker(tvTglSelesai);
                    }
                });

                tvTglMulai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDatePicker(tvTglMulai);
                    }
                });
                Button btnFilter = dialogFilter.findViewById(R.id.btnFilter);
                btnFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvTglSelesai.getText().toString().isEmpty()) {
                            tvTglSelesai.setError("Tanggal tidak boleh kosong");
                            tvTglSelesai.requestFocus();
                        }else if (tvTglMulai.getText().toString().isEmpty()) {
                            tvTglMulai.setError("Tanggal tidak boleh kosong");
                            tvTglMulai.requestFocus();
                        }else {
                            rvCuti.setAdapter(null);
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
                            AlertDialog pd = alert.create();
                            pd.show();
                            karyawanInterface.filterMyCuti(
                                    userId,
                                    tvTglMulai.getText().toString(),
                                    tvTglSelesai.getText().toString()
                            ).enqueue(new Callback<List<CutiModel>>() {
                                @Override
                                public void onResponse(Call<List<CutiModel>> call, Response<List<CutiModel>> response) {
                                    if (response.isSuccessful() && response.body().size() > 0) {
                                        cutiModelList = response.body();
                                        karyawanCutiAdapter = new KaryawanCutiAdapter(getContext(), cutiModelList);
                                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                        rvCuti.setLayoutManager(linearLayoutManager);
                                        rvCuti.setAdapter(karyawanCutiAdapter);
                                        dialogFilter.dismiss();
                                        rvCuti.setHasFixedSize(true);
                                        tvEmpty.setVisibility(View.GONE);
                                        pd.dismiss();
                                    }else {
                                        dialogFilter.dismiss();
                                        pd.dismiss();
                                        tvEmpty.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<CutiModel>> call, Throwable t) {
                                    pd.dismiss();
                                    tvEmpty.setVisibility(View.GONE);
                                    Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                });

                dialogFilter.show();
            }
        });
        fabInsertCuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameKaryawan, new KaryawanInsertCuti()).addToBackStack(null).commit();
            }
        });



        return view;
    }

    private void getDataCutiKaryawan(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        karyawanInterface.getAllCuti(userId).enqueue(new Callback<List<CutiModel>>() {
            @Override
            public void onResponse(Call<List<CutiModel>> call, Response<List<CutiModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    cutiModelList = response.body();
                    karyawanCutiAdapter = new KaryawanCutiAdapter(getContext(), cutiModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    rvCuti.setLayoutManager(linearLayoutManager);
                    rvCuti.setAdapter(karyawanCutiAdapter);
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
            if (item.getJenisCuti().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        karyawanCutiAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
        }else {
            karyawanCutiAdapter.filter(filteredList);
        }
    }

    private void getDatePicker(TextView tvDate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateFormatted, monthFormatted;
                if (month < 10) {
                    monthFormatted = String.format("%02d", month + 1);
                }else {
                    monthFormatted = String.valueOf(month + 1);
                }

                if (dayOfMonth < 10) {
                    dateFormatted = String.format("%02d", dayOfMonth);
                }else {
                    dateFormatted = String.valueOf(dayOfMonth);
                }

                tvDate.setText(year + "-" + monthFormatted + "-" + dateFormatted);
            }
        });
        datePickerDialog.show();
    }
}