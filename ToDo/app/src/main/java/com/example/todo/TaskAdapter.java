package com.example.todo;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.model.TaskModel;
import com.example.todo.utils.UtilsContract;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskModel> taskList;
    private UtilsContract obSQL;
    private  MainActivity activity;

    // Constructor to initialize the data
    public TaskAdapter(List<TaskModel> taskList,UtilsContract obSQL) {
        this.taskList = taskList;
        this.obSQL=obSQL;
    }
    public TaskAdapter(List<TaskModel> taskList,UtilsContract obSQL,MainActivity activity) {
        this.taskList = taskList;
        this.obSQL=obSQL;
        this.activity=activity;
    }

    public TaskAdapter(UtilsContract obSQL,MainActivity activity) {

        this.taskList = new ArrayList<>();
        this.activity=activity;
        this.obSQL=obSQL;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task , parent, false);
        return new TaskViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // - get element
        // - replace the contents of the view with that element
        TaskModel task=taskList.get(position);
        holder.checkBox.setText(task.getTask());

        // You can also set onClickListener or other properties here if needed
        // For example, handle checkbox state changes
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(getChecked(task.getStatus()));
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                task.setStatus(1);
            else
                task.setStatus(0);

            obSQL.updateStatus(task.getStatus(), task.getId());

        });

    }
    private Boolean getChecked(int val)
    {
        return val!=0;
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.e("here",""+taskList.size());
        return taskList.size();
    }
    public Context getContext() {
        return activity;
    }
    public void setTaskList(List<TaskModel> taskList)
    {
        this.taskList=taskList;

    }
    public void deleteTask(int position){
        obSQL.deleteTask(taskList.get(position).getId());
        taskList.remove(position);
        notifyItemRemoved(position);
    }
    public void editTask(int position){
        TaskModel task=taskList.get(position);

        Bundle bundle=new Bundle();
        bundle.putInt("id",task.getId());
        bundle.putString("task",task.getTask());

        AddNewTask edittask=new AddNewTask();
        edittask.setArguments(bundle);
        edittask.show(activity.getSupportFragmentManager(),edittask.getTag());

        notifyItemChanged(position,task);
    }


    // ViewHolder class
    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public CheckBox checkBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}