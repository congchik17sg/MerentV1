package com.example.mermentv1.model;

import java.util.List;

public class ProductResponse {
    private List<ProductItem> data;
    private boolean success;
    private String message;
    private String error;

    public List<ProductItem> getData() {
        return data;
    }

    public void setData(List<ProductItem> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
