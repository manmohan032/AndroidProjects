package com.example.chatwiseappdemo.RVAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.chatwiseappdemo.Model.ProductModel;
import com.example.chatwiseappdemo.R;
import com.example.chatwiseappdemo.Utils.RecyclerViewClickInterface;
import com.example.chatwiseappdemo.screens.ProductScreen;

import java.util.ArrayList;
import java.util.List;

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.ProductViewHolder> {
    private List<ProductModel>  productList;
    private Context context;
    private OnClickListener onClickListener;
    public  ProductRVAdapter(List<ProductModel> productList,  Context context){
        this.productList=productList;
        this.context=context;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view,onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel ob=productList.get(position);
        Glide.with(context).load(ob.getThumbnail())
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("ErrorG", "onLoadFailed: ", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(holder.productImage);
        holder.title.setText(ob.getTitle());
        holder.description.setText(ob.getDescription());
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, ob);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public void setOnClickListener(OnClickListener onClickListener)
    {
        this.onClickListener=onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, ProductModel model);
    }
    static class ProductViewHolder extends RecyclerView.ViewHolder{
         TextView title;
         TextView description;
         ImageView productImage;
        public ProductViewHolder(@NonNull View itemView,OnClickListener onClickListener) {
            super(itemView);
            title=itemView.findViewById(R.id.productTitle);
            description=itemView.findViewById(R.id.productDescription);
            productImage=itemView.findViewById(R.id.productImage);

        }
    }
}
