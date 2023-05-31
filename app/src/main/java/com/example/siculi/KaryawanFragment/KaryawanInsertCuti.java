package com.example.siculi.KaryawanFragment;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.siculi.Adapter.SpinnerJenisCutiAdapter;
import com.example.siculi.Model.JenisCutiModel;
import com.example.siculi.Model.KaryawanModel;
import com.example.siculi.Model.ResponseModel;
import com.example.siculi.R;
import com.example.siculi.Util.DataApi;
import com.example.siculi.Util.KaryawanInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryawanInsertCuti extends Fragment {

    EditText etSisaCuti, etKeterangan, etAlamat, etKeperluan, etFilePath;
    LinearLayout lrPicker;
    TextView etTglCuti, etTglMasuk;
    Button btnKembali;
    Integer jumlahCuti;
    Spinner spJenisCuti;
    SpinnerJenisCutiAdapter spinnerJenisCutiAdapter;
    List<JenisCutiModel> jenisCutiModelList;
    KaryawanInterface karyawanInterface;
    String jenisCuti, userId, atasanId;
    SharedPreferences sharedPreferences;
    Button btnSubmit, btnFilePicker;
    private File file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karyawan_insert_cuti, container, false);

        etKeperluan = view.findViewById(R.id.etKeperluan);
        btnKembali = view.findViewById(R.id.btnKembali);
        sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);
        karyawanInterface = DataApi.getClient().create(KaryawanInterface.class);
        etSisaCuti = view.findViewById(R.id.etSisaCuti);
        etFilePath = view.findViewById(R.id.etFilePath);
        etKeterangan = view.findViewById(R.id.etKeterangan);
        etAlamat = view.findViewById(R.id.etAlamat);
        etTglCuti = view.findViewById(R.id.etTglCuti);
        btnFilePicker = view.findViewById(R.id.btnFilePicker);
        etTglMasuk = view.findViewById(R.id.etTglMasuk);
        lrPicker = view.findViewById(R.id.layourPicker);
        spJenisCuti = view.findViewById(R.id.spJenisCuti);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        getAllJenisCuti();
        getMyProfile();


        etTglMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePicker(etTglMasuk);
            }
        });

        etTglCuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePicker(etTglCuti);
            }
        });



        spJenisCuti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenisCuti = spinnerJenisCutiAdapter.getJenisCuti(position);
                // hilangkan button file picker jika bukan cuti besar dan sakit
                if (jenisCuti.equals("Cuti Besar") || jenisCuti.equals("Cuti Sakit")) {
                    lrPicker.setVisibility(View.VISIBLE);
                }else {
                    lrPicker.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnFilePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 1);
            }
        });


        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertCuti();
            }
        });

        return view;
    }

    private void getAllJenisCuti() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Memuat data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        karyawanInterface.getAllJenisCuti().enqueue(new Callback<List<JenisCutiModel>>() {
            @Override
            public void onResponse(Call<List<JenisCutiModel>> call, Response<List<JenisCutiModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    jenisCutiModelList = response.body();
                    btnSubmit.setEnabled(true);
                    spinnerJenisCutiAdapter = new SpinnerJenisCutiAdapter(getContext(), jenisCutiModelList);
                    spJenisCuti.setAdapter(spinnerJenisCutiAdapter);
                    pd.dismiss();
                }else {
                    pd.dismiss();
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                    btnSubmit.setEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<List<JenisCutiModel>> call, Throwable t) {

                pd.dismiss();
                Toasty.error(getContext(), "Tidak ada koneksi interner", Toasty.LENGTH_SHORT).show();
                btnSubmit.setEnabled(false);

            }
        });
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
                    btnSubmit.setEnabled(true);
                    atasanId = response.body().getAtasan();
                    jumlahCuti = Integer.parseInt(response.body().getSisa_cuti());



                }else {
                    pd.dismiss();
                    btnSubmit.setEnabled(false);
                    System.err.println(Toasty.error(getContext(), "Gagal memuat data", Toasty.LENGTH_SHORT));
                }
            }

            @Override
            public void onFailure(Call<KaryawanModel> call, Throwable t) {
                pd.dismiss();
                btnSubmit.setEnabled(true);

                System.err.println(Toasty.error(getContext(), "Gagal memuat data total cuti", Toasty.LENGTH_SHORT));


            }
        });


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
                    dateFormatted = String.format("%02d",dayOfMonth);
                }else {
                    dateFormatted = String.valueOf(dayOfMonth);
                }

                tvDate.setText(year + "-" + monthFormatted + "-" + dateFormatted);

            }
        });

        datePickerDialog.show();
    }

    private void insertCuti() {

        // ambil tanggal sekarang
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tglSekarang = dateFormat.format(calendar.getTime());


        if (etSisaCuti.getText().toString().isEmpty()) {
            etSisaCuti.setError("Sisa cuti tidak boleh kosong");
            etSisaCuti.requestFocus();
            return;
        } else if (etTglCuti.getText().toString().isEmpty()) {
            etTglCuti.setError("Tanggal cuti tidak boleh kosong");
            etTglCuti.requestFocus();
            return;
        }else if (etTglMasuk.getText().toString().isEmpty()) {
            etTglMasuk.setError("Tanggal masuk tidak boleh kosong");
            etTglMasuk.requestFocus();
            return;
        }else if (etAlamat.getText().toString().isEmpty()) {
            etAlamat.setError("Alamat tidak boleh kosong");
            etAlamat.requestFocus();
            return;
        }else if (etKeperluan.getText().toString().isEmpty()) {
            etKeperluan.setError("Keperluan tidak boleh kosong");
            etKeperluan.requestFocus();
            return;
        }
        // jika tanggal cuti lebih besar dari tanggal masuk
        else if (etTglCuti.getText().toString().compareTo(etTglMasuk.getText().toString()) > 0) {
            etTglCuti.setError("Tanggal cuti tidak boleh lebih kecil dari tanggal sekarang");
            Toasty.error(getContext(), "Tanggal cuti tidak boleh lebih besar dari tanggal masuk", Toasty.LENGTH_SHORT).show();
            etTglCuti.requestFocus();
            return;
        }

        // jika tanggal cuti lebih kecil dari tanggal sekarang
        else if (etTglCuti.getText().toString().compareTo(tglSekarang) < 0) {
            etTglCuti.setError("Tanggal cuti tidak boleh lebih kecil dari tanggal sekarang");
            Toasty.error(getContext(), "Tanggal cuti tidak boleh lebih kecil dari tanggal sekarang", Toasty.LENGTH_SHORT).show();
            etTglCuti.requestFocus();
            return;
        }
        else {

            if (jenisCuti.equals("Cuti Besar") || jenisCuti.equals("Cuti Sakit")) {
                if (etFilePath.getText().toString().isEmpty()) {
                    Toasty.error(getContext(), "Harap memilih file lampiran cuti", Toasty.LENGTH_SHORT).show();
                }else {
                    HashMap map = new HashMap();
                    map.put("user_id", RequestBody.create(MediaType.parse("text/plain"), userId));
                    map.put("jenis_cuti", RequestBody.create(MediaType.parse("text/plain"), jenisCuti));
                    map.put("atasan_id", RequestBody.create(MediaType.parse("text/plain"), atasanId));
                    map.put("tgl_cuti", RequestBody.create(MediaType.parse("text/plain"), etTglCuti.getText().toString()));
                    map.put("jumlah_cuti", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(jumlahCuti)));
                    map.put("keperluan", RequestBody.create(MediaType.parse("text/plain"), etKeperluan.getText().toString()));
                    map.put("alamat", RequestBody.create(MediaType.parse("text/plain"), etAlamat.getText().toString()));
                    map.put("tgl_masuk", RequestBody.create(MediaType.parse("text/plain"), etTglMasuk.getText().toString()));
                    map.put("sisa_cuti", RequestBody.create(MediaType.parse("text/plain"), etSisaCuti.getText().toString()));
                    map.put("keterangan_sisa", RequestBody.create(MediaType.parse("text/plain"), etKeterangan.getText().toString()));

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
                    MultipartBody.Part surat = MultipartBody.Part.createFormData("surat", file.getName(), requestBody);

                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Loading").setMessage("Mengajukan cuti...").setCancelable(false);
                    AlertDialog pd = alert.create();
                    pd.show();

                    karyawanInterface.insertCutiSurat(map, surat).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.isSuccessful() && response.body().getStatus() == 200) {
                                Toasty.success(getContext(), "Berhasil mengajukan cuti", Toasty.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                                pd.dismiss();
                            }else {
                                Toasty.error(getContext(), response.body().getMessage(), Toasty.LENGTH_SHORT).show();
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
            }else {

                HashMap map = new HashMap();
                map.put("user_id", RequestBody.create(MediaType.parse("text/plain"), userId));
                map.put("jenis_cuti", RequestBody.create(MediaType.parse("text/plain"), jenisCuti));
                map.put("atasan_id", RequestBody.create(MediaType.parse("text/plain"), atasanId));
                map.put("tgl_cuti", RequestBody.create(MediaType.parse("text/plain"), etTglCuti.getText().toString()));
                map.put("jumlah_cuti", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(jumlahCuti)));
                map.put("keperluan", RequestBody.create(MediaType.parse("text/plain"), etKeperluan.getText().toString()));
                map.put("alamat", RequestBody.create(MediaType.parse("text/plain"), etAlamat.getText().toString()));
                map.put("tgl_masuk", RequestBody.create(MediaType.parse("text/plain"), etTglMasuk.getText().toString()));
                map.put("sisa_cuti", RequestBody.create(MediaType.parse("text/plain"), etSisaCuti.getText().toString()));
                map.put("keterangan_sisa", RequestBody.create(MediaType.parse("text/plain"), etKeterangan.getText().toString()));

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Loading").setMessage("Mengajukan cuti...").setCancelable(false);
                AlertDialog pd = alert.create();
                pd.show();

                karyawanInterface.insertCutiNonSurat(map).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful() && response.body().getStatus() == 200) {
                            Toasty.success(getContext(), "Berhasil mengajukan cuti", Toasty.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                            pd.dismiss();
                        }else {
                            Toasty.error(getContext(), response.body().getMessage(), Toasty.LENGTH_SHORT).show();
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
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                file = new File(pdfPath);
                etFilePath.setText(file.getName());

            }
        }
    }


    public String getRealPathFromUri(Uri uri) {
        String filePath = "";
        if (getContext().getContentResolver() != null) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                File file = new File(getContext().getCacheDir(), getFileName(uri));
                writeFile(inputStream, file);
                filePath = file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void writeFile(InputStream inputStream, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}