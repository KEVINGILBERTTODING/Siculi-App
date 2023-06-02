package com.example.siculi.AtasanFragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siculi.AtasanAdapter.AtasanHistoryPengajuanCutiKaryawanAdapter;
import com.example.siculi.AtasanAdapter.AtasanHistoryPengajuanIzinAtasanAdapter;
import com.example.siculi.Model.AtasanModel;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.R;
import com.example.siculi.Util.AtasanInterface;
import com.example.siculi.Util.DataApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtasanHistoryPengajuanCutiKaryawanFragment extends Fragment {

    RecyclerView rvIzin;
    SearchView searchView;

    List<CutiModel> cutiModelList;
    LinearLayoutManager linearLayoutManager;
    SharedPreferences sharedPreferences;
    TextView tvEmpty;
    FloatingActionButton fabFilter;

    AtasanHistoryPengajuanCutiKaryawanAdapter atasanHistoryPengajuanCutiKaryawanAdapter;
    String userId, atasanId;
    AtasanInterface atasanInterface;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atasan_history_pengajuan_cuti_karyawan, container, false);
        sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);

        tvEmpty = view.findViewById(R.id.tvEmpty);
        searchView = view.findViewById(R.id.searchBar);
        rvIzin = view.findViewById(R.id.rvIzin);
        fabFilter = view.findViewById(R.id.btnFilter);
        atasanInterface = DataApi.getClient().create(AtasanInterface.class);
        getDataCutiKaryawan();
        getMyProfile();

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
                dialogFilter.setContentView(R.layout.layout_filter_download);
                dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView tvTglMulai, tvTglSelesai;
                tvTglMulai = dialogFilter.findViewById(R.id.tvDateStart);
                tvTglSelesai = dialogFilter.findViewById(R.id.tvDateEnd);
                Button btnDownload = dialogFilter.findViewById(R.id.btnDownload);

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

                // download rekap laporan
                // download data
                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // cek izin mengakses file external
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                        } else if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                        }


                        if (tvTglMulai.getText().toString().isEmpty()) {
                            tvTglMulai.setError("Tanggal mulai tidak boleh kosong");
                            tvTglMulai.requestFocus();
                        }else if (tvTglSelesai.getText().toString().isEmpty()) {
                            tvTglSelesai.setError("Tanggal selesai tidak boleh kosong");
                            tvTglSelesai.requestFocus();
                        }else {
                            String url = DataApi.URL_DOWNLOAD_REKAP_LAPORAN_IZIN_ATASAN  + userId + "/" + tvTglMulai.getText().toString() + "/" + tvTglSelesai.getText().toString();
                            String title = "Rekap Pengajuan Izin Atasan " + tvTglMulai.getText().toString() + "-" + tvTglSelesai.getText().toString() + ".pdf";
                            String description = "Downloading PDF file";
                            String fileName = "Rekap Pengajuan Izin Atasan.pdf";

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));

                            startActivity(intent);


//                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                            request.setTitle(title);
//                            request.setDescription(description);
//                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                            request.allowScanningByMediaScanner();
//
//                            DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//                            downloadManager.enqueue(request);
                        }

                    }
                });


                // filter data
//                btnFilter.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (tvTglSelesai.getText().toString().isEmpty()) {
//                            tvTglSelesai.setError("Tanggal tidak boleh kosong");
//                            tvTglSelesai.requestFocus();
//                        }else if (tvTglMulai.getText().toString().isEmpty()) {
//                            tvTglMulai.setError("Tanggal tidak boleh kosong");
//                            tvTglMulai.requestFocus();
//                        }else {
//                            rvIzin.setAdapter(null);
//                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//                            alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
//                            AlertDialog pd = alert.create();
//                            pd.show();
//
//                            atasanInterface.filterHistoryPengajuanIzinAtasan(
//                                    userId,
//                                    tvTglMulai.getText().toString(),
//                                    tvTglSelesai.getText().toString()
//                            ).enqueue(new Callback<List<CutiModel>>() {
//                                @Override
//                                public void onResponse(Call<List<CutiModel>> call, Response<List<CutiModel>> response) {
//                                    if (response.isSuccessful() && response.body().size() > 0) {
//                                        cutiModelList = response.body();
//                                        atasanHistoryPengajuanCutiKaryawanAdapter = new AtasanHistoryPengajuanCutiKaryawanAdapter(getContext(), cutiModelList);
//                                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//                                        rvIzin.setLayoutManager(linearLayoutManager);
//                                        rvIzin.setAdapter(atasanHistoryPengajuanCutiKaryawanAdapter);
//                                        dialogFilter.dismiss();
//                                        rvIzin.setHasFixedSize(true);
//                                        tvEmpty.setVisibility(View.GONE);
//                                        pd.dismiss();
//                                    }else {
//                                        dialogFilter.dismiss();
//                                        pd.dismiss();
//                                        tvEmpty.setVisibility(View.VISIBLE);
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<List<CutiModel>> call, Throwable t) {
//                                    pd.dismiss();
//                                    tvEmpty.setVisibility(View.GONE);
//                                    Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
//
//                                }
//                            });
//                        }
//                    }
//                });

                dialogFilter.show();

            }
        });





        return view;
    }

    private void getDataCutiKaryawan(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        atasanInterface.getAtasanHistoryPengajuanCutiKaryawan(userId).enqueue(new Callback<List<CutiModel>>() {
            @Override
            public void onResponse(Call<List<CutiModel>> call, Response<List<CutiModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    cutiModelList = response.body();
                    atasanHistoryPengajuanCutiKaryawanAdapter = new AtasanHistoryPengajuanCutiKaryawanAdapter(getContext(), cutiModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    rvIzin.setLayoutManager(linearLayoutManager);
                    rvIzin.setAdapter(atasanHistoryPengajuanCutiKaryawanAdapter );
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

        atasanHistoryPengajuanCutiKaryawanAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
        }else {
            atasanHistoryPengajuanCutiKaryawanAdapter.filter(filteredList);
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

    private void getMyProfile() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        atasanInterface.getMyProfile(userId).enqueue(new Callback<AtasanModel>() {
            @Override
            public void onResponse(Call<AtasanModel> call, Response<AtasanModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pd.dismiss();
                   atasanId = response.body().getAtasan();



                }else {
                    pd.dismiss();
                    System.err.println(Toasty.error(getContext(), "Gagal memuat data", Toasty.LENGTH_SHORT));
                }
            }

            @Override
            public void onFailure(Call<AtasanModel> call, Throwable t) {
                pd.dismiss();
                System.err.println(Toasty.error(getContext(), "Gagal memuat data total cuti", Toasty.LENGTH_SHORT));


            }
        });


    }


}