package com.proseobd.testpoint;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {
    private List<String> itemList;
    private List<String> imageList;
    private Dialog imageDialog;
    private float currentZoom = 1.0f;

    public NestedAdapter(List<String> itemList, List<String> imageList) {
        this.itemList = itemList;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item, parent, false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.mTv.setText(itemList.get(position));
        
        String imageUrl = imageList.get(position);
        Glide.with(holder.itemView.getContext())
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_dialog_alert)
            .into(holder.imageView);

        holder.imageView.setOnClickListener(v -> showImagePopup(v.getContext(), imageUrl));
    }

    private void showImagePopup(Context context, String imageUrl) {
        imageDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        View popupView = LayoutInflater.from(context).inflate(R.layout.image_popup_layout, null);
        imageDialog.setContentView(popupView);

        PhotoView popupImage = popupView.findViewById(R.id.popup_image);
        ImageButton closeButton = popupView.findViewById(R.id.close_button);
        ImageButton zoomInButton = popupView.findViewById(R.id.zoom_in_button);
        ImageButton zoomOutButton = popupView.findViewById(R.id.zoom_out_button);

        Glide.with(context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_dialog_alert)
            .into(popupImage);

        zoomInButton.setOnClickListener(v -> {
            float newZoom = popupImage.getScale() + 0.25f;
            popupImage.setScale(newZoom, true);
        });

        zoomOutButton.setOnClickListener(v -> {
            float newZoom = Math.max(1.0f, popupImage.getScale() - 0.25f);
            popupImage.setScale(newZoom, true);
        });

        closeButton.setOnClickListener(v -> imageDialog.dismiss());

        imageDialog.show();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;
        private ImageView imageView;

        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.nestedItemTv);
            imageView = itemView.findViewById(R.id.image);
        }
    }
} 