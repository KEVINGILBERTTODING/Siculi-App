package com.example.siculi.KetuaAdapter;

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

import com.example.siculi.KetuaFragment.KetuaHistoryDetailPengajuanCutiAtasanfragment;
import com.example.siculi.KetuaFragment.KetuaHistoryDetailPengajuanCutiKaryawanfragment;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;

import java.util.ArrayList;
import java.util.List;

public class KetuaHistoryPengajuanCutiAtasanAdapter extends RecyclerView.Adapter<KetuaHistoryPengajuanCutiAtasanAdapter.ViewHolder> {
    Context context;
    List<CutiModel> cutiModelList;
    AdminInterface adminInterface;

    public KetuaHistoryPengajuanCutiAtasanAdapter(Context context, List<CutiModel> cutiModelList) {
        this.context = context;
        this.cutiModelList = cutiModelList;
    }

    @NonNull
    @Override
    public KetuaHistoryPengajuanCutiAtasanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_pengajuan_cuti, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KetuaHistoryPengajuanCutiAtasanAdapter.ViewHolder holder, int position) {


        holder.tvNamaPengaju.setText(cutiModelList.get(holder.getAdapterPosition()).getNama());
        holder.tvTglMasuk.setText(cutiModelList.get(holder.getAdapterPosition()).getTglMasuk());
        holder.tvTglCuti.setText(cutiModelList.get(holder.getAdapterPosition()).getTglCuti());
        holder.tvJenisCuti.setText(cutiModelList.get(holder.getAdapterPosition()).getJenisCuti());



        holder.tvStatus.setText(cutiModelList.get(holder.getAdapterPosition()).getStatus());
        if (cutiModelList.get(holder.getAdapterPosition()).getStatus().equals("Proses")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.yellow));
        }else if (cutiModelList.get(holder.getAdapterPosition()).getStatus().equals("Proses Ketua")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.yellow));
        }
        else if (cutiModelList.get(holder.getAdapterPosition()).getStatus().equals("Disetujui")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.green));
        }else if (cutiModelList.get(holder.getAdapterPosition()).getStatus().equals("Ditolak")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.red));
        }
        else {
            holder.tvStatus.setTextColor(context.getColor(R.color.orange));
        }


    }

    @Override
    public int getItemCount() {
        return cutiModelList.size();
    }

    public void filter(ArrayList<CutiModel> filteredList) {
        cutiModelList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNamaPengaju, tvStatus, tvTglCuti, tvTglMasuk, tvJenisCuti;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaPengaju = itemView.findViewById(R.id.tvNamaPengaju);
            tvTglCuti = itemView.findViewById(R.id.tvTglCuti);
            tvTglMasuk = itemView.findViewById(R.id.tvTglMasuk);
            tvJenisCuti = itemView.findViewById(R.id.tvJenisCuti);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            adminInterface = DataApi.getClient().create(AdminInterface.class);


            itemView.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new KetuaHistoryDetailPengajuanCutiAtasanfragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", cutiModelList.get(getAdapterPosition()).getId());
            bundle.putString("status", cutiModelList.get(getAdapterPosition()).getStatus());
            bundle.putString("karyawan_id", cutiModelList.get(getAdapterPosition()).getIdKaryawan());
            bundle.putString("nip", cutiModelList.get(getAdapterPosition()).getNik());
            bundle.putString("nama", cutiModelList.get(getAdapterPosition()).getNama());
            bundle.putString("tanggal_mulai", cutiModelList.get(getAdapterPosition()).getTglCuti());
            bundle.putString("tanggal_masuk", cutiModelList.get(getAdapterPosition()).getTglMasuk());
            bundle.putString("jenis", cutiModelList.get(getAdapterPosition()).getJenisCuti());
            bundle.putString("keperluan", cutiModelList.get(getAdapterPosition()).getKeperluan());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameKetua, fragment).addToBackStack(null)
                    .commit();

        }
    }
}
