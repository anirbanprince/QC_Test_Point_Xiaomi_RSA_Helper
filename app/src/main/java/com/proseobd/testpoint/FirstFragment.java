package com.proseobd.testpoint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FirstFragment extends Fragment {



    TextView testPoint, rsaHelper;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_first, container, false);

        testPoint=view.findViewById(R.id.testPoint);
        rsaHelper=view.findViewById(R.id.rsaHelper);

        testPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().getSupportFragmentManager().beginTransaction()
                       .replace(R.id.frame,new Test_PointFragment())
                       .addToBackStack(null).commit();

            }
        });



        rsaHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.frame,new RSAFragment())
                        .addToBackStack(null).commit();
            }
        });










        return view;
    }
}