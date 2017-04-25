package com.example.hazemnabil.islamictodo2.calenderDay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.dummy.DummyContent.DummyItem;
import com.example.hazemnabil.islamictodo2.objData.Task;

import java.util.Arrays;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link FragmentListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private static final String TAG ="zoma" ;
    private final List<Task> mValues;
    private Task[] allTasks;
    private final FragmentListener mListener;
    private Context mContext ;

    public MyItemRecyclerViewAdapter(Task[] items, FragmentListener listener) {
        mValues = Arrays.asList( items);
        mListener = listener;
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
        holder.mItem = mValues.get(position);


        //holder.root_task_item
        //holder.chk_checkBox
        holder.txt_task.setText(mValues.get(position).getTaskName());
        //holder.txt_time.setText(mValues.get(position).g);
//        holder.txt_taskCategory
//        holder.txt_taskTag
//        holder.ratingBar
//        holder.btn_edit done
//        holder.btn_move todo

        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onFragmentListClicked(holder.mItem);
                }
            }
        });
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onFragmentListClicked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
       // public final TextView mIdView;
       // public final TextView mContentView;
        public final LinearLayout root_task_item;
        public final TextView chk_checkBox;
        public final TextView txt_task;
        public final TextView txt_time;
        public final TextView txt_taskCategory;
        public final TextView txt_taskTag;
        public final RatingBar ratingBar;
        public final ImageButton btn_edit;
        public final ImageButton btn_move;



        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            root_task_item = (LinearLayout) view.findViewById(R.id.root_task_item);
            chk_checkBox = (TextView) view.findViewById(R.id.chk_checkBox);
            txt_task = (TextView) view.findViewById(R.id.txt_task);
            txt_time = (TextView) view.findViewById(R.id.txt_time);
            txt_taskCategory = (TextView) view.findViewById(R.id.txt_taskCategory);
            txt_taskTag = (TextView) view.findViewById(R.id.txt_taskTag);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            btn_edit = (ImageButton) view.findViewById(R.id.btn_edit);
            btn_move = (ImageButton) view.findViewById(R.id.btn_move);

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
