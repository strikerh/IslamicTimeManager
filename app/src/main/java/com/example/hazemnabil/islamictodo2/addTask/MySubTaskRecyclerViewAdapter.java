package com.example.hazemnabil.islamictodo2.addTask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.addTask.SubTaskFragment.OnListFragmentInteractionListener;
import com.example.hazemnabil.islamictodo2.addTask.TaskContent.TaskItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link TaskItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySubTaskRecyclerViewAdapter extends RecyclerView.Adapter<MySubTaskRecyclerViewAdapter.ViewHolder> {

    private final List<TaskItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MySubTaskRecyclerViewAdapter(List<TaskItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.p2_fragment_subtask, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
       // holder.mIdView.setText(mValues.get(position).id);
        holder.mTodoItemChk.setText(mValues.get(position).content);
        holder.mPos = position;

        holder.mDeletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"hello:"+holder.toString(""),Toast.LENGTH_SHORT).show();
                v.getContext();
                SubTaskFragment f =(SubTaskFragment)((AddTask2)v.getContext()).getSupportFragmentManager().findFragmentById(R.id.fragment3);
                f.onRemoveSubtask(holder.mPos);

                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);

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
        //public final TextView mContentView;
        public int mPos;
        public final CheckBox mTodoItemChk;
        public final ImageButton mDeletBtn;
        public final ImageButton mMoveBtn;


        public TaskItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
          //  mIdView = (TextView) view.findViewById(R.id.ch_todoItem);
           // mContentView = (TextView) view.findViewById(R.id.ch_todoItem);
            mTodoItemChk = (CheckBox)view.findViewById(R.id.ch_todoItem);
            mDeletBtn = (ImageButton) view.findViewById(R.id.btn_delete);
            mMoveBtn = (ImageButton) view.findViewById(R.id.btn_delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTodoItemChk.getText() + "'";
        }
        public String toString(String hh) {
            return mTodoItemChk.getText().toString();
        }

    }
}
