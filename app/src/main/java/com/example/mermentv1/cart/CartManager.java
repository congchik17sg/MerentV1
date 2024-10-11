package com.example.mermentv1.cart;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartModel> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(CartModel item) {
        cartItems.add(item);
    }

    public List<CartModel> getCartItems() {
        return cartItems;
    }
}
