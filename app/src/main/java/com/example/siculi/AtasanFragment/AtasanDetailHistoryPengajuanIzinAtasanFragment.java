package com.example.siculi.AtasanFragment;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.siculi.R;
import com.example.siculi.Util.AtasanInterface;
import com.example.siculi.Util.DataApi;

public class AtasanDetailHistoryPengajuanIzinAtasanFragment extends Fragment {
    CardView cvStatus;
    TextView tvStatus;
    EditText etNip, etNama, etJamMulai, etJamSelesai, etJenisLIzin,
    etKeperluan, etTglIzin;
    Button btnKembali, btnDownload;
    String idIzin, userId;
    SharedPreferences sharedPreferences;
    AtasanInterface atasanInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atasan_detail_history_pengajuan_izin_atasan_fragment, container, false);

        cvStatus = view.findViewById(R.id.cvStatus);
        tvStatus = view.findViewById(R.id.tvStatus);
        etNip = view.findViewById(R.id.etNip);
        etNama = view.findViewById(R.id.etNama);
        etJamMulai = view.findViewById(R.id.etJamMulai);
        etJamSelesai = view.findViewById(R.id.etJamSelesai);
        etJenisLIzin = view.findViewById(R.id.etJenisIzin);
        etKeperluan = view.findViewById(R.id.etKeperluan);
        etTglIzin = view.findViewById(R.id.etTglIzin);
        btnDownload = view.findViewById(R.id.btnDownload);

        btnKembali = view.findViewById(R.id.btnKembali);
        atasanInterface = DataApi.getClient().create(AtasanInterface.class);
        sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);


        idIzin = getArguments().getString("id");
        etNama.setText(getArguments().getString("nama"));
        etNip.setText(getArguments().getString("nip"));
        etJamMulai.setText(getArguments().getString("jam_mulai"));
        etJamSelesai.setText(getArguments().getString("jam_selesai"));
        etJenisLIzin.setText(getArguments().getString("jenis"));
        etKeperluan.setText(getArguments().getString("keperluan"));
        tvStatus.setText(getArguments().getString("status"));
        etTglIzin.setText(getArguments().getString("tgl_izin"));

        if (getArguments().getString("status").equals("Disetujui")) {
            if (getArguments().getString("jenis").equals("Cepat")) {
                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downloadSuratIzinCepat();
                    }
                });
            }else {
                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downloadSuratIzinNormal();
                    }
                });
            }
            btnDownload.setVisibility(View.VISIBLE);
        }else {
            btnDownload.setVisibility(View.GONE);
        }




        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }


    private void downloadSuratIzinCepat() {
        String url = DataApi.URL_DOWNLOAD_SURAT_IZIN_KARYAWAN_CEPAT+ userId + "/" + idIzin;
        String title = "Surat Izin_" +getArguments().getString("nama") + ".pdf";
        String description = "Downloading PDF file";
        String fileName = "Surat izin.pdf";


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        request.setDescription(description);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();

        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }
    private void downloadSuratIzinNormal() {
        String url = DataApi.URL_DOWNLOAD_SURAT_IZIN_KARYAWAN_NORMAL+ userId + "/" + idIzin;
        String title = "Surat Izin_" +getArguments().getString("nama") + ".pdf";
        String description = "Downloading PDF file";
        String fileName = "Surat izin.pdf";


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        request.setDescription(description);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();

        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

}