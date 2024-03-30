package com.mspawar.flikereapp;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";
    interface OnRecyclerClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);
    }

    private final OnRecyclerClickListener listener;
    private  final GestureDetector gestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        this.listener = listener;
        gestureDetector=null;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: "+e.toString());
        return super.onInterceptTouchEvent(rv, e);
    }
}
