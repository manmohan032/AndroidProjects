package com.mspawar.instaclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mspawar.instaclone.Models.Post;
import com.mspawar.instaclone.databinding.MyPostRvDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyPostRvAdapter  extends  RecyclerView.Adapter<MyPostRvViewHolder>{


    private ArrayList<Post> arrayList;
    private Context context;
    private MyPostRvDesignBinding binding;

    public MyPostRvAdapter(Context context, ArrayList<Post> postList) {
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
       Picasso.get().load(arrayList.get(position).getPostUrl()).into(holder.binding.postImage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
