package com.mspawar.instaclone.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mspawar.instaclone.Models.Reel;
import com.mspawar.instaclone.Models.User;
import com.mspawar.instaclone.databinding.ReelDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReelAdapter extends RecyclerView.Adapter<ReelAdapter.ViewHolder>{

    ArrayList<Reel> reelList;
    private ReelDesignBinding binding;
    private Context context;
    private User user;

    public ReelAdapter(Context context, ArrayList<Reel> postList) {
        this.context=context;
        this.reelList=postList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ReelDesignBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reel ob=reelList.get(position);
        if(!ob.getProfileLink().equals("")) {

            FirebaseFirestore.getInstance().collection("users").document(ob.getProfileLink()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user=documentSnapshot.toObject(User.class);
                    if(user!=null){
                        Picasso.get().load(user.getImage()).into(holder.binding.profileImage);
                        holder.binding.userName.setText(user.getName());}
                }
            });
            if(user!=null){
            Picasso.get().load(user.getImage()).into(binding.profileImage);
            holder.binding.userName.setText(user.getName());}
        }

        holder.binding.title.setText(ob.getPostTitle());
        holder.binding.videoView.setVideoPath(ob.getPostUrl());
        holder.binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setLooping(true);
                holder.binding.progressBar.setVisibility(View.GONE);
                mp.start();

            }
        });



    }
    @Override
    public int getItemCount() {
        return reelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ReelDesignBinding binding;
        public ViewHolder(ReelDesignBinding reelDesignBinding) {

            super(reelDesignBinding.getRoot());
            binding=reelDesignBinding;
        }
    }
}
