package com.example.siculi.AdminFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
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
import com.example.siculi.AdminAdapter.AdminIzinKaryawanAdapter;
import com.example.siculi.Model.IzinModel;
import com.example.siculi.Model.IzinModel;
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

public class AdminIzinKaryawanFragment extends Fragment {

    RecyclerView rvIzin;
    SearchView searchView;
    AdminIzinKaryawanAdapter adminIzinKaryawanAdapter;
    List<IzinModel> izinModelList;
    AdminInterface adminInterface;
    LinearLayoutManager linearLayoutManager;
    TextView tvEmpty;
    FloatingActionButton fabFilter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_izin_karyawan, container, false);

        tvEmpty = view.findViewById(R.id.tvEmpty);
        searchView = view.findViewById(R.id.searchBar);
        rvIzin = view.findViewById(R.id.rvIzin);
        fabFilter = view.findViewById(R.id.btnFilter);
        adminInterface = DataApi.getClient().create(AdminInterface.class);

        getDataIzinKaryawan();
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
                            rvIzin.setAdapter(null);
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
                            AlertDialog pd = alert.create();
                            pd.show();

                            adminInterface.filterDataIzinKaryawan(
                                    tvTglMulai.getText().toString(),
                                    tvTglSelesai.getText().toString()
                            ).enqueue(new Callback<List<IzinModel>>() {
                                @Override
                                public void onResponse(Call<List<IzinModel>> call, Response<List<IzinModel>> response) {
                                    if (response.isSuccessful() && response.body().size() > 0) {
                                        izinModelList = response.body();
                                        adminIzinKaryawanAdapter = new AdminIzinKaryawanAdapter(getContext(), izinModelList);
                                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                        rvIzin.setLayoutManager(linearLayoutManager);
                                        rvIzin.setAdapter(adminIzinKaryawanAdapter);
                                        dialogFilter.dismiss();
                                        rvIzin.setHasFixedSize(true);
                                        tvEmpty.setVisibility(View.GONE);
                                        pd.dismiss();
                                    }else {
                                        dialogFilter.dismiss();
                                        pd.dismiss();
                                        tvEmpty.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<IzinModel>> call, Throwable t) {
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

        return view;
    }

    private void getDataIzinKaryawan(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getAllIzinKaryawan().enqueue(new Callback<List<IzinModel>>() {
            @Override
            public void onResponse(Call<List<IzinModel>> call, Response<List<IzinModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    izinModelList = response.body();
                    adminIzinKaryawanAdapter = new AdminIzinKaryawanAdapter(getContext(), izinModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    rvIzin.setLayoutManager(linearLayoutManager);
                    rvIzin.setAdapter(adminIzinKaryawanAdapter);
                    rvIzin.setHasFixedSize(true);
                    tvEmpty.setVisibility(View.GONE);
                    pd.dismiss();
                }else {
                    pd.dismiss();
                    tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<IzinModel>> call, Throwable t) {
                pd.dismiss();
                tvEmpty.setVisibility(View.VISIBLE);
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

            }
        });
    }

    private void filter(String text) {
        ArrayList filteredList = new ArrayList<>();
        for (IzinModel item : izinModelList) {
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adminIzinKaryawanAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
        }else {
            adminIzinKaryawanAdapter.filter(filteredList);
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