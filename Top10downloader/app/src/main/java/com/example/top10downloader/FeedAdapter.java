package com.example.top10downloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private final List<FeedEntry> application;

    public FeedAdapter(@NonNull Context context, int resource, List<FeedEntry> application) {
        super(context, resource);
        this.layoutResource=resource;
        this.layoutInflater=LayoutInflater.from(context);
        this.application = application;
    }

    @Override
    public int getCount() {
        return application.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        @SuppressLint("ViewHolder") View view =layoutInflater.inflate(layoutResource,parent,false);//this will create a
//        // new view for evry item which get on the screen every time
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
//        TextView name= (TextView) convertView.findViewById(R.id.name);
//        TextView artist=(TextView) convertView.findViewById(R.id.artist);
//        TextView summary=(TextView) convertView.findViewById(R.id.summary);

        FeedEntry currentApp= application.get(position);
        viewHolder.name.setText(currentApp.getName());
        viewHolder.artist.setText(currentApp.getArtist());
//        viewHolder.summary.setText(currentApp.getSummary());
//        Log.d(TAG, "getView: + "+currentApp.getSummary());

        return convertView;
    }
    private class ViewHolder{
        final TextView name;
        final TextView artist;
        final TextView summary;

        ViewHolder(View v){
            this.name=v.findViewById(R.id.name);
            this.artist=v.findViewById(R.id.artist);
            this.summary=v.findViewById(R.id.summary);
        }

    }
}
