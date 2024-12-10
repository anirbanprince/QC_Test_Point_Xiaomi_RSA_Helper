package com.proseobd.testpoint;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FirstFragment extends Fragment {

    CardView cvTestPoint, cvRSA, cvISP;
    TextView testPoint, rsaHelper, ispPinout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_first, container, false);

        testPoint=view.findViewById(R.id.testPoint);
        rsaHelper=view.findViewById(R.id.rsaHelper);
        cvTestPoint=view.findViewById(R.id.cvTestPoint);
        cvRSA=view.findViewById(R.id.cvRSA);
        cvISP = view.findViewById(R.id.cvISP);
        ispPinout = view.findViewById(R.id.ispPinout);

        cvTestPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().getSupportFragmentManager().beginTransaction()
                       .replace(R.id.frame,new Test_PointFragment())
                       .addToBackStack(null).commit();

            }
        });



        cvRSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.frame,new RSAFragment())
                        .addToBackStack(null).commit();
            }
        });

        cvISP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.frame, new ISPFragment())
                        .addToBackStack(null).commit();
            }
        });










        return view;
    }
}