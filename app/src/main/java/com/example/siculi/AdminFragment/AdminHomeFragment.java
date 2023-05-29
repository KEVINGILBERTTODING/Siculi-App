package com.example.siculi.AdminFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.siculi.R;

public class AdminHomeFragment extends Fragment {
    TextView tvName;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
       sharedPreferences = getContext().getSharedPreferences("data_user", Context.MODE_PRIVATE);

       tvName = view.findViewById(R.id.tvName);
       tvName.setText(sharedPreferences.getString("nama", null));

       return view;
    }
}