package com.example.chatwiseappdemo.API;

import com.example.chatwiseappdemo.Model.ProductModel;
import com.example.chatwiseappdemo.Model.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApiInterface {
    @GET("products")
    Call<ProductResponse> getProductData();
}
