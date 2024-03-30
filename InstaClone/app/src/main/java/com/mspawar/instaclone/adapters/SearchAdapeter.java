package com.mspawar.instaclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mspawar.instaclone.Models.User;
import com.mspawar.instaclone.databinding.SearchRvItemBinding;
import com.mspawar.instaclone.utils.Utils;

import java.util.ArrayList;

public class SearchAdapeter extends RecyclerView.Adapter<SearchAdapeter.ViewHolder> {

    ArrayList<User> userList;
    SearchRvItemBinding binding;
    Context context;
    public SearchAdapeter(Context context, ArrayList<User> user)
    {
        this.userList=user;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=SearchRvItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User ob=userList.get(position);
        Glide.with(context).load(ob.getImage()).into(holder.binding.profileImage);
        holder.binding.name.setText(ob.getName());
        String followFolder=FirebaseAuth.getInstance().getCurrentUser().getUid()+ Utils.FOLLOW;
        FirebaseFirestore.getInstance().collection(followFolder)
                .whereEqualTo("email",ob.getEmail()).
                get().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty())
                            holder.binding.followButton.setText("Unfollow");
                    }
                }
        );
        final boolean [] isFollow={false};
        holder.binding.followButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isFollow[0])
                        {
                            FirebaseFirestore.getInstance().collection(followFolder)
                                    .whereEqualTo("email",ob.getEmail()).
                                    get().addOnSuccessListener(
                                            new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    FirebaseFirestore.getInstance().collection(followFolder).document(
                                                            queryDocumentSnapshots.getDocuments().get(0).getId()
                                                    ).delete().addOnSuccessListener(
                                                            new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    holder.binding.followButton.setText("follow");
                                                                    isFollow[0]=false;
                                                                }
                                                            }
                                                    );

                                                }
                                            }
                                    );

                        }
                        else {
                            FirebaseFirestore.getInstance()
                                    .collection(followFolder)
                                    .document(ob.getEmail()).
                                    set(ob).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            holder.binding.followButton.setText("Unfollow");

                                        }
                                    });
                            isFollow[0]=true;
                        }
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        SearchRvItemBinding binding;
        public ViewHolder(SearchRvItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
