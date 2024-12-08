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
import androidx.appcompat.widget.SearchView;
import java.util.Locale;



public class Test_PointFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<DataModel> mList;
    private List<DataModel> filteredList;
    private ItemAdapter adapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test__point, container, false);

        recyclerView = view.findViewById(R.id.main_recyclervie);
        searchView = view.findViewById(R.id.searchView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mList = new ArrayList<>();
        filteredList = new ArrayList<>();

        setupSearchView();
        loadData4();
        loadData3();
        loadData2();
        loadData();

        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });
    }

    private void filterData(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(mList);
        } else {
            String lowercaseQuery = query.toLowerCase(Locale.getDefault());
            for (DataModel model : mList) {
                // Check if category name matches
                if (model.getItemText().toLowerCase(Locale.getDefault()).contains(lowercaseQuery)) {
                    filteredList.add(model);
                    continue;
                }
                
                // Check if any item in nested list matches
                List<String> nestedItems = new ArrayList<>();
                for (String item : model.getNestedList()) {
                    if (item.toLowerCase(Locale.getDefault()).contains(lowercaseQuery)) {
                        nestedItems.add(item);
                    }
                }
                
                if (!nestedItems.isEmpty()) {
                    filteredList.add(new DataModel(nestedItems, model.getItemText()));
                }
            }
        }
        
        adapter = new ItemAdapter(filteredList);
        recyclerView.setAdapter(adapter);
    }

    private void loadData2() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/fuljhuridirectory/Jewellers/view.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {
                    List<String> nestedList2 = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("name");
                            nestedList2.add(items);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList2, "Jewellers");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter = new ItemAdapter(filteredList);
                    recyclerView.setAdapter(adapter);
                }, error -> {});
        requestQueue.add(jsonArrayRequest);
    }

    private void loadData3() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/fuljhuridirectory/farmecy/view.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {
                    List<String> nestedList3 = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("name");
                            nestedList3.add(items);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList3, "Pharmacy");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter = new ItemAdapter(filteredList);
                    recyclerView.setAdapter(adapter);
                }, error -> {});
        requestQueue.add(jsonArrayRequest);
    }

    private void loadData4() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/fuljhuridirectory/workshop/view.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {
                    List<String> nestedList4 = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("name");
                            nestedList4.add(items);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList4, "Workshop");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter = new ItemAdapter(filteredList);
                    recyclerView.setAdapter(adapter);
                }, error -> {});
        requestQueue.add(jsonArrayRequest);
    }

    private void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/fuljhuridirectory/upmembers/view.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {
                    List<String> nestedList1 = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("name");
                            nestedList1.add(items);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList1, "UP Members");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter = new ItemAdapter(filteredList);
                    recyclerView.setAdapter(adapter);
                }, error -> {});
        requestQueue.add(jsonArrayRequest);
    }
}




