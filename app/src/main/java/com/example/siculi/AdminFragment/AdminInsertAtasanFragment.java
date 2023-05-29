package com.example.siculi.AdminFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.siculi.AdminAdapter.SpinnerAtasanAdapter;
import com.example.siculi.AdminAdapter.SpinnerJabatanAdapter;
import com.example.siculi.AdminAdapter.SpinnerUnitKerjaAdapter;
import com.example.siculi.Model.AtasanModel;
import com.example.siculi.Model.JabatanModel;
import com.example.siculi.Model.ResponseModel;
import com.example.siculi.Model.UnitKerjaModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminInsertAtasanFragment extends Fragment {
    EditText etNip, etNama, etEmail, etTelepon, etSisaCuti;
    Spinner spJk, spAtasan, spJabatan, spUnitKerja;
    Button btnSimpan, btnBatal;
    TextView tvTglMasuk;
    AdminInterface adminInterface;
    List<JabatanModel> jabatanModelList;
    SpinnerJabatanAdapter spinnerJabatanAdapter;
    String [] opsiJenisKelamin = {"Laki-Laki", "Perempuan"};
    String jenisKelamin, atasanId, jabatan, unitKerja;
    List<AtasanModel> atasanModelList;
    SpinnerAtasanAdapter spinnerAtasanAdapter;
    List<UnitKerjaModel> unitKerjaModelList;
    SpinnerUnitKerjaAdapter spinnerUnitKerjaAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_insert_atasan, container, false);

        etNip = view.findViewById(R.id.etNip);
        etNama = view.findViewById(R.id.etNama);
        etEmail = view.findViewById(R.id.etEmail);
        etTelepon = view.findViewById(R.id.etTelepon);
        etSisaCuti = view.findViewById(R.id.etSisaCuti);
        spJk = view.findViewById(R.id.spJk);
        tvTglMasuk = view.findViewById(R.id.tvTglMasuk);
        spAtasan = view.findViewById(R.id.spAtasan);
        spJabatan = view.findViewById(R.id.spJabatan);
        spUnitKerja = view.findViewById(R.id.spUnitKerja);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        btnBatal = view.findViewById(R.id.btnBatal);
        adminInterface = DataApi.getClient().create(AdminInterface.class);

        ArrayAdapter jkAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, opsiJenisKelamin);
        jkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJk.setAdapter(jkAdapter);

        getAllAtasan();
        getAllJabatan();
        getAllUnitKerja();

        tvTglMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String monthFormatted, dateFormatted;
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


                        tvTglMasuk.setText(year + "-" + monthFormatted + "-" + dateFormatted);

                    }
                });
                datePickerDialog.show();
            }
        });

        spJk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if (opsiJenisKelamin[position].equals("Laki-aki")) {
                     jenisKelamin = "L";
                 }else {
                     jenisKelamin = "P";
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spAtasan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                atasanId = String.valueOf(spinnerAtasanAdapter.getIdAtasan(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spJabatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               jabatan = String.valueOf(spinnerJabatanAdapter.getNamaJabatan(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spUnitKerja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitKerja = spinnerUnitKerjaAdapter.getNamaDept(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adminInterface = DataApi.getClient().create(AdminInterface.class);

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etNip.getText().toString().isEmpty()) {
                    etNip.setError("NIP tidak boleh kosong");
                    etNip.requestFocus();
                } else if (etNama.getText().toString().isEmpty()) {
                    etNama.setError("Nama tidak boleh kosong");
                    etNama.requestFocus();
                } else if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Email tidak boleh kosong");
                    etEmail.requestFocus();
                }else if (etTelepon.getText().toString().isEmpty()) {
                    etTelepon.setError("Telepon tidak boleh kosong");
                    etTelepon.requestFocus();
                }else if (etSisaCuti.getText().toString().isEmpty()) {
                    etSisaCuti.setError("Sisa Cuti tidak boleh kosong");
                    etSisaCuti.requestFocus();
                }else {
                    HashMap map = new HashMap();
                    map.put("nik", RequestBody.create(MediaType.parse("text/plain"), etNip.getText().toString()));
                    map.put("nama", RequestBody.create(MediaType.parse("text/plain"), etNama.getText().toString()));
                    map.put("masuk_kerja", RequestBody.create(MediaType.parse("text/plain"), tvTglMasuk.getText().toString()));
                    map.put("jenis_kelamin", RequestBody.create(MediaType.parse("text/plain"), jenisKelamin));
                    map.put("email", RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString()));
                    map.put("telp", RequestBody.create(MediaType.parse("text/plain"), etTelepon.getText().toString()));
                    map.put("jabatan", RequestBody.create(MediaType.parse("text/plain"), jabatan));
                    map.put("atasan", RequestBody.create(MediaType.parse("text/plain"), atasanId));
                    map.put("unit_kerja", RequestBody.create(MediaType.parse("text/plain"), unitKerja));
                    map.put("sisa_cuti", RequestBody.create(MediaType.parse("text/plain"), etSisaCuti.getText().toString()));

                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Loading").setMessage("Menyimpan data...").setCancelable(false);
                    AlertDialog pd = alert.create();
                    pd.show();

                    adminInterface.insertAtasan(map).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.isSuccessful() && response.body().getStatus() == 200) {
                                pd.dismiss();
                                Toasty.success(getContext(), "Berhasil menambahkan atasan baru", Toasty.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            }else {
                                pd.dismiss();
                                Toasty.error(getContext(), response.body().getMessage(), Toasty.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            pd.dismiss();
                            Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

                        }
                    });


                }
            }
        });

        return view;
    }

    private void getAllAtasan() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data atasan...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getAllAtasan().enqueue(new Callback<List<AtasanModel>>() {
            @Override
            public void onResponse(Call<List<AtasanModel>> call, Response<List<AtasanModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    pd.dismiss();
                    atasanModelList = response.body();
                    spinnerAtasanAdapter = new SpinnerAtasanAdapter(getContext(), atasanModelList);
                    spAtasan.setAdapter(spinnerAtasanAdapter);


                }else {
                    pd.dismiss();
                    Toasty.error(getContext(), "Gagal memuat data atasan", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AtasanModel>> call, Throwable t) {
                pd.dismiss();
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

            }
        });
    }

    private void getAllJabatan() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data jabatan...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getAllJabatan().enqueue(new Callback<List<JabatanModel>>() {
            @Override
            public void onResponse(Call<List<JabatanModel>> call, Response<List<JabatanModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    pd.dismiss();
                    jabatanModelList = response.body();
                    spinnerJabatanAdapter= new SpinnerJabatanAdapter(getContext(), jabatanModelList);
                    spJabatan.setAdapter(spinnerJabatanAdapter);


                }else {
                    pd.dismiss();
                    Toasty.error(getContext(), "Gagal memuat data jabatan", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<JabatanModel>> call, Throwable t) {
                pd.dismiss();
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

            }
        });
    }
    private void getAllUnitKerja() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data unit kerja...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        adminInterface.getAllUnitKerja().enqueue(new Callback<List<UnitKerjaModel>>() {
            @Override
            public void onResponse(Call<List<UnitKerjaModel>> call, Response<List<UnitKerjaModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    pd.dismiss();
                    unitKerjaModelList = response.body();
                    spinnerUnitKerjaAdapter = new SpinnerUnitKerjaAdapter(getContext(), unitKerjaModelList);
                    spUnitKerja.setAdapter(spinnerUnitKerjaAdapter );


                }else {
                    pd.dismiss();
                    Toasty.error(getContext(), "Gagal memuat data unit kerja", Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UnitKerjaModel>> call, Throwable t) {
                pd.dismiss();
                Toasty.error(getContext(), "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();

            }
        });
    }
}