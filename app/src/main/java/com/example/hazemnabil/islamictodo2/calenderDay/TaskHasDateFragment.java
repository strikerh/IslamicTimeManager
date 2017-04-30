package com.example.hazemnabil.islamictodo2.calenderDay;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.objData.MoDays;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link FragmentListener}
 * interface.
 */
public class TaskHasDateFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String YEAR = "year";
    private static final String TAG ="zoma" ;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int mDay = 1;
    private int mMonth = 1;
    private int mYear = 1;
    private FragmentListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskHasDateFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskHasDateFragment newInstance(int day,int month,int year) {
        TaskHasDateFragment fragment = new TaskHasDateFragment();
        Bundle args = new Bundle();
        args.putInt(DAY, day);
        args.putInt(MONTH, month);
        args.putInt(YEAR, year);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDay = getArguments().getInt(DAY);
            mMonth = getArguments().getInt(MONTH);
            mYear = getArguments().getInt(YEAR);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.p4_fragment_item_list, container, false);

        Log.i(TAG, "onCreateView++++++++++: "+this);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            Toast.makeText(context, "month:" + mMonth + " year:" + mYear, Toast.LENGTH_SHORT).show();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if (null != mListener) {
                MoDays newDay  = mListener.onChangeDay();
                //MoMonth moMonth = new MoMonth(4, 2017);
                //MoDays hh = moMonth.getMoDay(6);
               // hh.getTasks();
                recyclerView.setAdapter(new MyItemRecyclerViewAdapter(newDay.getTasksList(), mListener));


            }
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onCreateView----------: "+this);
        if (context instanceof FragmentListener) {
            mListener = (FragmentListener) context;
        } else {
            //throw new RuntimeException(context.toString()
            //        + " must implement OnListFragmentInteractionListener");
        }
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
//    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentListClicked(Task item);
//        MoDays onChangeDay();
//    }
}
