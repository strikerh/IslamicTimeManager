package com.example.hazemnabil.islamictodo2.addTask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.addTask.TaskContent.TaskItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SubTaskFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_COLUMN_TXT = "column-txt";
    private static final String ARG_COLUMN_CHK = "column-chk";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String mColumnTxt = "";
    private boolean mColumnChk = false;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView ;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SubTaskFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SubTaskFragment newInstance(int columnCount,String columnTxt,boolean columnChk) {
        SubTaskFragment fragment = new SubTaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_COLUMN_TXT, columnTxt);
        args.putBoolean(ARG_COLUMN_CHK, columnChk);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mColumnTxt = getArguments().getString(ARG_COLUMN_TXT);
            mColumnChk = getArguments().getBoolean(ARG_COLUMN_CHK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.p2_fragment_subtask_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
//            TaskContent.createTaskItem("hazem1");
//            TaskContent.createTaskItem("حازم 2");
//            TaskContent.createTaskItem("نتانتا3 ");
//            TaskContent.createTaskItem("منتمنت م تم4");
//            TaskContent.createTaskItem("نتا ات 5");
//            TaskContent.createTaskItem("ت منت  6");

            recyclerView.setAdapter(new MySubTaskRecyclerViewAdapter(TaskContent.ITEMS, mListener));


        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

     /*   if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(TaskItem item);
    }




    public void onAddSubtask(String txt){
        TaskContent.createTaskItem(txt);
        recyclerView.setAdapter(new MySubTaskRecyclerViewAdapter(TaskContent.ITEMS, mListener));

    }
    public void onRemoveSubtask(int pos){
        TaskContent.removeTaskItem(pos);
        recyclerView.setAdapter(new MySubTaskRecyclerViewAdapter(TaskContent.ITEMS, mListener));
    }
}
