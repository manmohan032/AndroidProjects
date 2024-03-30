package com.mspawar.flikereapp;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mspawar.flikereapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import  java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  implements GetFlickerJsonData.OnDataAvailable
            , RecyclerItemClickListener.OnRecyclerClickListener
{



    private FlickerRecyclerViewAdapter flickerRecyclerViewAdapter;
    private String result;
    private TextView textView;
    private Toolbar toolbar;
    String baseUrl="https://www.flickr.com/services/feeds/photos_public.gne";

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts  "+Thread.currentThread().getId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        flickerRecyclerViewAdapter=new FlickerRecyclerViewAdapter(new ArrayList<Photos>(),this);
        recyclerView.setAdapter(flickerRecyclerViewAdapter);
//        download("",false,"");

        Log.d(TAG, "onCreate: ends");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        download("en-us",true,"");

        Log.d(TAG, "onResume: ends");
    }



    //listening to any event on recyclerView



    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }


    // end of listening block







    @Override
    protected void onPause() {
        super.onPause();
    }

    public void download(String lang, boolean match, String constraints)
    {

        new Thread(new GetFlickerJsonData(baseUrl,lang,match,constraints,this)).start();
//
//        Thread ob1=new Thread(new getData(string,store,this));
//        ob1.start();
        Log.d(TAG, "download:  is  :  complete ");
    }


    @Override
    public void onDataAvailable(List<Photos> list, DownloadStatus downloadStatus) {
        Log.d(TAG, "onDataAvailable: starts");
        if(downloadStatus==DownloadStatus.OK)
        {
              flickerRecyclerViewAdapter.loadNewData(list);
        }
        else
            Log.d(TAG, "onData available: error occur "+downloadStatus);
    }
}