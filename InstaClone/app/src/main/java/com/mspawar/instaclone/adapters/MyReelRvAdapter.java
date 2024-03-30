package com.mspawar.instaclone.adapters;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mspawar.instaclone.Models.Post;
import com.mspawar.instaclone.Models.Reel;
import com.mspawar.instaclone.databinding.MyPostRvDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyReelRvAdapter  extends  RecyclerView.Adapter<MyPostRvViewHolder>{


    private ArrayList<Reel> arrayList;
    private Context context;
    private MyPostRvDesignBinding binding;

    public MyReelRvAdapter(Context context, ArrayList<Reel> postList) {
        this.context=context;
        this.arrayList=postList;
    }

    @NonNull
    @Override
    public MyPostRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=MyPostRvDesignBinding.inflate(LayoutInflater.from(context),parent,false);

        return new MyPostRvViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull MyPostRvViewHolder holder, int position) {
//        Picasso.get().load(arrayList.get(position).getPostUrl()).into(holder.binding.postImage);
        Glide.with(context).load(arrayList.get(position).getPostUrl())
        .diskCacheStrategy(DiskCacheStrategy.ALL).
        into(binding.postImage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
