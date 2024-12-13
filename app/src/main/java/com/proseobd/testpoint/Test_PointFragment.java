package com.proseobd.testpoint;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.proseobd.testpoint.databinding.FragmentTestPointBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;
import androidx.appcompat.widget.SearchView;
import java.util.Locale;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;

import com.google.android.material.snackbar.Snackbar;
import com.android.volley.DefaultRetryPolicy;



public class Test_PointFragment extends Fragment  {

    private FragmentTestPointBinding binding;
    private FilterBottomSheetFragment filterSheet;
    private RecyclerView recyclerView;
    private List<DataModel> mList;
    private List<DataModel> filteredList;
    private ItemAdapter adapter;
    private SearchView searchView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTestPointBinding.inflate(inflater, container, false);
        

        return binding.getRoot();
    }


    }





