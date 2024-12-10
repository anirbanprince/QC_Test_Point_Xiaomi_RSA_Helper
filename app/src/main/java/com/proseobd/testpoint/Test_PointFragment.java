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
import com.proseobd.testpoint.databinding.FragmentTestPointBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;
import androidx.appcompat.widget.SearchView;
import java.util.Locale;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Filter;

import java.util.Collections;

import com.google.android.material.snackbar.Snackbar;
import com.android.volley.DefaultRetryPolicy;



public class Test_PointFragment extends Fragment implements FilterBottomSheetFragment.FilterListener {

    private FragmentTestPointBinding binding;
    private FilterBottomSheetFragment filterSheet;
    private RecyclerView recyclerView;
    private List<DataModel> mList;
    private List<DataModel> filteredList;
    private ItemAdapter adapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTestPointBinding.inflate(inflater, container, false);
        
        // Setup filter FAB
        binding.fabFilter.setOnClickListener(v -> {
            filterSheet = new FilterBottomSheetFragment();
            filterSheet.setFilterListener(this);
            filterSheet.show(getChildFragmentManager(), "filters");
        });

        recyclerView = binding.mainRecyclervie;
        searchView = binding.searchView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new ItemAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        setupSearchView();
        loadAllData();

        return binding.getRoot();
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
        
        // Show/hide empty state
        binding.emptyState.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void loadAllData() {
        binding.shimmerLayout.setVisibility(View.VISIBLE);
        binding.shimmerLayout.startShimmer();
        
        loadData4();
        loadData3();
        loadData2();
        loadData();
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
                            Log.e("JSON_ERROR", "Error parsing JSON", e);
                        }
                    }
                    if (!nestedList1.isEmpty()) {
                        DataModel newModel = new DataModel(nestedList1, "UP Members");
                        mList.add(newModel);
                        filteredList.add(newModel);
                        adapter.notifyDataSetChanged();
                    }
                    checkDataLoadComplete();
                }, error -> {
                    showError(error.getMessage());
                    checkDataLoadComplete();
                });
        
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                
        requestQueue.add(jsonArrayRequest);
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

    private void checkDataLoadComplete() {
        if (mList.size() >= 4) {  // All data sources loaded
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
            if (mList.isEmpty()) {
                showEmptyState();
            }
        }
    }

    private void showEmptyState() {
        binding.emptyState.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showError(String message) {
        Snackbar.make(binding.getRoot(), 
            message != null ? message : "Network error occurred", 
            Snackbar.LENGTH_LONG)
            .setAction("Retry", v -> loadAllData())
            .show();
    }

    @Override
    public void onFilterSelected(String filter) {
        if (mList == null || mList.isEmpty()) return;
        
        filteredList.clear();
        switch (filter) {
            case "all":
                filteredList.addAll(mList);
                break;
            case "recent":
                // Add last 5 items from each category
                for (DataModel model : mList) {
                    List<String> items = model.getItems();
                    int start = Math.max(0, items.size() - 5);
                    List<String> recentItems = items.subList(start, items.size());
                    filteredList.add(new DataModel(recentItems, model.getTitle()));
                }
                break;
            case "favorite":
                // Implement favorite logic based on SharedPreferences
                SharedPreferences prefs = requireContext().getSharedPreferences("Favorites", Context.MODE_PRIVATE);
                for (DataModel model : mList) {
                    List<String> favoriteItems = new ArrayList<>();
                    for (String item : model.getItems()) {
                        if (prefs.getBoolean(item, false)) {
                            favoriteItems.add(item);
                        }
                    }
                    if (!favoriteItems.isEmpty()) {
                        filteredList.add(new DataModel(favoriteItems, model.getTitle()));
                    }
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSortSelected(String sortBy) {
        if (filteredList == null || filteredList.isEmpty()) return;
        
        for (DataModel model : filteredList) {
            List<String> items = model.getItems();
            if (sortBy.equals("name")) {
                Collections.sort(items);
            } else {
                // Sort by date if items have timestamps
                // For now, reverse the list as a simple example
                Collections.reverse(items);
            }
        }
        adapter.notifyDataSetChanged();
    }
}




