package com.example.siculi.AdminAdapter;

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

import com.example.siculi.AdminFragment.AdminDetailCutiKaryawan;
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

public class AdminIzinAtasanAdapter extends RecyclerView.Adapter<AdminIzinAtasanAdapter.ViewHolder> {
    Context context;
    List<IzinModel> izinModelList;
    AdminInterface adminInterface;

    public AdminIzinAtasanAdapter(Context context, List<IzinModel> izinModelList) {
        this.context = context;
        this.izinModelList = izinModelList;
    }

    @NonNull
    @Override
    public AdminIzinAtasanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_cuti, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminIzinAtasanAdapter.ViewHolder holder, int position) {
        holder.tvNama.setText(izinModelList.get(holder.getAdapterPosition()).getNama());
        holder.tvJenisCuti.setText(izinModelList.get(holder.getAdapterPosition()).getJenis());

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
        TextView tvNama, tvJenisCuti,tvStatus;
        Button btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvJenisCuti = itemView.findViewById(R.id.tvJenisCuti);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            adminInterface = DataApi.getClient().create(AdminInterface.class);


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Peringatan").setMessage("Apakah anda yakin?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder alert2 = new AlertDialog.Builder(context);
                                    alert2.setTitle("Loading").setMessage("Menghapus cuti...").setCancelable(false);
                                    AlertDialog pd = alert2.create();
                                    pd.show();

                                    adminInterface.deleteIzin(
                                            izinModelList.get(getAdapterPosition()).getId()
                                    ).enqueue(new Callback<ResponseModel>() {
                                        @Override
                                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                            if (response.isSuccessful() && response.body().getStatus() == 200) {
                                                izinModelList.remove(getAdapterPosition());
                                                notifyDataSetChanged();
                                                pd.dismiss();
                                                Toasty.normal(context, "Berhasil menghapus data", Toasty.LENGTH_SHORT).show();
                                            }else {
                                                pd.dismiss();
                                                Toasty.error(context, "Gagal menghapus data", Toasty.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                                            pd.dismiss();
                                            Toasty.error(context, "Tidak ada koneksi internet", Toasty.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    alert.show();
                }

            });

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
