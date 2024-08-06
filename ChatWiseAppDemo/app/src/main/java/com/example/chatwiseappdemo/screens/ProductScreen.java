package com.example.chatwiseappdemo.screens;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.chatwiseappdemo.Model.ProductModel;
import com.example.chatwiseappdemo.R;

import java.util.Objects;

public class ProductScreen extends AppCompatActivity {
    private TextView productTitle;
    private TextView productDescription;
    private TextView productPrice;
    private TextView productRating;
    private ProductModel product;
    private ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_screen_option);


        Toolbar toolbar = findViewById(R.id.productToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        productTitle = findViewById(R.id.product_title);
        productDescription = findViewById(R.id.product_description);
        productImage=findViewById(R.id.product_thumbnail);
        productRating=findViewById(R.id.product_rating);
        productPrice=findViewById(R.id.product_price);
        Intent intent = getIntent();
//        productTitle.setText("first");

        if (intent != null && intent.hasExtra(ProductListScreen.NEXT_SCREEN)) {
            ProductModel product = (ProductModel) intent.getSerializableExtra(ProductListScreen.NEXT_SCREEN);

            if (product != null) {
                // Display product details
                productTitle.setText(product.getTitle());
                productPrice.setText("$"+String.valueOf(product.getPrice()));
                productRating.setText("Rating : "+String.valueOf(product.getRating())+"/5.0");
                productDescription.setText(product.getDescription());

                // Load image using Glide or another image loading library
                Glide.with(this)
                        .load(product.getThumbnail()) // Assuming thumbnail is the main image for simplicity
                        .into(productImage);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            this.finish();
            return true;
        }
        return false;
    }
}