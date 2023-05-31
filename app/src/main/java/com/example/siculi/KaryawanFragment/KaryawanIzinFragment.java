package com.example.siculi.KaryawanFragment;

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
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siculi.KaryawanAdapter.KaryawanIzinAdapter;
import com.example.siculi.Model.IzinModel;
import com.example.siculi.Model.KaryawanModel;
import com.example.siculi.Model.ResponseModel;
import com.example.siculi.R;
import com.example.siculi.Util.DataApi;
import com.example.siculi.Util.KaryawanInterface;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryawanIzinFragment extends Fragment {

    RecyclerView rvIzin;
    SearchView searchView;
    KaryawanIzinAdapter karyawanIzinAdapter;
    List<IzinModel> izinModelList;
    KaryawanInterface karyawanInterface;
    LinearLayoutManager linearLayoutManager;
    SharedPreferences sharedPreferences;
    TextView tvEmpty;
    FloatingActionButton fabFilter, fabInsert;
    String userId, atasanId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karyawan_izin, container, false);
        sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);

        tvEmpty = view.findViewById(R.id.tvEmpty);
        searchView = view.findViewById(R.id.searchBar);
        rvIzin = view.findViewById(R.id.rvIzin);
        fabFilter = view.findViewById(R.id.btnFilter);
        fabInsert = view.findViewById(R.id.btnInsertIzin);
        karyawanInterface = DataApi.getClient().create(KaryawanInterface.class);

        getDataIzinKaryawan();
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

                            karyawanInterface.filterMyIzin(
                                    userId,
                                    tvTglMulai.getText().toString(),
                                    tvTglSelesai.getText().toString()
                            ).enqueue(new Callback<List<IzinModel>>() {
                                @Override
                                public void onResponse(Call<List<IzinModel>> call, Response<List<IzinModel>> response) {
                                    if (response.isSuccessful() && response.body().size() > 0) {
                                        izinModelList = response.body();
                                        karyawanIzinAdapter = new KaryawanIzinAdapter(getContext(), izinModelList);
                                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                        rvIzin.setLayoutManager(linearLayoutManager);
                                        rvIzin.setAdapter(karyawanIzinAdapter);
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

        fabInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogJenisIzin = new Dialog(getContext());
                dialogJenisIzin.setContentView(R.layout.layout_jenis_izin);
                dialogJenisIzin.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button btnIzinKeluarKantor, btnIzinKeluarCepat;
                btnIzinKeluarCepat = dialogJenisIzin.findViewById(R.id.btnIzinKeluarCepat);
                btnIzinKeluarKantor = dialogJenisIzin.findViewById(R.id.btnIzinKeluarKantor);
                btnIzinKeluarKantor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogJenisIzin.dismiss();
                        izinKeluarKantor();
                    }
                });
                btnIzinKeluarCepat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogJenisIzin.dismiss();
                        izinKeluarCepat();
                    }
                });
                dialogJenisIzin.show();
            }
        });



        return view;
    }

    private void getDataIzinKaryawan(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        karyawanInterface.getAllIzin(userId).enqueue(new Callback<List<IzinModel>>() {
            @Override
            public void onResponse(Call<List<IzinModel>> call, Response<List<IzinModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    izinModelList = response.body();
                    karyawanIzinAdapter = new KaryawanIzinAdapter(getContext(), izinModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    rvIzin.setLayoutManager(linearLayoutManager);
                    rvIzin.setAdapter(karyawanIzinAdapter);
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
            if (item.getTanggalIzin().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        karyawanIzinAdapter.filter(filteredList);
        if (filteredList.isEmpty()) {
        }else {
            karyawanIzinAdapter.filter(filteredList);
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

    private void izinKeluarKantor() {
        Dialog dialogIzinKeluarKantor = new Dialog(getContext());
        dialogIzinKeluarKantor.setContentView(R.layout.layout_izin_keluar_kantor);
        dialogIzinKeluarKantor.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnSubmit, btnBatal;
        TextView tvWaktuPergi, tvWaktuPulang;
        EditText etKeperluan;
        tvWaktuPulang = dialogIzinKeluarKantor.findViewById(R.id.tvWaktuPulang);
        tvWaktuPergi = dialogIzinKeluarKantor.findViewById(R.id.tvWaktuPergi);
        etKeperluan = dialogIzinKeluarKantor.findViewById(R.id.etKeperluan);
        btnBatal = dialogIzinKeluarKantor.findViewById(R.id.btnBatal);
        btnSubmit = dialogIzinKeluarKantor.findViewById(R.id.btnSubmit);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String waktuSekarang = simpleDateFormat.format(calendar.getTime());



        dialogIzinKeluarKantor.show();

        tvWaktuPergi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker(tvWaktuPergi);
            }
        });

        tvWaktuPulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker(tvWaktuPulang);
            }
        });


        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogIzinKeluarKantor.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvWaktuPergi.getText().toString().isEmpty()) {
                    tvWaktuPergi.setError("Waktu pergi tidak boleh kosong");
                    tvWaktuPergi.requestFocus();
                    return;
                }else if (tvWaktuPulang.getText().toString().isEmpty()) {
                    tvWaktuPulang.setError("Waktu pulang tidak boleh kosong");
                    tvWaktuPulang.requestFocus();
                    return;
                }else  if (etKeperluan.getText().toString().isEmpty()) {
                    etKeperluan.setError("Keperluan tidak boleh kosong");
                    etKeperluan.requestFocus();
                    return;
                }
                // jika waktu pergi lebih besar dibanding waktu pulang
                else if (tvWaktuPergi.getText().toString().compareTo(tvWaktuPulang.getText().toString()) > 0) {
                    tvWaktuPergi.setError("Waktu pergi tidak boleh lebih besar dari waktu pulang");
                    Toasty.error(getContext(), "Waktu pergi tidak boleh lebih besar dari waktu pulang", Toasty.LENGTH_SHORT).show();
                    tvWaktuPergi.requestFocus();
                    return;
                }
                // jika waktu pergi lebih kecil dari waktu sekarang
                else if (tvWaktuPergi.getText().toString().compareTo(waktuSekarang) < 0) {
                    tvWaktuPergi.setError("Waktu pergi tidak boleh lebih kecil dari waktu sekarang");
                    Toasty.error(getContext(), "Waktu pergi tidak boleh lebih kecil dari waktu sekarang", Toasty.LENGTH_SHORT).show();
                    tvWaktuPergi.requestFocus();
                    return;
                }
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Loading").setMessage("Menyimpan data...").setCancelable(false);
                    AlertDialog pd = alert.create();
                    pd.show();

                    HashMap map = new HashMap();
                    map.put("waktu_pergi", RequestBody.create(MediaType.parse("text/plain"), tvWaktuPergi.getText().toString()));
                    map.put("waktu_pulang", RequestBody.create(MediaType.parse("text/plain"), tvWaktuPulang.getText().toString()));
                    map.put("jenis", RequestBody.create(MediaType.parse("text/plain"), "Normal"));
                    map.put("user_id", RequestBody.create(MediaType.parse("text/plain"), userId));
                    map.put("atasan_id", RequestBody.create(MediaType.parse("text/plain"), atasanId));
                    map.put("keperluan", RequestBody.create(MediaType.parse("text/plain"), etKeperluan.getText().toString()));

                    karyawanInterface.insertIzin(map).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.isSuccessful() && response.body().getStatus() == 200) {
                                Toasty.success(getContext(), "Berhasil mengajukan izin", Toasty.LENGTH_SHORT).show();
                                getDataIzinKaryawan();
                                pd.dismiss();
                                dialogIzinKeluarKantor.dismiss();
                            }else {
                                Toasty.error(getContext(), "Gagal mengajukan izin", Toasty.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });
                }
            }
        });
    }
    private void izinKeluarCepat() {
        Dialog dialogIzinKeluarKantor = new Dialog(getContext());
        dialogIzinKeluarKantor.setContentView(R.layout.layout_izin_keluar_cepat);
        dialogIzinKeluarKantor.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnSubmit, btnBatal;
        TextView tvWaktuPergi;
        EditText etKeperluan;
        tvWaktuPergi = dialogIzinKeluarKantor.findViewById(R.id.tvWaktuPergi);
        etKeperluan = dialogIzinKeluarKantor.findViewById(R.id.etKeperluan);
        btnBatal = dialogIzinKeluarKantor.findViewById(R.id.btnBatal);
        btnSubmit = dialogIzinKeluarKantor.findViewById(R.id.btnSubmit);

        dialogIzinKeluarKantor.show();

        tvWaktuPergi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker(tvWaktuPergi);
            }
        });




        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogIzinKeluarKantor.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvWaktuPergi.getText().toString().isEmpty()) {
                    tvWaktuPergi.setError("Waktu pergi tidak boleh kosong");
                    tvWaktuPergi.requestFocus();
                    return;
                }else  if (etKeperluan.getText().toString().isEmpty()) {
                    etKeperluan.setError("Keperluan tidak boleh kosong");
                    etKeperluan.requestFocus();
                    return;
                }else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Loading").setMessage("Menyimpan data...").setCancelable(false);
                    AlertDialog pd = alert.create();
                    pd.show();

                    HashMap map = new HashMap();
                    map.put("waktu_pergi", RequestBody.create(MediaType.parse("text/plain"), tvWaktuPergi.getText().toString()));
                    map.put("waktu_pulang", RequestBody.create(MediaType.parse("text/plain"), ""));
                    map.put("jenis", RequestBody.create(MediaType.parse("text/plain"), "Cepat"));
                    map.put("user_id", RequestBody.create(MediaType.parse("text/plain"), userId));
                    map.put("atasan_id", RequestBody.create(MediaType.parse("text/plain"), atasanId));
                    map.put("keperluan", RequestBody.create(MediaType.parse("text/plain"), etKeperluan.getText().toString()));

                    karyawanInterface.insertIzin(map).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.isSuccessful() && response.body().getStatus() == 200) {
                                Toasty.success(getContext(), "Berhasil mengajukan izin", Toasty.LENGTH_SHORT).show();
                                getDataIzinKaryawan();
                                pd.dismiss();
                                dialogIzinKeluarKantor.dismiss();
                            }else {
                                Toasty.error(getContext(), "Gagal mengajukan izin", Toasty.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });
                }
            }
        });
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

        karyawanInterface.getMyProfile(userId).enqueue(new Callback<KaryawanModel>() {
            @Override
            public void onResponse(Call<KaryawanModel> call, Response<KaryawanModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pd.dismiss();
                   atasanId = response.body().getAtasan();



                }else {
                    pd.dismiss();
                    System.err.println(Toasty.error(getContext(), "Gagal memuat data", Toasty.LENGTH_SHORT));
                }
            }

            @Override
            public void onFailure(Call<KaryawanModel> call, Throwable t) {
                pd.dismiss();
                System.err.println(Toasty.error(getContext(), "Gagal memuat data total cuti", Toasty.LENGTH_SHORT));


            }
        });


    }


}