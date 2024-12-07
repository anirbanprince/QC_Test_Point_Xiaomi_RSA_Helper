package com.proseobd.testpoint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NastedAdapter extends RecyclerView.Adapter<NastedAdapter.ViewHolder>{

    private List<String> mList;

    public NastedAdapter(List<String> mList) {
        this.mList = mList;
    }



    @NonNull
    @Override
    public NastedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NastedAdapter.ViewHolder holder, int position) {
        holder.mTv.setText(mList.get(position));

    }

    @Override
    public int getItemCount() {

        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.nestedItemTv);
        }
    }
}
