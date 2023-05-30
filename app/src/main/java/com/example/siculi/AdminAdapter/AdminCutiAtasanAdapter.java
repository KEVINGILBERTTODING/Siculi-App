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
import com.example.siculi.Model.CutiModel;
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

public class AdminCutiAtasanAdapter extends RecyclerView.Adapter<AdminCutiAtasanAdapter.ViewHolder> {
    Context context;
    List<CutiModel> cutiModelList;
    AdminInterface adminInterface;

    public AdminCutiAtasanAdapter(Context context, List<CutiModel> cutiModelList) {
        this.context = context;
        this.cutiModelList = cutiModelList;
    }

    @NonNull
    @Override
    public AdminCutiAtasanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_cuti, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCutiAtasanAdapter.ViewHolder holder, int position) {
        holder.tvNama.setText(cutiModelList.get(holder.getAdapterPosition()).getNama());
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

                                    adminInterface.deleteCutiKaryawan(
                                            cutiModelList.get(getAdapterPosition()).getId(),
                                            cutiModelList.get(getAdapterPosition()).getIdKaryawan()
                                    ).enqueue(new Callback<ResponseModel>() {
                                        @Override
                                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                            if (response.isSuccessful() && response.body().getStatus() == 200) {
                                                cutiModelList.remove(getAdapterPosition());
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
