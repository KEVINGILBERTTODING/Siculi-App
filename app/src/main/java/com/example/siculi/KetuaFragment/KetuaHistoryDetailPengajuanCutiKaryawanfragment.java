package com.example.siculi.KetuaFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.siculi.R;
import com.example.siculi.Util.DataApi;

public class KetuaHistoryDetailPengajuanCutiKaryawanfragment extends Fragment {
    CardView cvStatus;
    TextView tvStatus;
    EditText etNip, etNama, etTglMulai, etTglSelesai, etJenisCuti,
    etKeperluan;
    Button btnKembali, btnDownload;
    String idCuti, userId;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_atasan_history_detail_pengajuan_cuti_karyawan, container, false);

        cvStatus = view.findViewById(R.id.cvStatus);
        tvStatus = view.findViewById(R.id.tvStatus);
        etNip = view.findViewById(R.id.etNip);
        etNama = view.findViewById(R.id.etNama);
        etTglMulai = view.findViewById(R.id.etTglMulai);
        etTglSelesai = view.findViewById(R.id.etTglSelesai);
        etJenisCuti = view.findViewById(R.id.etJenisCuti);
        etKeperluan = view.findViewById(R.id.etKeperluan);
        btnKembali = view.findViewById(R.id.btnKembali);
        btnDownload = view.findViewById(R.id.btnDownload);
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



        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = DataApi.URL_DOWNLOAD_SURAT_CUTI_KARYAWAN + idCuti + "/" + getArguments().getString("karyawan_id");
                String title = "Surat Cuti.pdf";
                String description = "Downloading PDF file";
                String fileName = "Surat cuti.pdf";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                startActivity(intent);

//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                request.setTitle(title);
//                request.setDescription(description);
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.allowScanningByMediaScanner();
//
//                DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//                downloadManager.enqueue(request);
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
}