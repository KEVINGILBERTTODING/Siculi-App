package com.example.siculi.AdminAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siculi.Model.KaryawanModel;
import com.example.siculi.R;

import java.util.ArrayList;
import java.util.List;

public class AdminKaryawanAdapter extends RecyclerView.Adapter<AdminKaryawanAdapter.ViewHolder> {
    Context context;
    List<KaryawanModel> karyawanModelList;

    public AdminKaryawanAdapter(Context context, List<KaryawanModel> karyawanModelList) {
        this.context = context;
        this.karyawanModelList = karyawanModelList;
    }

    @NonNull
    @Override
    public AdminKaryawanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_karyawan, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminKaryawanAdapter.ViewHolder holder, int position) {
        holder.tvNama.setText(karyawanModelList.get(holder.getAdapterPosition()).getNama());
        //set lowercase
        String jabatan = karyawanModelList.get(holder.getAdapterPosition()).getJabatan();
        String jabatanLower = jabatan.toLowerCase();
        holder.tvJabatan.setText(jabatanLower);
        holder.tvStatus.setText(karyawanModelList.get(holder.getAdapterPosition()).getStatus());
        if (karyawanModelList.get(holder.getAdapterPosition()).getStatus().equals("Aktif")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.green));
        }else if (karyawanModelList.get(holder.getAdapterPosition()).getStatus().equals("Proses")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.yellow));
        }else {
            holder.tvStatus.setTextColor(context.getColor(R.color.red));

        }


    }

    @Override
    public int getItemCount() {
        return karyawanModelList.size();
    }

    public void filter(ArrayList<KaryawanModel> filteredList) {
        karyawanModelList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvJabatan,tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvJabatan = itemView.findViewById(R.id.tvJabatan);
            tvStatus = itemView.findViewById(R.id.tvStatus);


        }
    }
}