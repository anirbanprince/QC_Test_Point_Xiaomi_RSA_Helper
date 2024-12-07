package com.proseobd.testpoint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.ArrayList;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Test_PointFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<DataModel> dataModelList;
    private ItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test__point, container, false);

        recyclerView =view.findViewById(R.id.main_recyclervie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataModelList=new ArrayList<>();
        loadData();

/*
        //list1
        List<String> nestedList1 = new ArrayList<>();
        nestedList1.add("Jams and Honey");
        nestedList1.add("Pickles and Chutneys");
        nestedList1.add("Readymade Meals");
        nestedList1.add("Chyawanprash and Health Foods");
        nestedList1.add("Pasta and Soups");
        nestedList1.add("Sauces and Ketchup");
        nestedList1.add("Namkeen and Snacks");
        nestedList1.add("Honey and Spreads");

        List<String> nestedList2 = new ArrayList<>();
        nestedList2.add("Book");
        nestedList2.add("Pen");
        nestedList2.add("Office Chair");
        nestedList2.add("Pencil");
        nestedList2.add("Eraser");
        nestedList2.add("NoteBook");
        nestedList2.add("Map");
        nestedList2.add("Office Table");

        List<String> nestedList3 = new ArrayList<>();
        nestedList3.add("Decorates");
        nestedList3.add("Tea Table");
        nestedList3.add("Wall Paint");
        nestedList3.add("Furniture");
        nestedList3.add("Bedsits");
        nestedList3.add("Certain");
        nestedList3.add("Namkeen and Snacks");
        nestedList3.add("Honey and Spreads");

        List<String> nestedList4 = new ArrayList<>();
        nestedList4.add("Pasta");
        nestedList4.add("Spices");
        nestedList4.add("Salt");
        nestedList4.add("Chyawanprash");
        nestedList4.add("Maggie");
        nestedList4.add("Sauces and Ketchup");
        nestedList4.add("Snacks");
        nestedList4.add("Kurkure");

        List<String> nestedList5 = new ArrayList<>();
        nestedList5.add("Jams and Honey");
        nestedList5.add("Pickles and Chutneys");
        nestedList5.add("Readymade Meals");
        nestedList5.add("Chyawanprash and Health Foods");
        nestedList5.add("Pasta and Soups");
        nestedList5.add("Sauces and Ketchup");
        nestedList5.add("Namkeen and Snacks");
        nestedList5.add("Honey and Spreads");

        List<String> nestedList6 = new ArrayList<>();
        nestedList6.add("Pasta");
        nestedList6.add("Spices");
        nestedList6.add("Salt");
        nestedList6.add("Chyawanprash");
        nestedList6.add("Maggie");
        nestedList6.add("Sauces and Ketchup");
        nestedList6.add("Snacks");
        nestedList6.add("Kurkure");


        List<String> nestedList7 = new ArrayList<>();
        nestedList7.add("Decorates");
        nestedList7.add("Tea Table");
        nestedList7.add("Wall Paint");
        nestedList7.add("Furniture");
        nestedList7.add("Bedsits");
        nestedList7.add("Certain");
        nestedList7.add("Namkeen and Snacks");
        nestedList7.add("Honey and Spreads");

 */



       /* dataModelList.add(new DataModel( nestedList2,"Stationary"));
        dataModelList.add(new DataModel( nestedList3,"Home Care"));
        dataModelList.add(new DataModel(nestedList4 ,"Grocery & Staples"));
        dataModelList.add(new DataModel(nestedList5,"Pet Care"));
        dataModelList.add(new DataModel(nestedList6,"Baby Care"));
        dataModelList.add(new DataModel(nestedList7 ,"Personal Care"));

        */




        return view;
    }

    private void loadData(){



        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());

        String url = "https://proseobd.com/apps/fuljhuridirectory/Cable%20Network/view.php";

        JsonArrayRequest jsonArrayRequest;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {
                    dataModelList.clear();
                    Log.d("ServerRes", response.toString());


                    List<String> nestedList1 = null;
                    for (int x = 0; x < response.length(); x++) {

                        try {

                            JSONObject jsonObject = response.getJSONObject(x);

                            nestedList1 = new ArrayList<>();
                            String name = jsonObject.getString("name");
                            nestedList1.add(name);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    dataModelList.add(new DataModel(nestedList1, "Instant Food and Noodles"));


                    adapter = new ItemAdapter(dataModelList);
                    recyclerView.setAdapter(adapter);


                }, error -> {
        });

        requestQueue.add(jsonArrayRequest);



    }
}