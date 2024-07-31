package com.mspawar.flikereapp;

import android.util.Log;

public class Store {
    private String data;
    private static final String TAG = "Store";
    public synchronized void setData(String data){
        this.data=data;
        Log.d(TAG, "setData: "+data);
    }
    public synchronized String getData()
    {
        Log.d(TAG, "getData: "+data);
        return data;

    }

}
