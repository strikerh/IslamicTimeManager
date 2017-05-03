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
import com.example.hazemnabil.islamictodo2.objData.Day;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link FragmentListener}
 * interface.
 */
public class TaskHasDateFragment extends Fragment {

    // TODO: Customize parameter argument names
    public static final String DAY = "day";
    public static final String MONTH = "month011";
    public static final String YEAR = "year";
    private static final String TAG ="zoma_TaskFrag" ;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int mDay = 6;
    private int mMonth011 = 4;
    private int mYear = 2017;
    private FragmentListener mListener;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskHasDateFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskHasDateFragment newInstance(int day,int month011,int year) {
        Log.i(TAG, "____________ newInstance: ");
        TaskHasDateFragment fragment = new TaskHasDateFragment();
        Bundle args = new Bundle();
        args.putInt(DAY, day);
        args.putInt(MONTH, month011);
        args.putInt(YEAR, year);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "____________ 2.onCreate: ");
        if (getArguments() != null) {
            mDay = getArguments().getInt(DAY);
            mMonth011 = getArguments().getInt(MONTH);
            mYear = getArguments().getInt(YEAR);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "____________ 3.onCreateView: "+this);
        View view = inflater.inflate(R.layout.p4_fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            updateView( mDay, mMonth011, mYear);
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "____________ 1.onAttach: "+this);
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
        Log.i(TAG, "____________ onDetach: "+this);
        mListener = null;
    }



    public void updateView(int mDay,int mMonth011, int mYear) {
        Log.i(TAG, "_________ 4.1.updateView: "+this);
        this.mDay = mDay;
        this.mMonth011 = mMonth011;
        this.mYear = mYear;

        if (null != mListener) {
           // MoDays newDay  = mListener.onChangeDay();
            Context mContext   = mListener.getContext();
           // MoMonth moMonth = new MoMonth(mContext ,mMonth011, mYear);
            Day hh = new Day(mContext,mDay,mMonth011,mYear);
            hh.getTasks();
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(hh.getTasksList(), mListener));
            Toast.makeText(getContext(), "Day: " + hh._day_n + " / " + (hh._month_n011 +1 )+ " / " + hh._year_n, Toast.LENGTH_SHORT).show();

        }
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
