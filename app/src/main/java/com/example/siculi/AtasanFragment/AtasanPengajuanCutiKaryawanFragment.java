package com.example.siculi.AtasanFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.siculi.AtasanAdapter.AtasanPengajuanCutiKaryawanAdapter;
import com.example.siculi.AtasanAdapter.AtasanPengajuanIzinAtasanAdapter;
import com.example.siculi.Model.AtasanModel;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.R;
import com.example.siculi.Util.AtasanInterface;
import com.example.siculi.Util.DataApi;
import com.example.siculi.Util.KaryawanInterface;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtasanPengajuanCutiKaryawanFragment extends Fragment {

    RecyclerView rvIzin;
    SearchView searchView;
    List<CutiModel> cutiModelList;
    KaryawanInterface karyawanInterface;
    LinearLayoutManager linearLayoutManager;
    SharedPreferences sharedPreferences;
    TextView tvEmpty;
    FloatingActionButton fabFilter, fabRiwayat;
    AtasanPengajuanCutiKaryawanAdapter atasanPengajuanCutiKaryawanAdapter;
    String userId, atasanId;
    AtasanInterface atasanInterface;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atasan_pengajuan_cuti_karyawan, container, false);
        sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);

        tvEmpty = view.findViewById(R.id.tvEmpty);
        searchView = view.findViewById(R.id.searchBar);
        rvIzin = view.findViewById(R.id.rvIzin);
        fabFilter = view.findViewById(R.id.btnFilter);
        fabRiwayat = view.findViewById(R.id.btnRiwayat);
        karyawanInterface = DataApi.getClient().create(KaryawanInterface.class);

        atasanInterface = DataApi.getClient().create(AtasanInterface.class);
        getCutiAtasan();

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

                            atasanInterface.filterAtasanPengajuanCutiKaryawan(
                                    userId,
                                    tvTglMulai.getText().toString(),
                                    tvTglSelesai.getText().toString()
                            ).enqueue(new Callback<List<CutiModel>>() {
                                @Override
                                public void onResponse(Call<List<CutiModel>> call, Response<List<CutiModel>> response) {
                                    if (response.isSuccessful() && response.body().size() > 0) {
                                        cutiModelList = response.body();
                                        atasanPengajuanCutiKaryawanAdapter = new AtasanPengajuanCutiKaryawanAdapter(getContext(), cutiModelList);
                                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                        rvIzin.setLayoutManager(linearLayoutManager);
                                        rvIzin.setAdapter(atasanPengajuanCutiKaryawanAdapter);
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

        fabRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameAtasan, new AtasanHistoryPengajuanCutiKaryawanFragment())
                        .addToBackStack(null).commit();

            }
        });



        return view;
    }

    private void getCutiAtasan(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        atasanInterface.getAtasanPengajuanCutiKaryawan(userId).enqueue(new Callback<List<CutiModel>>() {
            @Override
            public void onResponse(Call<List<CutiModel>> call, Response<List<CutiModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    cutiModelList = response.body();
                    atasanPengajuanCutiKaryawanAdapter = new AtasanPengajuanCutiKaryawanAdapter(getContext(), cutiModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    rvIzin.setLayoutManager(linearLayoutManager);
                    rvIzin.setAdapter(atasanPengajuanCutiKaryawanAdapter);
                    rvIzin.setHasFixedSize(true);
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

        atasanPengajuanCutiKaryawanAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
        }else {
            atasanPengajuanCutiKaryawanAdapter.filter(filteredList);
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
                } else {
                    monthFormatted = String.valueOf(month + 1);
                }

                if (dayOfMonth < 10) {
                    dateFormatted = String.format("%02d", dayOfMonth);
                } else {
                    dateFormatted = String.valueOf(dayOfMonth);
                }

                tvDate.setText(year + "-" + monthFormatted + "-" + dateFormatted);
            }
        });
        datePickerDialog.show();

    }

    private void timePicker(TextView tvTime) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
            String timeFormatted, minuteFormatted;
            if (hourOfDay < 10) {
                timeFormatted = String.format("%02d", hourOfDay);
            }else {
                timeFormatted = String.valueOf(hourOfDay);
            }

            if (minute < 10) {
                minuteFormatted = String.format("%02d", minute);
            }else {
                minuteFormatted = String.valueOf(minute);
            }

            tvTime.setText(timeFormatted + ":" + minuteFormatted);
        }, 0, 0, true);

        timePickerDialog.show();

    }




}