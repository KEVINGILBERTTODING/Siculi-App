package com.example.siculi.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.siculi.Model.JenisCutiModel;

import java.util.List;

public class SpinnerJenisCutiAdapter extends ArrayAdapter<JenisCutiModel> {

   public SpinnerJenisCutiAdapter(@NonNull Context context, List<JenisCutiModel> pilarModel){
            super(context, android.R.layout.simple_spinner_item, pilarModel);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getJenis());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getJenis());
            return view;
        }


    public String getJenisCuti(int position) {
        return getItem(position).getJenis();
    }






    }
