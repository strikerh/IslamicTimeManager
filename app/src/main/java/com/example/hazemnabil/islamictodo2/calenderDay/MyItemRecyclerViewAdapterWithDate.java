package com.example.hazemnabil.islamictodo2.calenderDay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.dummy.DummyContent.DummyItem;
import com.example.hazemnabil.islamictodo2.myCalender.MyTime;
import com.example.hazemnabil.islamictodo2.objData.Task;
import com.example.hazemnabil.islamictodo2.viewTask.TaskItemDetailActivity;
import com.example.hazemnabil.islamictodo2.viewTask.TaskItemDetailFragment;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link FragmentListener}.
 * TODO: Replace the implementation with Code for your data type.
 */
@Deprecated
public class MyItemRecyclerViewAdapterWithDate extends RecyclerView.Adapter<MyItemRecyclerViewAdapterWithDate.ViewHolder> {

    private static final String TAG ="zoma" ;
    private final List<Task> taskList;
    private Task[] allTasks;
    private final FragmentListener mListener;
    private Context mContext ;
    MyTime myTime = new MyTime();


    public MyItemRecyclerViewAdapterWithDate(MyTime time, List<Task> taskList, FragmentListener listener) {
        this.taskList =  taskList;
        this.mListener = listener;
         myTime = time;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.p4_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {  // Every Row
        if(taskList !=null && taskList.get(position).isSplitter == true) {

            holder.txt_timeName.setText(taskList.get(position).splitter_TimesName);
            holder.txt_splitterTime.setText(taskList.get(position).splitter_time);
            holder.ll_spliter.setVisibility(View.VISIBLE);
            holder.ll_task_item.setVisibility(View.GONE);
        }
       if(taskList !=null && taskList.get(position).isSplitter == false) {
           holder.mTask = taskList.get(position);



           //holder.root_task_item
           holder.chk_checkBox.setChecked(holder.mTask.getDone());
           holder.txt_task.setText(holder.mTask.getTaskName());
           holder.txt_task.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Intent intent = new Intent(mContext, TaskItemDetailActivity.class);
                   intent.putExtra(TaskItemDetailFragment.ARG_ITEM_ID, holder.mTask._id);

                   mContext.startActivity(intent);
               }
           });



          holder.txt_taskCategory.setText(holder.mTask._catName);
           holder.txt_taskCategory.setBackgroundColor(Color.parseColor(holder.mTask._catColor));

          // String mean = myTime.checkWhen_str(taskList.get(position)._stime_h,taskList.get(position)._stime_m);

           holder.txt_time.setText(taskList.get(position)._sTime_string +taskList.get(position).getTime());
//        holder.txt_taskTag
//        holder.ratingBar
//        holder.btn_edit done
//        holder.btn_move todo

           //holder.mIdView.setText(taskList.get(position).id);
           //holder.mContentView.setText(taskList.get(position).content);

           holder.mView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (null != mListener) {
                       // Notify the active callbacks interface (the activity, if the
                       // fragment is attached to one) that an item has been selected.
                       mListener.onFragmentListClicked(holder.mTask);
                   }
               }
           });
           holder.btn_edit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (null != mListener) {
                       // Notify the active callbacks interface (the activity, if the
                       // fragment is attached to one) that an item has been selected.
                       mListener.onFragmentListClicked(holder.mTask);
                   }
               }
           });
           holder.chk_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   holder.mTask.setDoneAndSave(isChecked);
               }
           });
       }
    }

    @Override
    public int getItemCount() {
        if(taskList !=null) {
            return taskList.size();
        }else  return 0;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
       // public final TextView mIdView;
       // public final TextView mContentView;
        public final LinearLayout root_task_item;
        public final LinearLayout ll_task_item;
        public final LinearLayout ll_spliter ;
        public final CheckBox chk_checkBox;
        public final TextView txt_task;
        public final TextView txt_time;
        public final TextView txt_taskCategory;
        public final TextView txt_taskTag;
        public final RatingBar ratingBar;
        public final ImageButton btn_edit;
        public final ImageButton btn_move;
        public final TextView txt_timeName;
        public final TextView txt_splitterTime;

        public int TaskId;


        public Task mTask;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            root_task_item = (LinearLayout) view.findViewById(R.id.root_task_item);
            ll_task_item = (LinearLayout) view.findViewById(R.id.ll_task_item);
            ll_spliter =  (LinearLayout) view.findViewById(R.id.ll_spliter);

            chk_checkBox = (CheckBox) view.findViewById(R.id.chk_checkBox);
            txt_task = (TextView) view.findViewById(R.id.txt_task);
            txt_time = (TextView) view.findViewById(R.id.txt_time);
            txt_taskCategory = (TextView) view.findViewById(R.id.txt_taskCategory);
            txt_taskTag = (TextView) view.findViewById(R.id.txt_taskTag);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            btn_edit = (ImageButton) view.findViewById(R.id.btn_edit);
            btn_move = (ImageButton) view.findViewById(R.id.btn_move);

            txt_timeName = (TextView) view.findViewById(R.id.txt_time_name);
            txt_splitterTime = (TextView) view.findViewById(R.id.txt_splitter_time);

           // mIdView = (TextView) view.findViewById(R.id.txt_task1);
           // mContentView = (TextView) view.findViewById(R.id.txt_time1);

            //Change Font
            ViewGroup gr =(ViewGroup)root_task_item;
            ChangeFonts hh = new ChangeFonts(mContext,gr);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txt_task.getText() + "'";
        }
    }
}
