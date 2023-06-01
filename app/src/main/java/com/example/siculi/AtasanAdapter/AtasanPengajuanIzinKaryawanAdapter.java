package com.example.siculi.AtasanAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siculi.AtasanFragment.AtasanDetailIzinFragment;
import com.example.siculi.AtasanFragment.AtasanDetailPengajuanIzinKaryawanFragment;
import com.example.siculi.Model.IzinModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;

import java.util.ArrayList;
import java.util.List;

public class AtasanPengajuanIzinKaryawanAdapter extends RecyclerView.Adapter<AtasanPengajuanIzinKaryawanAdapter.ViewHolder> {
    Context context;
    List<IzinModel> izinModelList;
    AdminInterface adminInterface;

    public AtasanPengajuanIzinKaryawanAdapter(Context context, List<IzinModel> izinModelList) {
        this.context = context;
        this.izinModelList = izinModelList;
    }

    @NonNull
    @Override
    public AtasanPengajuanIzinKaryawanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_pengajuan_izin, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AtasanPengajuanIzinKaryawanAdapter.ViewHolder holder, int position) {
        holder.tvTanggalIzin.setText(izinModelList.get(holder.getAdapterPosition()).getTanggalIzin());
        holder.tvWaktuPulang.setText(izinModelList.get(holder.getAdapterPosition()).getWaktuPulang());
        holder.tvWaktuPergi.setText(izinModelList.get(holder.getAdapterPosition()).getWaktuPergi());
        holder.tvNamaPengaju.setText(izinModelList.get(holder.getAdapterPosition()).getNama());




        holder.tvStatus.setText(izinModelList.get(holder.getAdapterPosition()).getStatus());
        if (izinModelList.get(holder.getAdapterPosition()).getStatus().equals("Proses")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.yellow));
        }else if (izinModelList.get(holder.getAdapterPosition()).getStatus().equals("Proses Ketua")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.yellow));
        }
        else if (izinModelList.get(holder.getAdapterPosition()).getStatus().equals("Disetujui")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.green));
        }else if (izinModelList.get(holder.getAdapterPosition()).getStatus().equals("Ditolak")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.red));
        }
        else {
            holder.tvStatus.setTextColor(context.getColor(R.color.orange));
        }


    }

    @Override
    public int getItemCount() {
        return izinModelList.size();
    }

    public void filter(ArrayList<IzinModel> filteredList) {
        izinModelList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTanggalIzin, tvStatus, tvNamaPengaju, tvWaktuPergi, tvWaktuPulang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggalIzin = itemView.findViewById(R.id.tvTanggalIzin);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            adminInterface = DataApi.getClient().create(AdminInterface.class);
            tvWaktuPergi = itemView.findViewById(R.id.tvWaktuPergi);
            tvWaktuPulang = itemView.findViewById(R.id.tvWaktuPulang);
            tvNamaPengaju = itemView.findViewById(R.id.tvNamaPengaju);

            itemView.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new AtasanDetailPengajuanIzinKaryawanFragment();
            Bundle bundle = new Bundle();
            bundle.putString("status", izinModelList.get(getAdapterPosition()).getStatus());
            bundle.putString("nip", izinModelList.get(getAdapterPosition()).getNik());
            bundle.putString("nama", izinModelList.get(getAdapterPosition()).getNama());
            bundle.putString("keperluan", izinModelList.get(getAdapterPosition()).getKeperluan());
            bundle.putString("jenis", izinModelList.get(getAdapterPosition()).getJenis());
            bundle.putString("tgl_izin", izinModelList.get(getAdapterPosition()).getTanggalIzin());
            bundle.putString("jam_mulai", izinModelList.get(getAdapterPosition()).getWaktuPergi());
            bundle.putString("jam_selesai", izinModelList.get(getAdapterPosition()).getWaktuPulang());
            bundle.putString("id", izinModelList.get(getAdapterPosition()).getId());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAtasan, fragment).addToBackStack(null)
                    .commit();

        }
    }
}
