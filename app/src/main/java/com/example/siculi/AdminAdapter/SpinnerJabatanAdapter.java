package com.example.siculi.AdminAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.siculi.Model.JabatanModel;

import java.util.List;

public class SpinnerJabatanAdapter extends ArrayAdapter<JabatanModel> {

   public SpinnerJabatanAdapter(@NonNull Context context, List<JabatanModel> pilarModel){
            super(context, android.R.layout.simple_spinner_item, pilarModel);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getJabatan());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getJabatan());
            return view;
        }


    public String getIdJabatan(int position) {
        return getItem(position).getId();
    }
    public String getNamaJabatan(int position) {
        return getItem(position).getJabatan();
    }





    }
