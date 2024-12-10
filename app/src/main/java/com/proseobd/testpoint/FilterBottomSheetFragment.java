package com.proseobd.testpoint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.proseobd.testpoint.databinding.BottomSheetFiltersBinding;

public class FilterBottomSheetFragment extends BottomSheetDialogFragment {
    private BottomSheetFiltersBinding binding;
    private FilterListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = BottomSheetFiltersBinding.inflate(inflater, container, false);
        
        setupListeners();
        return binding.getRoot();
    }

    private void setupListeners() {
        binding.filterChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (listener != null) {
                String filter = "";
                if (checkedId == R.id.chipAll) filter = "all";
                else if (checkedId == R.id.chipRecent) filter = "recent";
                else if (checkedId == R.id.chipFavorite) filter = "favorite";
                listener.onFilterSelected(filter);
            }
        });

        binding.sortGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (listener != null) {
                String sortBy = checkedId == R.id.sortName ? "name" : "date";
                listener.onSortSelected(sortBy);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setFilterListener(FilterListener listener) {
        this.listener = listener;
    }

    public interface FilterListener {
        void onFilterSelected(String filter);
        void onSortSelected(String sortBy);
    }
}