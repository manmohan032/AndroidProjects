package com.example.todo;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo.model.TaskModel;
import com.example.todo.utils.OnDialogCloseListener;
import com.example.todo.utils.UtilsContract;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
      public static final String TAG="AddNewTask";
      private EditText editText;
      private Button saveButton;
      private UtilsContract db;


      public static AddNewTask newInstance(){
          return  new AddNewTask();
      }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_new_task,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText=view.findViewById(R.id.taskInputText);
        saveButton=view.findViewById(R.id.addTaskButton);

        db=new UtilsContract(getActivity());
        if(editText.getText().toString().equals("")){
            saveButton.setEnabled(false);
            saveButton.setBackgroundColor(Color.GRAY);
        }

        boolean isUpdate=false;
        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            isUpdate=true;
            saveButton.setText("Update");
            String text=bundle.getString("task");

            editText.setText(text);
            if(text!=null && !text.isEmpty())
            {
                saveButton.setEnabled(false);
            }

        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.equals("")){
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(Color.GRAY);
                }
                else {
                    saveButton.setEnabled(true);
                    saveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(Color.GRAY);
                }
            }
        });
        boolean finalIsUpdate = isUpdate;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editText.getText().toString();
                if(finalIsUpdate)
                {
                    db.updateTask(text,bundle.getInt("id"));
                }
                else{
                    TaskModel task=new TaskModel();
                    task.setStatus(0);
                    task.setTask(text);
                    db.insertTask(task);
                }
                dismiss();
            }

        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if(activity instanceof OnDialogCloseListener)
        {
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
