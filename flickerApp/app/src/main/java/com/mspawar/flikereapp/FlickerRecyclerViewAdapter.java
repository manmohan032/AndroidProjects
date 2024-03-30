package com.mspawar.flikereapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.PipHintTrackerKt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FlickerRecyclerViewAdapter extends RecyclerView.Adapter<FlickerRecyclerViewAdapter.FlickerImageViewHolder> {
    private static final String TAG = "FlickerRecyclerViewAdap";
    private List<Photos> photosList;
    private Context context;

    public FlickerRecyclerViewAdapter(List<Photos> photosList, Context context) {
        Log.d(TAG, "FlickerRecyclerViewAdapter: constructer");
        this.photosList = photosList;
        this.context = context;
    }

    @NonNull
    @Override
    public FlickerImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.browse,parent,false);

        return new FlickerImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickerImageViewHolder holder, int position) {
        // called by layout manger when it needs new data for new row

        Photos photo= getPhoto(position);
        Log.d(TAG, "onBindViewHolder:  photos is  "+ photo.getTitle()+" position --> " +position);
        Picasso.get()
                .load(photo.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
        holder.title.setText(photo.getTitle());
    }

    void loadNewData(List<Photos> newPhotos)
    {
        Log.d(TAG, "loadNewData: starts");
        photosList=newPhotos;
        notifyDataSetChanged();
    }
    public Photos getPhoto(int position)
    {
        Log.d(TAG, "getPhoto: starts");
        return (photosList!=null && photosList.size()> position) ? photosList.get(position) : null;
    }

    @Override
    public int getItemCount() {
        
        Log.d(TAG, "getItemCount: starts");
        return (photosList!=null && photosList.size()!=0) ? photosList.size() : 0;
    }

    static class FlickerImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlickerImageViewHolder";
        ImageView thumbnail=null;
        TextView title=null;

        public FlickerImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "FlickerImageViewHolder: constructer called");

            this.thumbnail=(ImageView) itemView.findViewById(R.id.thumbnail);
            this.title=(TextView) itemView.findViewById(R.id.photo_title);
        }

    }
}
