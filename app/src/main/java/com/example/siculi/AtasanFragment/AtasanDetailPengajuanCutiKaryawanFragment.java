package com.example.siculi.AtasanFragment;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.siculi.Model.ResponseModel;
import com.example.siculi.R;
import com.example.siculi.Util.AtasanInterface;
import com.example.siculi.Util.DataApi;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtasanDetailPengajuanCutiKaryawanFragment extends Fragment {
    CardView cvStatus;
    TextView tvStatus;
    EditText etNip, etNama, etTglMulai, etTglSelesai, etJenisCuti,
    etKeperluan;
    Button btnKembali, btnDownload, btnSubmit;
    AtasanInterface atasanInterface;
    Spinner spAksi;
    String [] opsiAksi = {"Disetujui", "Ditolak", "Ditangguhkan"};
    String idCuti, userId, status;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atasan_detail_pengajuan_cuti_karyawan, container, false);

        cvStatus = view.findViewById(R.id.cvStatus);
        tvStatus = view.findViewById(R.id.tvStatus);
        etNip = view.findViewById(R.id.etNip);
        etNama = view.findViewById(R.id.etNama);
        spAksi = view.findViewById(R.id.spAksi);
        etTglMulai = view.findViewById(R.id.etTglMulai);
        etTglSelesai = view.findViewById(R.id.etTglSelesai);
        etJenisCuti = view.findViewById(R.id.etJenisCuti);
        atasanInterface = DataApi.getClient().create(AtasanInterface.class);
        etKeperluan = view.findViewById(R.id.etKeperluan);
        btnKembali = view.findViewById(R.id.btnKembali);
        btnDownload = view.findViewById(R.id.btnDownload);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);

        etNama.setText(getArguments().getString("nama"));
        idCuti = getArguments().getString("id");
        etNip.setText(getArguments().getString("nip"));
        etTglMulai.setText(getArguments().getString("tanggal_mulai"));
        etTglSelesai.setText(getArguments().getString("tanggal_masuk"));
        etJenisCuti.setText(getArguments().getString("jenis"));
        etKeperluan.setText(getArguments().getString("keperluan"));
        tvStatus.setText(getArguments().getString("status"));

        ArrayAdapter opsiAksiAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, opsiAksi);
        opsiAksiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spAksi.setAdapter(opsiAksiAdapter);

        if (getArguments().getString("status").equals("Disetujui")) {
            tvStatus.setTextColor(getContext().getColor(R.color.white));
            cvStatus.setCardBackgroundColor(getContext().getColor(R.color.green));
        }else if (getArguments().getString("status").equals("Ditolak")) {
            tvStatus.setTextColor(getContext().getColor(R.color.white));
            cvStatus.setCardBackgroundColor(getContext().getColor(R.color.red));
        }else if (getArguments().getString("status").equals("Ditolak")) {
            cvStatus.setCardBackgroundColor(getContext().getColor(R.color.red));
        }else {

                cvStatus.setCardBackgroundColor(getContext().getColor(R.color.yellow));

        }
        if (getArguments().getString("jenis").equals("Cuti Sakit") || getArguments().getString("jenis").equals("Cuti Besar")) {
            btnDownload.setVisibility(View.VISIBLE);

        }else {
            btnDownload.setVisibility(View.GONE);
        }

        spAksi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = opsiAksi[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              validasiPermohonanCutiKaryawan();
            }
        });



        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = DataApi.URL_DOWNLOAD_LAMPIRAN_SURAT_CUTI_KARYAWAN + idCuti;
                String title = "Surat Lampiran Cuti " + getArguments().getString("nama") + ".pdf";
                String description = "Downloading PDF file";
                String fileName = "Surat Lampiran Cuti " + getArguments().getString("nama") + ".pdf";


                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setTitle(title);
                request.setDescription(description);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.allowScanningByMediaScanner();

                DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void validasiPermohonanCutiKaryawan() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Loading").setMessage("Menyimpan data...").setCancelable(false);
        AlertDialog pd = alert.create();
        pd.show();

        HashMap map = new HashMap();
        map.put("status", RequestBody.create(MediaType.parse("text/plain"), status));
        map.put("cuti_id", RequestBody.create(MediaType.parse("text/plain"), getArguments().getString("id")));
        map.put("karyaawan_id", RequestBody.create(MediaType.parse("text/plain"), getArguments().getString("karyawan_id")));
        map.put("tgl_masuk", RequestBody.create(MediaType.parse("text/plain"), getArguments().getString("tanggal_masuk")));

        atasanInterface.validasiPermohonanCutiKaryawan(map).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getStatus() == 200) {
                    pd.dismiss();
                    Toasty.success(getContext(), "Berhasil validasi cuti", Toasty.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }else {
                    pd.dismiss();
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();

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