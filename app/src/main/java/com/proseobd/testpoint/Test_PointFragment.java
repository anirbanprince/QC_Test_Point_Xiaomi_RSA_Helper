package com.proseobd.testpoint;

import android.os.Bundle;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;



public class Test_PointFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<DataModel> mList;
    private ItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test__point, container, false);

        recyclerView = view.findViewById(R.id.main_recyclervie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mList = new ArrayList<>();

        loadData4();
        loadData3();
        loadData2();
        loadData();

        return view;

    }


    private void loadData2 () {


        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());

        String url = "https://proseobd.com/apps/fuljhuridirectory/Jewellers/view.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList2 = new ArrayList<>();
                    Log.d("ServerRes", response.toString());

                    for (int x=0; x<response.length(); x++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("name");
                            nestedList2.add(items);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    mList.add(new DataModel(nestedList2,"Jewellers"));
                    adapter = new ItemAdapter(mList);
                    recyclerView.setAdapter(adapter);

                }, error ->{});


        requestQueue.add(jsonArrayRequest);

    }


    private void loadData3 () {


        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());

        String url = "https://proseobd.com/apps/fuljhuridirectory/farmecy/view.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList3 = new ArrayList<>();
                    Log.d("ServerRes", response.toString());

                    for (int x=0; x<response.length(); x++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("name");
                            nestedList3.add(items);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    mList.add(new DataModel(nestedList3,"Pharmacy"));
                    adapter = new ItemAdapter(mList);
                    recyclerView.setAdapter(adapter);

                }, error ->{});


        requestQueue.add(jsonArrayRequest);

    }

    private void loadData4 () {


        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());

        String url = "https://proseobd.com/apps/fuljhuridirectory/workshop/view.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {
                    List<String> nestedList4 = new ArrayList<>();
                    Log.d("ServerRes", response.toString());

                    for (int x=0; x<response.length(); x++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("name");
                            nestedList4.add(items);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    mList.add(new DataModel(nestedList4,"Workshop"));
                    adapter = new ItemAdapter(mList);
                    recyclerView.setAdapter(adapter);

                }, error ->{});


        requestQueue.add(jsonArrayRequest);

    }

    private void loadData () {


        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());

        String url = "https://proseobd.com/apps/fuljhuridirectory/upmembers/view.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList1 = new ArrayList<>();
                    Log.d("ServerRes", response.toString());

                    for (int x=0; x<response.length(); x++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("name");
                            nestedList1.add(items);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    mList.add(new DataModel(nestedList1,"UP Members"));
                    adapter = new ItemAdapter(mList);
                    recyclerView.setAdapter(adapter);

                }, error ->{});


        requestQueue.add(jsonArrayRequest);

    }
}




