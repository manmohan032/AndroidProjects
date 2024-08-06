package com.example.todo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.example.todo.model.TaskModel;
import com.example.todo.utils.OnDialogCloseListener;
import com.example.todo.utils.UtilsContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private UtilsContract obSQL;
    private  List<TaskModel> tempList;
    List<TaskModel> taskList;
    TaskAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fab = findViewById(R.id.fabTask);
        recyclerView = findViewById(R.id.recyclerView);
        obSQL=new UtilsContract(this);

//        tempList = new ArrayList<>();
//        TaskModel ob=new TaskModel();
//        ob.setStatus(0);
//        ob.setTask("1");
//        tempList.add(ob);
//        tempList.add(ob);
//        tempList.add(ob);
//        tempList.add(ob);
        taskList=obSQL.getAlTask();
        Collections.reverse(taskList);
        adapter = new TaskAdapter(taskList,obSQL,this);
        fab.setImageTintList(ColorStateList.valueOf(Color.WHITE));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopupWindow();
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
        ItemTouchHelper touchHelper=new ItemTouchHelper(new RecyclerViewItemTouchHelper(adapter));
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

//
//
//    private void showPopupWindow() {
//        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        View popupView = inflater.inflate(R.layout.add_new_task, null);
//
//        // Create a PopupWindow instance
//        PopupWindow popupWindow = new PopupWindow(popupView,
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT, true);
//
//
//        // Set up the close button inside the popup window
//        Button saveButton = popupView.findViewById(R.id.addTaskButton);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText taskText = popupView.findViewById(R.id.taskInputText);
//                String text=taskText.getText().toString();
//
//                TaskModel task=new TaskModel();
//                task.setTask(text);
//                task.setStatus(0);
//                Log.e("task",text);
//                if(!task.getTask().isEmpty())
//                {
//                    obSQL.insertTask(task);
//                    taskList.add(task);
//                    tempList.add(task);
//                    fab.setVisibility(View.VISIBLE);
//                    popupWindow.dismiss();
//                }
//                else {
//                    showDialog();
//                }
//                // Dismiss the popup window
//            }
//        });
//
        // Set focusable to true to prevent accidental background click dismiss
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(false);
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//
//
//        // Calculate x and y coordinates to show the popup window at the bottom end of screen
////        int x = screenWidth ; // align right
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        int y = displayMetrics.heightPixels; ; // align bottom
////        fab.setVisibility(View.GONE);
//        // Show the popup window at the calculated position
//
//        popupWindow.showAtLocation(fab, 0, 0, y);
//    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("") // Set the dialog title
                .setMessage("Enter some task before save") // Set the dialog message
                ;

        AlertDialog dialog = builder.create(); // Create the AlertDialog object
        dialog.show(); // Show the dialog
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        taskList=obSQL.getAlTask();
        Collections.reverse(taskList);
        adapter.setTaskList(taskList);
        adapter.notifyDataSetChanged();
    }
}
