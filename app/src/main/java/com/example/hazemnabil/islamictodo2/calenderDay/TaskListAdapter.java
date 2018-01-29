package com.example.hazemnabil.islamictodo2.calenderDay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.objData.Task;
import com.example.hazemnabil.islamictodo2.viewTask.TaskItemDetailActivity;
import com.example.hazemnabil.islamictodo2.viewTask.TaskItemDetailFragment;

import java.util.List;

/**
 * Created by hazem.nabil on 29/01/2018.
 */

public class TaskListAdapter extends BaseAdapter {
    private List<Task> listOfTasks;

    private View mView;
    // public final TextView mIdView;
    // public final TextView mContentView;
    private LinearLayout root_task_item;
    private LinearLayout ll_task_item;
    private LinearLayout ll_spliter;
    private CheckBox chk_checkBox;
    private TextView txt_task;
    private TextView txt_time;
    private TextView txt_taskCategory;
    private TextView txt_taskTag;
    private RatingBar ratingBar;
    private ImageButton btn_edit;
    private ImageButton btn_move;
    private ImageButton btn_star;
    private TextView txt_timeName;
    private TextView txt_splitterTime;


    private final FragmentListener mListener;
    private Context mContext ;
    //private MyTime myTime = new MyTime();

    public int TaskId;
    private boolean hasTime;


    private Task mTask;


    public TaskListAdapter(boolean hasTime , List<Task> listOfTasks, FragmentListener listener) {

        this.hasTime = hasTime;
        this.listOfTasks = listOfTasks;
        this.mListener = listener;
        this.mContext = mListener.getContext();



    }

    @Override
    public int getCount() {
        return listOfTasks.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        Task task = listOfTasks.get(position);
        if (task._id != null)
            return task._id;
        else return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!hasTime) {
            convertView = ((Activity) mListener).getLayoutInflater().inflate(R.layout.p4_fragment_item_nodate, null);
        }else {
            convertView = ((Activity) mListener).getLayoutInflater().inflate(R.layout.p4_fragment_item, null);
        }
        prepareView(convertView);
        CreateList(position,listOfTasks.get(position));
        return convertView;
    }


    ////////////////


    public void prepareView(View view) {

        mView = view;

        root_task_item = (LinearLayout) view.findViewById(R.id.root_task_item);
        ll_task_item = (LinearLayout) view.findViewById(R.id.ll_task_item);
        ll_spliter = (LinearLayout) view.findViewById(R.id.ll_spliter);

        chk_checkBox = (CheckBox) view.findViewById(R.id.chk_checkBox);
        txt_task = (TextView) view.findViewById(R.id.txt_task);
        txt_time = (TextView) view.findViewById(R.id.txt_time);
        txt_taskCategory = (TextView) view.findViewById(R.id.txt_taskCategory);
        txt_taskTag = (TextView) view.findViewById(R.id.txt_taskTag);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        btn_edit = (ImageButton) view.findViewById(R.id.btn_edit);
        btn_move = (ImageButton) view.findViewById(R.id.btn_move);
        btn_star = (ImageButton) view.findViewById(R.id.btn_star);

        txt_timeName = (TextView) view.findViewById(R.id.txt_time_name);
        txt_splitterTime = (TextView) view.findViewById(R.id.txt_splitter_time);

        // mIdView = (TextView) view.findViewById(R.id.txt_task1);
        // mContentView = (TextView) view.findViewById(R.id.txt_time1);

        //Change Font
        // ViewGroup gr = (ViewGroup) root_task_item;
        // ChangeFonts hh = new ChangeFonts(mContext, gr);

    }


    public void CreateList(int position, Task thisTask) {
        if (listOfTasks != null && thisTask.isSplitter == true) {


            txt_timeName.setText(thisTask.splitter_TimesName);
            txt_splitterTime.setText(thisTask.splitter_time);
            ll_spliter.setVisibility(View.VISIBLE);
            ll_task_item.setVisibility(View.GONE);
        }
        if (listOfTasks != null && thisTask.isSplitter == false) {
            Log.i(Vars.TAG + "_p", "onBindViewHolder: -------" + position + "___" + thisTask._name);
            mTask = thisTask;


            //root_task_item
            chk_checkBox.setChecked(mTask.getDone());
            chk_checkBox.setTag(position) ;
            txt_task.setText(mTask.getTaskName());


            if(thisTask._importance == 0){
                btn_star.setImageResource(R.drawable.ic_star_border_black_24dp); //<<<<<<<<<<<<<<<<<<<<<<<<<<<< I'm Here
                btn_star.setTag(0);
            }else {
                btn_star.setImageResource(R.drawable.ic_star_black_24dp);
                btn_star.setTag(1);
            }



            txt_taskCategory.setText(mTask._catName);
            txt_taskCategory.setBackgroundColor(Color.parseColor(mTask._catColor));

            //String mean = myTime.checkWhen_str(listOfTasks.get(position)._stime_h,listOfTasks.get(position)._stime_m);

            if(hasTime) {
                txt_time.setText(thisTask._sTime_string + thisTask.getTime());
            }
//        txt_taskTag
//        ratingBar
//        btn_edit done
//        btn_move todo

            //mIdView.setText(listOfTasks.get(position).id);
            //mContentView.setText(listOfTasks.get(position).content);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onFragmentListClicked(mTask);


                        Intent intent = new Intent(mContext ,TaskItemDetailActivity.class);
                        intent.putExtra(TaskItemDetailFragment.ARG_ITEM_ID, mTask._id);
                        mContext.startActivity(intent);
                    }
                }
            });
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onFragmentListClicked(mTask);
                    }
                }
            });
            btn_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        ImageButton star = (ImageButton)v;
                       // if(star.getDrawable().get)    <<<<<<<<<<<<<<<<<<<<<<<<<<<< I'm Here
                        listOfTasks.get((int)v.getTag()).setImportantAndSave(1);
                    }
                }
            });
            chk_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listOfTasks.get((int)buttonView.getTag()).setDoneAndSave(isChecked);
                }
            });

        }
    }


}


