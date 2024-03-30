package com.mspawar.instaclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mspawar.instaclone.Models.User;
import com.mspawar.instaclone.databinding.StoryFollowRvItemBinding;

import java.util.ArrayList;

public class StoryFollowAdapter extends RecyclerView.Adapter<StoryFollowAdapter.ViewHolder>{


    Context context;
    ArrayList<User>  userList;
    StoryFollowRvItemBinding binding;
    public StoryFollowAdapter(Context context,ArrayList<User> user)
    {
        this.context =context;

        this.userList=user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=StoryFollowRvItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User ob=userList.get(position);
        Glide.with(context).load(ob.getImage()).into(holder.binding.profileImage);
        holder.binding.name.setText(ob.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        StoryFollowRvItemBinding binding;
        ViewHolder(StoryFollowRvItemBinding binding)
        {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
