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
    private int completedRequests = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTestPointBinding.inflate(inflater, container, false);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
            requestQueue.getCache().clear(); // Clear cache before refreshing
            completedRequests = 0;  // Reset counter before loading
            loadAllData();
            //binding.swipeRefresh.setRefreshing(false);

        });

        // Setup filter FAB
        binding.fabFilter.setOnClickListener(v -> {
            filterSheet = new FilterBottomSheetFragment();
            filterSheet.setFilterListener((FilterBottomSheetFragment.FilterListener) this);
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
                List<String> nestedItems = new ArrayList<>();
                List<String> nestedImages = new ArrayList<>();
                List<String> nestedCodeNames = new ArrayList<>();

                // Search through both items and codeNames
                for (int i = 0; i < model.getNestedList().size(); i++) {
                    String item = model.getNestedList().get(i);
                    String codeName = model.getCodeNames().get(i);

                    // Check if either item name or code name contains the search query
                    if (item.toLowerCase(Locale.getDefault()).contains(lowercaseQuery) ||
                            codeName.toLowerCase(Locale.getDefault()).contains(lowercaseQuery)) {
                        nestedItems.add(item);
                        nestedImages.add(model.getImageUrls().get(i));
                        nestedCodeNames.add(codeName);
                    }
                }

                if (!nestedItems.isEmpty()) {
                    filteredList.add(new DataModel(nestedItems, nestedImages, nestedCodeNames, model.getTitle()));
                }
            }
        }

        adapter.notifyDataSetChanged();
        binding.emptyState.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void loadAllData() {
        mList.clear();
        filteredList.clear();
        binding.shimmerLayout.setVisibility(View.VISIBLE);
        binding.shimmerLayout.startShimmer();

        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.getCache().clear(); // Clear cache before loading new data

        completedRequests = 0;  // Reset counter before loading

        loadOppo();
        loadHuawei();
        loadSamsung();
        loadXiaomi();
        loadRealme();
        loadVivo();
        loadZTE();
        loadNokia();
        loadMotorola();
        loadOnePlus();
        loadTecno();
    }

    private void loadXiaomi() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/xiaomi_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {
                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            imageList.add(image);
                            codeNameList.add(codeName);
                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", "Error parsing JSON", e);
                        }
                    }
                    if (!nestedList.isEmpty()) {
                        DataModel newModel = new DataModel(nestedList, imageList, codeNameList, "Xiaomi");
                        mList.add(newModel);
                        filteredList.add(newModel);
                        adapter.notifyDataSetChanged();
                    }
                    checkDataLoadComplete();
                }, error -> {
            Log.d("rerr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void loadSamsung() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/samsung_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {
                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            codeNameList.add(codeName);
                            imageList.add(image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList, imageList, codeNameList, "Samsung");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter.notifyDataSetChanged();
                    checkDataLoadComplete();
                }, error -> {
            Log.d("merr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void loadHuawei() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/huawei_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {
                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            codeNameList.add(codeName);
                            imageList.add(image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList, imageList, codeNameList, "Huawei");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter.notifyDataSetChanged();
                    checkDataLoadComplete();
                }, error -> {
            Log.d("rnerr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void loadOppo() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/oppo_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            codeNameList.add(codeName);
                            imageList.add(image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList, imageList, codeNameList,"Oppo");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter.notifyDataSetChanged();
                    checkDataLoadComplete();
                }, error -> {
            Log.d("perr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void loadRealme() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/realme_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            codeNameList.add(codeName);
                            imageList.add(image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList, imageList, codeNameList,"Realme");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter.notifyDataSetChanged();
                    checkDataLoadComplete();
                }, error -> {
            Log.d("perr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void loadVivo() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/vivo_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            codeNameList.add(codeName);
                            imageList.add(image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList, imageList, codeNameList,"Vivo");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter.notifyDataSetChanged();
                    checkDataLoadComplete();
                }, error -> {
            Log.d("perr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void loadZTE() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/zte_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            codeNameList.add(codeName);
                            imageList.add(image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList, imageList, codeNameList,"ZTE");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter.notifyDataSetChanged();
                    checkDataLoadComplete();
                }, error -> {
            Log.d("perr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void loadNokia() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/nokia_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            codeNameList.add(codeName);
                            imageList.add(image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList, imageList, codeNameList,"Nokia");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter.notifyDataSetChanged();
                    checkDataLoadComplete();
                }, error -> {
            Log.d("perr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void loadMotorola() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/motorola_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            codeNameList.add(codeName);
                            imageList.add(image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList, imageList, codeNameList,"Motorola");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter.notifyDataSetChanged();
                    checkDataLoadComplete();
                }, error -> {
            Log.d("perr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void loadOnePlus() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/oneplus_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            codeNameList.add(codeName);
                            imageList.add(image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList, imageList, codeNameList,"OnePlus");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter.notifyDataSetChanged();
                    checkDataLoadComplete();
                }, error -> {
            Log.d("perr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void loadTecno() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        String url = "https://proseobd.com/apps/Test_Point/test_point/apis/tecno_data.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                response -> {

                    List<String> nestedList = new ArrayList<>();
                    List<String> codeNameList = new ArrayList<>();
                    List<String> imageList = new ArrayList<>();
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(x);
                            String items = jsonObject.getString("modelname");
                            String codeName = jsonObject.getString("codename");
                            String image = jsonObject.getString("image");
                            nestedList.add(items);
                            codeNameList.add(codeName);
                            imageList.add(image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    DataModel newModel = new DataModel(nestedList, imageList, codeNameList,"Tecno");
                    mList.add(newModel);
                    filteredList.add(newModel);
                    adapter.notifyDataSetChanged();
                    checkDataLoadComplete();
                }, error -> {
            Log.d("perr", error.getMessage().toString());
            showError(error.getMessage());
            checkDataLoadComplete();
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }



    private void checkDataLoadComplete() {
        completedRequests++;
        if (completedRequests == 11) {
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.setVisibility(View.GONE);
            binding.swipeRefresh.setRefreshing(false);
            if (mList.isEmpty()) {
                showError("No data available");
            } else {
                adapter.notifyDataSetChanged();
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
                    List<String> codeNames = model.getCodeNames();
                    List<String> images = model.getImageUrls();

                    int start = Math.max(0, items.size() - 5);
                    List<String> recentItems = new ArrayList<>(items.subList(start, items.size()));
                    List<String> recentCodeNames = new ArrayList<>(codeNames.subList(start, codeNames.size()));
                    List<String> recentImages = new ArrayList<>(images.subList(start, images.size()));

                    filteredList.add(new DataModel(recentItems, recentImages, recentCodeNames, model.getTitle()));
                }
                break;
            case "favorite":
                SharedPreferences prefs = requireContext().getSharedPreferences("Favorites", Context.MODE_PRIVATE);
                for (DataModel model : mList) {
                    List<String> favoriteItems = new ArrayList<>();
                    List<String> favoriteCodeNames = new ArrayList<>();
                    List<String> favoriteImages = new ArrayList<>();

                    List<String> items = model.getItems();
                    List<String> codeNames = model.getCodeNames();
                    List<String> images = model.getImageUrls();

                    for (int i = 0; i < items.size(); i++) {
                        String item = items.get(i);
                        if (prefs.getBoolean(item, false)) {
                            favoriteItems.add(item);
                            favoriteCodeNames.add(codeNames.get(i));
                            favoriteImages.add(images.get(i));
                        }
                    }
                    if (!favoriteItems.isEmpty()) {
                        filteredList.add(new DataModel(favoriteItems, favoriteImages, favoriteCodeNames, model.getTitle()));
                    }
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    public void onSortSelected(String sortBy) {
        if (filteredList == null || filteredList.isEmpty()) return;

        for (DataModel model : filteredList) {
            List<String> items = model.getItems();
            List<String> codeNames = model.getCodeNames();
            List<String> images = model.getImageUrls();

            // Create a list of indices to maintain the relationship
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                indices.add(i);
            }

            // Sort indices based on the selected criteria
            if (sortBy.equals("name")) {
                indices.sort((i1, i2) -> items.get(i1).compareTo(items.get(i2)));
            } else {
                // For date sorting, reverse the indices
                Collections.reverse(indices);
            }

            // Create new sorted lists
            List<String> sortedItems = new ArrayList<>();
            List<String> sortedCodeNames = new ArrayList<>();
            List<String> sortedImages = new ArrayList<>();

            // Rebuild all lists maintaining their relationships
            for (Integer index : indices) {
                sortedItems.add(items.get(index));
                sortedCodeNames.add(codeNames.get(index));
                sortedImages.add(images.get(index));
            }

            // Replace the original lists with sorted ones
            items.clear();
            codeNames.clear();
            images.clear();

            items.addAll(sortedItems);
            codeNames.addAll(sortedCodeNames);
            images.addAll(sortedImages);
        }
        adapter.notifyDataSetChanged();
    }


    }





