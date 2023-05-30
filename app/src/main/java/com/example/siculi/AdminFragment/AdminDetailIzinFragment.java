package com.example.siculi.AdminFragment;

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

public class AdminDetailIzinFragment extends Fragment {
    CardView cvStatus;
    TextView tvStatus;
    EditText etNip, etNama, etJamMulai, etJamSelesai, etJenisLIzin,
    etKeperluan, etTglIzin;
    Button btnKembali;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_detail_izin_fragment, container, false);

        cvStatus = view.findViewById(R.id.cvStatus);
        tvStatus = view.findViewById(R.id.tvStatus);
        etNip = view.findViewById(R.id.etNip);
        etNama = view.findViewById(R.id.etNama);
        etJamMulai = view.findViewById(R.id.etJamMulai);
        etJamSelesai = view.findViewById(R.id.etJamSelesai);
        etJenisLIzin = view.findViewById(R.id.etJenisIzin);
        etKeperluan = view.findViewById(R.id.etKeperluan);
        etTglIzin = view.findViewById(R.id.etTglIzin);
        btnKembali = view.findViewById(R.id.btnKembali);

        etNama.setText(getArguments().getString("nama"));
        etNip.setText(getArguments().getString("nip"));
        etJamMulai.setText(getArguments().getString("jam_mulai"));
        etJamSelesai.setText(getArguments().getString("jam_selesai"));
        etJenisLIzin.setText(getArguments().getString("jenis"));
        etKeperluan.setText(getArguments().getString("keperluan"));
        tvStatus.setText(getArguments().getString("status"));
        etTglIzin.setText(getArguments().getString("tgl_izin"));

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