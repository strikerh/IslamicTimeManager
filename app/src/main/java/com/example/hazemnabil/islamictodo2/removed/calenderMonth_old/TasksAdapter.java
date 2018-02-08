package com.example.hazemnabil.islamictodo2.removed.calenderMonth_old;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.objData.Task;

/**
 * Created by hazem.nabil on 4/2/2017.
 */

public class TasksAdapter extends BaseAdapter {
    private Context mContext;
    private Task[] moTasks;
    private int taskLength;





    public TasksAdapter(Context c, Task moTask[]) {
        this.mContext = c;
        this.moTasks = moTask;
        if (moTask != null) {
            taskLength = moTasks.length;
        }else {
            taskLength = 0;
        }

    }

    @Override
    public int getCount() {
        if (taskLength>0)
          return taskLength;
        else
            return taskLength+1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        View task;
        CheckBox chk_isDone;
        TextView txt_taskCategory;
        ImageView img_isDone;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        task = inflater.inflate(R.layout.p3_fragment_day_task, parent, false);

        //chk_isDone = (CheckBox) Task.findViewById(R.id.chk_isDone);
        img_isDone = (ImageView) task.findViewById(R.id.img_isDone);
        txt_taskCategory = (TextView) task.findViewById(R.id.txt_taskCategory);

        if(taskLength>0) {
            txt_taskCategory.setTextColor(Color.WHITE);
            txt_taskCategory.setText(moTasks[position].getTaskCategory());
            txt_taskCategory.setBackgroundColor(moTasks[position].getTaskCategoryColor());
            if (!moTasks[position].getDone())
                img_isDone.setImageResource(R.drawable.ic_done_black_24dp);
            else
                img_isDone.setImageResource(0);


            if (position == taskLength - 1) {
                LinearLayout hg = (LinearLayout) task.findViewById(R.id.mainTask);

                hg.setMinimumHeight(parent.getHeight());

            }
       /* GradientDrawable bgShape = (GradientDrawable)txt_taskCategory.getBackground();
        bgShape.setColor(Color.BLUE);
       // txt_taskCategory.setback*/

        }else{
            img_isDone.setVisibility(View.GONE);
            txt_taskCategory.setVisibility(View.GONE);
            LinearLayout hg = (LinearLayout) task.findViewById(R.id.mainTask);

            hg.setMinimumHeight(parent.getHeight());
        }


        return task;
    }
}
