package com.example.chatwiseappdemo.Model;

import java.util.List;

public class ProductResponse {
    private List<ProductModel> products;

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}