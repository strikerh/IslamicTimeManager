package com.example.hazemnabil.islamictodo2.calenderDay;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.CheckableLinearLayout;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.SmallDate;

import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeekTabFragment extends Fragment  {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = Vars.TAG+"_WeekTabFrag";

    public int tabNum;
    private SmallDate selectedDay;
    private CheckableLinearLayout[] chk = new  CheckableLinearLayout[7];
    private TextView[] txtDay = new TextView[7];
    private TextView[] txtDayName = new TextView[7];
    private WeekTabListener mListener ;



    public WeekTabFragment() {
    }
    public static WeekTabFragment newInstance(int sectionNumber) {
        Log.i(TAG, "____________ newInstance: ");
        WeekTabFragment fragment = new WeekTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);


        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "____________ onAttach: "+this.tabNum);
        super.onAttach(context);

        tabNum = getArguments().getInt(ARG_SECTION_NUMBER);

        if (context instanceof FragmentListener) {
            mListener = (WeekTabListener) context;

        } else {
            //throw new RuntimeException(context.toString()
            //        + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "____________ onCreate: "+this.tabNum);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "______X_____ onDestroyView: "+this.tabNum);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "_____X______ onPause: "+this.tabNum);
        unCheckDay();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "_____X______ onDetach: "+this.tabNum);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "____________ onCreateView: "+this.tabNum);
        View rootView = inflater.inflate(R.layout.p4_fragment_days_tab, container, false);



        txtDay[0] = (TextView) rootView.findViewById(R.id.txt_d0);
        txtDay[1] = (TextView) rootView.findViewById(R.id.txt_d1);
        txtDay[2] = (TextView) rootView.findViewById(R.id.txt_d2);
        txtDay[3] = (TextView) rootView.findViewById(R.id.txt_d3);
        txtDay[4] = (TextView) rootView.findViewById(R.id.txt_d4);
        txtDay[5] = (TextView) rootView.findViewById(R.id.txt_d5);
        txtDay[6] = (TextView) rootView.findViewById(R.id.txt_d6);

        txtDayName[0] = (TextView) rootView.findViewById(R.id.txt_dName0);
        txtDayName[1] = (TextView) rootView.findViewById(R.id.txt_dName1);
        txtDayName[2] = (TextView) rootView.findViewById(R.id.txt_dName2);
        txtDayName[3] = (TextView) rootView.findViewById(R.id.txt_dName3);
        txtDayName[4] = (TextView) rootView.findViewById(R.id.txt_dName4);
        txtDayName[5] = (TextView) rootView.findViewById(R.id.txt_dName5);
        txtDayName[6] = (TextView) rootView.findViewById(R.id.txt_dName6);


        chk[0] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d0) ;
        chk[1] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d1) ;
        chk[2] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d2) ;
        chk[3] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d3) ;
        chk[4] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d4) ;
        chk[5] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d5) ;
        chk[6] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d6) ;


        Calendar day = Calendar.getInstance();

       // day.setFirstDayOfWeek(Calendar.SATURDAY);
        int pastYear = day.get(Calendar.YEAR)-1;
        day.set(pastYear,0,1);
        day.set(Calendar.DAY_OF_WEEK,0);
        day.add(Calendar.WEEK_OF_YEAR,-1);

        day.add(Calendar.WEEK_OF_YEAR,getArguments().getInt(ARG_SECTION_NUMBER));
        Log.i(TAG, "-----------------"+day.get(Calendar.YEAR) );
        for (int i = 0; i <7 ; i++) {


            txtDay[i].setText(""+day.get(Calendar.DAY_OF_MONTH));
            String[] dayNames =    {"سبت","أحد","اثنين","ثلاثاء","أربعاء","خميس","جمعة"};
            //txtDayName[i].setText(day.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT,new Locale("ar")));
            txtDayName[i].setText(dayNames[day.get(Calendar.DAY_OF_WEEK)-1]);
            Calendar hh3 = day;
            chk[i].myDate = day ;
            chk[i].nDay = day.get(Calendar.DAY_OF_MONTH) ;
            chk[i].nMonth011 = day.get(Calendar.MONTH) ;
            chk[i].nYear = day.get(Calendar.YEAR) ;
            chk[i].nTab = getArguments().getInt(ARG_SECTION_NUMBER) ;

            Log.i(TAG, "onCreateView-Tab: "+getArguments().getInt(ARG_SECTION_NUMBER)+" - "+day.get(Calendar.DAY_OF_YEAR)+" : day["+i+"] = "+ day.get(Calendar.DAY_OF_MONTH)+"-"+day.get(Calendar.MONTH)+"-"+day.get(Calendar.YEAR) );
            day.add(Calendar.DAY_OF_MONTH,1);

            chk[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "____________ onClick: ");
                    CheckableLinearLayout view = (CheckableLinearLayout) v;

                    unCheckDay();

                    for (int i = 0; i < 7; i++) {
                        chk[i].setChecked(false);
                    }
                    view.setChecked(true);
                    //CalenderDay.

                    if (null != mListener) {
                       Calendar cal =  view.myDate;
                        mListener.onWeekTabClicked(view,""+((TextView)view.getChildAt(1)).getText());

                    }
                }
            });
        }
        //Change Font
        ViewGroup gr =(ViewGroup)rootView;
        ChangeFonts hh = new ChangeFonts(this.getContext(),gr);

         mListener.onWeekTabLoaded(this , tabNum);


        return rootView;
    }


    public void unCheckDay() {
        Log.i(TAG, "____________ checkDay: ");
//        chk[0] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d0) ;
//        chk[1] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d1) ;
//        chk[2] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d2) ;
//        chk[3] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d3) ;
//        chk[4] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d4) ;
//        chk[5] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d5) ;
//        chk[6] = (CheckableLinearLayout) rootView.findViewById(R.id.btn_d6) ;
        for (int i = 0; i < 7; i++) {
            chk[i].setChecked(false);
        }

    }
    public void checkDay(SmallDate smDay) {
        Log.i(TAG, "____________ checkDay: ");
        for (int i = 0; i < 7; i++) {
            chk[i].setChecked(false);
            if(chk[i].nMonth011 == smDay.month011 && chk[i].nDay == smDay.day && chk[i].nYear == smDay.year ){
                chk[i].setChecked(true);
            }
        }

    }


    public interface WeekTabListener {
        // TODO: Update argument type and name
        void onWeekTabClicked(CheckableLinearLayout view, String test);

        void onWeekTabLoaded(WeekTabFragment frag,int tabNum) ;
    }

}
