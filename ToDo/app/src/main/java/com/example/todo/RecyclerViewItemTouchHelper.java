package com.example.todo;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewItemTouchHelper extends ItemTouchHelper.SimpleCallback{

    TaskAdapter adapter;
    public RecyclerViewItemTouchHelper(TaskAdapter adapter) {
        super(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.adapter=adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position =viewHolder.getAdapterPosition();
        if(direction==ItemTouchHelper.LEFT  )
        {
            AlertDialog.Builder builder= new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("DeleteTask");
            builder.setMessage("Are you sure?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.deleteTask(position);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(position);
                }
            });
            AlertDialog dialog=builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        else {
            adapter.editTask(position);
        }

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        Paint paint = new Paint();

        // Handle swipe actions
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Swipe to the left (show red background with delete icon)
            if (dX < 0) {
                // Red background
                paint.setColor(ContextCompat.getColor(adapter.getContext(), R.color.deleteRed));
                c.drawRect(itemView.getLeft()+dX, itemView.getTop(), itemView.getRight() , itemView.getBottom(), paint);

                // Delete icon
                Drawable deleteIcon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.baseline_delete);
                if (deleteIcon != null) {
                    int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
                    int iconTop = itemView.getTop() + iconMargin;
                    int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();
                    int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;

                    deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                    deleteIcon.draw(c);
                }
            }
            // Swipe to the right (show green background with edit icon)
            else if (dX > 0) {
                // Green background
                paint.setColor(ContextCompat.getColor(adapter.getContext(), R.color.editGreen));
                c.drawRect(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + dX, itemView.getBottom(), paint);

                // Edit icon
                Drawable editIcon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.baseline_edit_24);
                if (editIcon != null) {
                    int iconMargin = (itemView.getHeight() - editIcon.getIntrinsicHeight()) / 2;
                    int iconTop = itemView.getTop() + iconMargin;
                    int iconBottom = iconTop + editIcon.getIntrinsicHeight();
                    int iconLeft = itemView.getLeft() + iconMargin;
                    int iconRight = iconLeft + editIcon.getIntrinsicWidth();

                    editIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                    editIcon.draw(c);
                }
            }
        }
    }
}
