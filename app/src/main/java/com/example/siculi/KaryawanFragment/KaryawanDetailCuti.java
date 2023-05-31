package com.example.siculi.KaryawanFragment;

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

public class KaryawanDetailCuti extends Fragment {
    CardView cvStatus;
    TextView tvStatus;
    EditText etNip, etNama, etTglMulai, etTglSelesai, etJenisCuti,
    etKeperluan;
    Button btnKembali;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karyawan_detail_cuti, container, false);

        cvStatus = view.findViewById(R.id.cvStatus);
        tvStatus = view.findViewById(R.id.tvStatus);
        etNip = view.findViewById(R.id.etNip);
        etNama = view.findViewById(R.id.etNama);
        etTglMulai = view.findViewById(R.id.etTglMulai);
        etTglSelesai = view.findViewById(R.id.etTglSelesai);
        etJenisCuti = view.findViewById(R.id.etJenisCuti);
        etKeperluan = view.findViewById(R.id.etKeperluan);
        btnKembali = view.findViewById(R.id.btnKembali);

        etNama.setText(getArguments().getString("nama"));
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

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}