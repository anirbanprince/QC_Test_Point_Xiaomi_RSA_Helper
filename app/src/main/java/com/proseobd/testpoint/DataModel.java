package com.proseobd.testpoint;

import java.util.List;

public class DataModel {

    private List<String> items;
    private List<String> imageUrls;
    private String title;
    private boolean expandable;

    public DataModel(List<String> items, List<String> imageUrls, String title) {
        this.items = items;
        this.imageUrls = imageUrls;
        this.title = title;
        this.expandable = false;
    }

    public List<String> getItems() {
        return items;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getNestedList() {
        return items;
    }

    public String getItemText() {
        return title;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }
}
