package com.example.siculi.AdminAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.siculi.Model.UnitKerjaModel;

import java.util.List;

public class SpinnerUnitKerjaAdapter extends ArrayAdapter<UnitKerjaModel> {

   public SpinnerUnitKerjaAdapter(@NonNull Context context, List<UnitKerjaModel> pilarModel){
            super(context, android.R.layout.simple_spinner_item, pilarModel);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getDept());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getDept());
            return view;
        }


    public String getIdDept(int position) {
        return getItem(position).getId();
    }

    public String getNamaDept(int position) {
        return getItem(position).getDept();
    }





    }
