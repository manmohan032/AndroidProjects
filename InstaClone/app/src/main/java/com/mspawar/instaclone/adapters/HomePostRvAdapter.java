package com.mspawar.instaclone.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mspawar.instaclone.Models.Post;
import com.mspawar.instaclone.Models.User;
import com.mspawar.instaclone.R;
import com.mspawar.instaclone.databinding.PostRvBinding;

import java.util.ArrayList;

public class HomePostRvAdapter extends  RecyclerView.Adapter <HomePostRvAdapter.ViewHolder> {

    Context context;
    private  PostRvBinding binding;
    ArrayList<Post> postList;
    public HomePostRvAdapter(Context context, ArrayList<Post> postList) {
        this.context=context;
        this.postList=postList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=PostRvBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post ob=postList.get(position);
        if(ob.getProfileLink()!=null) {


            FirebaseFirestore.getInstance().collection("users")
                    .document(ob.getProfileLink())
                    .get()
                    .addOnSuccessListener(
                    new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user=documentSnapshot.toObject(User.class);
                    if(user!=null){
                        Glide.with(context).load(user.getImage()).into(holder.binding.profileImage);
                        holder.binding.name.setText(user.getName());
                        holder.binding.userName.setText(user.getName());
                    }
                }
            });

        }
        holder.binding.caption.setText(ob.getPostTitle());
        Glide.with(context).load(ob.getPostUrl()).into(holder.binding.imageView);
        final boolean[] like = {false};
        holder.binding.likeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!like[0])
                        {
                            holder.binding.likeButton.setImageResource(R.drawable.heart_1);
                            like[0] =true;
                        }
                        else
                        {
                            holder.binding.likeButton.setImageResource(R.drawable.heart);
                            like[0]=false;
                        }
                    }
                }
        );
        if(ob.getTime()!=null)
        {
            long time= Long.parseLong(ob.getTime());
            holder.binding.time.setText(TimeAgo.using(time));


        }
        holder.binding.shareButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_TEXT,ob.getPostUrl());
                        context.startActivity(i);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        PostRvBinding binding;
        public ViewHolder(PostRvBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
