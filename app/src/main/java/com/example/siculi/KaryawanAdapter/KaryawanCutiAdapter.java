package com.example.siculi.KaryawanAdapter;

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

import com.example.siculi.AdminFragment.AdminDetailCutiKaryawan;
import com.example.siculi.Model.CutiModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;

import java.util.ArrayList;
import java.util.List;

public class KaryawanCutiAdapter extends RecyclerView.Adapter<KaryawanCutiAdapter.ViewHolder> {
    Context context;
    List<CutiModel> cutiModelList;
    AdminInterface adminInterface;

    public KaryawanCutiAdapter(Context context, List<CutiModel> cutiModelList) {
        this.context = context;
        this.cutiModelList = cutiModelList;
    }

    @NonNull
    @Override
    public KaryawanCutiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_cuti_karyawan, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KaryawanCutiAdapter.ViewHolder holder, int position) {
        holder.tvTanggalSelesai.setText(cutiModelList.get(holder.getAdapterPosition()).getTglMasuk());
        holder.tvTanggalMulai.setText(cutiModelList.get(holder.getAdapterPosition()).getTglCuti());
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
        TextView tvTanggalMulai, tvTanggalSelesai, tvJenisCuti,tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJenisCuti = itemView.findViewById(R.id.tvJenisCuti);
            tvTanggalMulai = itemView.findViewById(R.id.tvTanggalCuti);
            tvTanggalSelesai = itemView.findViewById(R.id.tvTanggalSelesai);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            adminInterface = DataApi.getClient().create(AdminInterface.class);




            itemView.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new AdminDetailCutiKaryawan();
            Bundle bundle = new Bundle();
            bundle.putString("status", cutiModelList.get(getAdapterPosition()).getStatus());
            bundle.putString("nip", cutiModelList.get(getAdapterPosition()).getNik());
            bundle.putString("nama", cutiModelList.get(getAdapterPosition()).getNama());
            bundle.putString("tanggal_mulai", cutiModelList.get(getAdapterPosition()).getTglCuti());
            bundle.putString("tanggal_masuk", cutiModelList.get(getAdapterPosition()).getTglMasuk());
            bundle.putString("jenis", cutiModelList.get(getAdapterPosition()).getJenisCuti());
            bundle.putString("keperluan", cutiModelList.get(getAdapterPosition()).getKeperluan());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAdmin, fragment).addToBackStack(null)
                    .commit();

        }
    }
}
