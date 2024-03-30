package com.mspawar.instaclone.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mspawar.instaclone.databinding.MyPostRvDesignBinding;

public class MyPostRvViewHolder extends RecyclerView.ViewHolder {

    MyPostRvDesignBinding binding;
    public MyPostRvViewHolder(MyPostRvDesignBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
