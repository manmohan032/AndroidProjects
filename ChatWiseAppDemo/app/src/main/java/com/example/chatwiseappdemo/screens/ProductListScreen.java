package com.example.chatwiseappdemo.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatwiseappdemo.API.APIClient;
import com.example.chatwiseappdemo.API.ProductApiInterface;
import com.example.chatwiseappdemo.Model.ProductModel;
import com.example.chatwiseappdemo.Model.ProductResponse;
import com.example.chatwiseappdemo.R;
import com.example.chatwiseappdemo.RVAdapter.ProductRVAdapter;
import com.example.chatwiseappdemo.Utils.RecyclerViewClickInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListScreen extends AppCompatActivity implements RecyclerViewClickInterface {
    private RecyclerView recyclerView;
    private List<ProductModel> productList;
    private ProductRVAdapter adapter;
    private ProductApiInterface apiService;
    public static final String NEXT_SCREEN = "details_screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_screen);


        Toolbar toolbar = findViewById(R.id.productListToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView=findViewById(R.id.productListRV);
        productList=new ArrayList<>();

        apiService=APIClient.getClient().create(ProductApiInterface.class);
        adapter=new ProductRVAdapter(productList,ProductListScreen.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductListScreen.this));
        fetchProducts();
        adapter.setOnClickListener(new ProductRVAdapter.OnClickListener() {
            @Override
            public void onClick(int position, ProductModel model) {
                Intent intent = new Intent(ProductListScreen.this, ProductScreen.class);
                try{
                    intent.putExtra(NEXT_SCREEN, model);
                }
                catch (Exception e)
                {
                    Log.d("ProductListScreen", "onClick: "+e.getMessage());
                }
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            this.finish();
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ProductScreen.class);
        ProductModel selectedProduct = productList.get(position);
        intent.putExtra("product", selectedProduct); // Ensure ProductModel implements Serializable
        startActivity(intent);
    }

    private void fetchProducts() {
        Call<ProductResponse> call = apiService.getProductData();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    if (productResponse != null) {
                        productList.clear();
                        productList.addAll(productResponse.getProducts());
                        adapter.notifyDataSetChanged();
                        Log.d("Success", "Products fetched: " + productList.size());
                    } else {
                        Log.d("ErrorInList 0", "Response body is null");
                    }
                } else {
                    Log.d("ErrorInList 1", "Request failed with status code: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("ErrorInList 2", "Request failed: " + t.getMessage(), t);
            }
        });
    }


}