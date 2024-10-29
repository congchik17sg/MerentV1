package com.example.mermentv1.cart;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartModel> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // Add a method to check if the item is already in the cart
    public boolean isProductInCart(String productName) {
        for (CartModel item : cartItems) {
            if (item.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    // Add item to cart only if it's not already added
    public void addItem(CartModel item) {
        if (!isProductInCart(item.getName())) {
            cartItems.add(item);
        }
    }

    public List<CartModel> getCartItems() {
        return cartItems;
    }
}
