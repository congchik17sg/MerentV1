package com.example.mermentv1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductOrderResponse {

    private List<ProductOrder> data;

    // Getter and setter for 'data'
    public List<ProductOrder> getData() {
        return data;
    }

    public void setData(List<ProductOrder> data) {
        this.data = data;
    }

}
