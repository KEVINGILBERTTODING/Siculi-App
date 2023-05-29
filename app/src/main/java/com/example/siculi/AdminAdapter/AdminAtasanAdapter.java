package com.example.siculi.AdminAdapter;

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

import com.example.siculi.AdminFragment.AdminDetailAtasanFragment;
import com.example.siculi.AdminFragment.DetailKaryawanFragment;
import com.example.siculi.Model.AtasanModel;
import com.example.siculi.R;

import java.util.ArrayList;
import java.util.List;

public class AdminAtasanAdapter extends RecyclerView.Adapter<AdminAtasanAdapter.ViewHolder> {
    Context context;
    List<AtasanModel> atasanModelList;

    public AdminAtasanAdapter(Context context, List<AtasanModel> atasanModelList) {
        this.context = context;
        this.atasanModelList = atasanModelList;
    }

    @NonNull
    @Override
    public AdminAtasanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_karyawan, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAtasanAdapter.ViewHolder holder, int position) {
        holder.tvNama.setText(atasanModelList.get(holder.getAdapterPosition()).getNama());
        //set lowercase
        String jabatan = atasanModelList.get(holder.getAdapterPosition()).getJabatan();
        String jabatanLower = jabatan.toLowerCase();
        holder.tvJabatan.setText(jabatanLower);
        holder.tvStatus.setText(atasanModelList.get(holder.getAdapterPosition()).getStatus());
        if (atasanModelList.get(holder.getAdapterPosition()).getStatus().equals("Aktif")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.green));
        }else if (atasanModelList.get(holder.getAdapterPosition()).getStatus().equals("Proses")) {
            holder.tvStatus.setTextColor(context.getColor(R.color.yellow));
        }else {
            holder.tvStatus.setTextColor(context.getColor(R.color.red));

        }


    }

    @Override
    public int getItemCount() {
        return atasanModelList.size();
    }

    public void filter(ArrayList<AtasanModel> filteredList) {
        atasanModelList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNama, tvJabatan,tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvJabatan = itemView.findViewById(R.id.tvJabatan);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            itemView.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {
            Fragment fragment = new AdminDetailAtasanFragment();
            Bundle bundle = new Bundle();
            bundle.putString("atasan_id", atasanModelList.get(getAdapterPosition()).getKd_atasan());
            fragment.setArguments(bundle);
            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAdmin, fragment).addToBackStack(null)
                    .commit();

        }
    }
}
