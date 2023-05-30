package com.example.siculi.KaryawanAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siculi.AdminFragment.AdminDetailIzinFragment;
import com.example.siculi.Model.IzinModel;
import com.example.siculi.Model.ResponseModel;
import com.example.siculi.R;
import com.example.siculi.Util.AdminInterface;
import com.example.siculi.Util.DataApi;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryawanIzinAdapter extends RecyclerView.Adapter<KaryawanIzinAdapter.ViewHolder> {
    Context context;
    List<IzinModel> izinModelList;
    AdminInterface adminInterface;

    public KaryawanIzinAdapter(Context context, List<IzinModel> izinModelList) {
        this.context = context;
        this.izinModelList = izinModelList;
    }

    @NonNull
    @Override
    public KaryawanIzinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_izin_karyawan, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KaryawanIzinAdapter.ViewHolder holder, int position) {
        holder.tvTanggalIzin.setText(izinModelList.get(holder.getAdapterPosition()).getTanggalIzin());

        if (izinModelList.get(holder.getAdapterPosition()).getKeperluan().length() > 30) {
            holder.tvIzin.setText(izinModelList.get(holder.getAdapterPosition()).getKeperluan().substring(0, 30)+"...");
        }else {
            holder.tvIzin.setText(izinModelList.get(holder.getAdapterPosition()).getKeperluan());
        }

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
        TextView tvTanggalIzin, tvIzin,tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggalIzin = itemView.findViewById(R.id.tvTanggalIzin);
            tvIzin = itemView.findViewById(R.id.tvIzin);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            adminInterface = DataApi.getClient().create(AdminInterface.class);




            itemView.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new AdminDetailIzinFragment();
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
                    .replace(R.id.frameAdmin, fragment).addToBackStack(null)
                    .commit();

        }
    }
}
