package com.example.mermentv1.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ComboResponse {

    @SerializedName("data") // Use @SerializedName if the API returns 'data'
    private List<ComboItem> data;

    // Getters and setters for 'data' instead of 'combos'
    public List<ComboItem> getData() {
        return data;
    }

    public void setData(List<ComboItem> data) {
        this.data = data;
    }


}
