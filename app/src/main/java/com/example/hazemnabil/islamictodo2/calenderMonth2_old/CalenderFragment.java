package com.example.hazemnabil.islamictodo2.calenderMonth2_old;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.monthCalender.MoMonth2;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.MyCalender;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CalenderFragment extends Fragment {

    //  Customize parameter argument names
    private static final String ARG_MONTH011 = "column-month011";
    private static final String ARG_YEAR = "column-year";

    // Customize parameters
    private final int mColumnCount = 7;
    private int month011;
    private int year;
    private OnListFragmentInteractionListener mListener;
    private MoMonth2 mMonth;
    private RecyclerView  calenderTableRecyclerView;
    private Context mContext;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CalenderFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CalenderFragment newInstance(int month011, int year) {
        CalenderFragment fragment = new CalenderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MONTH011, month011);
        args.putInt(ARG_YEAR, year);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {  //2
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            month011 = getArguments().getInt(ARG_MONTH011);
            year = getArguments().getInt(ARG_YEAR);
        }else {
            MyCalender myCalDate;
            myCalDate = new MyCalender(mContext);
            month011 = myCalDate._month_n011;
            year = myCalDate._year_n;
            myCalDate = null;
        }
    }  //2

    long startTime ;
    long stopTime ;
    long elapsedTime ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  //3
                             Bundle savedInstanceState) {
        //>>>
         startTime = System.currentTimeMillis();
        //<<<

        View view = inflater.inflate(R.layout.p5_fragment_calender_list, container, false);


       // android:rotationY="180"
        view.setRotationY(180);
        mContext = view.getContext();

        mMonth = new MoMonth2(view.getContext(),month011,year);

        _prepareWeekDaysBar(view);



        //make the RecyclerView in Grid Layout
        calenderTableRecyclerView.setLayoutManager(new GridLayoutManager(mContext, mColumnCount));

        DaysRecyclerViewAdapter dr = new DaysRecyclerViewAdapter(mMonth, mListener);
        calenderTableRecyclerView.setAdapter(dr);

        //>>>
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        Log.i(Vars.TAG+"_", "onCreateView 2222: "+ elapsedTime);
        //<<<

        return view;
    }


    private void _prepareWeekDaysBar(View view){
        //--> Prepare the Week Bar -->>

        LinearLayout dayCont = (LinearLayout)view.findViewById(R.id.llo_daysCont);
        calenderTableRecyclerView = (RecyclerView)view.findViewById(R.id.rcy_theCalender);
        TextView[] txtDays = new TextView[7] ;
        txtDays[0] = (TextView)view.findViewById(R.id.txt_t1);
        txtDays[1] = (TextView)view.findViewById(R.id.txt_t2);
        txtDays[2] = (TextView)view.findViewById(R.id.txt_t3);
        txtDays[3] = (TextView)view.findViewById(R.id.txt_t4);
        txtDays[4] = (TextView)view.findViewById(R.id.txt_t5);
        txtDays[5] = (TextView)view.findViewById(R.id.txt_t6);
        txtDays[6] = (TextView)view.findViewById(R.id.txt_t7);



        for (int i = 0; i <7 ; i++) {
            txtDays[i].setText( MyCalender.getWeekName(i));
        }
        // <<-----
    }   // used in onCreateView

    public void updateView(int mMonth011, int mYear){
        mMonth = new MoMonth2(mContext,mMonth011,mYear);
        DaysRecyclerViewAdapter hghg = new DaysRecyclerViewAdapter(mMonth, mListener);
        calenderTableRecyclerView.setAdapter(hghg);
       // calenderTableRecyclerView.swapAdapter(new DaysRecyclerViewAdapter(mMonth, mListener),false);
        //( (DaysRecyclerViewAdapter)calenderTableRecyclerView.getAdapter()).
    }

    @Override
    public void onAttach(Context context) {  //1
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }  //1


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_YEAR,year);
        outState.putInt(ARG_MONTH011,month011);
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
        void onListFragmentInteraction(int positionOnTable);
    }
}
