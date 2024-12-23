package com.proseobd.testpoint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ISPFragment extends Fragment {

    TextView itemTv;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_isp, container, false);

        itemTv = fragmentView.findViewById(R.id.itemTv);









        return fragmentView;
    }
}