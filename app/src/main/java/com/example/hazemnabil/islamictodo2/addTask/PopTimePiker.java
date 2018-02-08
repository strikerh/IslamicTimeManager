package com.example.hazemnabil.islamictodo2.addTask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;
import com.example.hazemnabil.islamictodo2.myCalender.MyTime;
import com.example.hazemnabil.islamictodo2.myCalender.SmallTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hazem.nabil on 16/02/2017.
 */

public class PopTimePiker extends DialogFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private View view1;

    private static final int RELATED_TAB = 0;
    private static final int SPECIFIC_TAB = 1;


    private int placeID = -1;
    private WheelPicker wp_timeName;
    private int selectedTime = 0;
    private int selectedTab = 0;
    private boolean flag_isLoadingView = true;

    String mytime;
//    int t_hour24 = 0, t_hour12 = 0, t_minute = 0;
//    String t_ampm = "am";
//    int t_timeNameAfter;
//    int t_timeNameAfterMinutes;
    private Context mContext;
    private List<String> timeNameList;

    public MyTime myTime;
    public SmallTime smallTime;
    private Float startTime;
    private MyDate inputDate;

    final private int snap = 5;


    private TextView OutputTextView;

    private TimePicker tp;
    private AppCompatActivity mainActivity;

    private TextView txt_after;
    private Button btn_ok;
    private Button btn_cancel;
    private TextView txt_startTime;
    private TextView txt_endTime;
    public TextView txt_duration;
    private SeekBar sb_duration;
    private TextView txt_resultTime;
    private TextView txt_resultTimeAt;

    private LinearLayout tab_related;
    private LinearLayout tab_specific;
    private Button tabBtn_specify;
    private Button tabBtn_related;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        flag_isLoadingView = true;
        // Inflate the layout for this fragment
        mainActivity = (AppCompatActivity) getActivity();
        view1 = inflater.inflate(R.layout.p2_popup_time_piker, container, false);
        this.mContext =  getActivity();

        _intUiConnection();
        _prepareUI();
        _int();
        _actionListener();

        ChangeFonts.overrideFonts(mContext, view1);
        flag_isLoadingView = false;
        return view1;

    }




    private void  _intUiConnection() {

        this.tp = (TimePicker) view1.findViewById(R.id.timePicker);
        this.btn_ok = (Button) view1.findViewById(R.id.ok);
        this.btn_cancel = (Button) view1.findViewById(R.id.cancel);
        this.txt_after = (TextView) view1.findViewById(R.id.afterLabel);
        this.txt_startTime = (TextView) view1.findViewById(R.id.txt_startTime);
        this.txt_endTime = (TextView) view1.findViewById(R.id.txt_endTime);
        this.txt_after = (TextView) view1.findViewById(R.id.afterLabel);
        this.wp_timeName = (WheelPicker) view1.findViewById(R.id.wheel_timeName);
        this.txt_duration = (TextView) view1.findViewById(R.id.txt_duration);
        this.sb_duration = (SeekBar) view1.findViewById(R.id.sb_duration);
        this.txt_resultTime = (TextView) view1.findViewById(R.id.txt_resultTime);
        this.txt_resultTimeAt = (TextView) view1.findViewById(R.id.txt_resultTimeAt);

        this.tab_related = (LinearLayout) view1.findViewById(R.id.tap1);
        this.tab_specific = (LinearLayout) view1.findViewById(R.id.tap2);

        tabBtn_specify = (Button) view1.findViewById(R.id.tabBtn_specify);
        tabBtn_related = (Button) view1.findViewById(R.id.tabBtn_related);
    }


    private void _prepareUI() {

        tab_specific.setVisibility(View.GONE);
        getDialog().setTitle("تحديد الوقت");

        sb_duration.setOnSeekBarChangeListener(this);

        this.timeNameList= Arrays.asList( getResources().getStringArray(R.array.timeNames));

        ArrayList timeNameListForWp = new ArrayList<>( timeNameList);
        timeNameListForWp.remove(timeNameListForWp.size()-1);
        wp_timeName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        wp_timeName.setData(timeNameListForWp);

        wp_timeName.setTypeface(ChangeFonts.getTypeface(mContext));

        //Weel Selector
        wp_timeName.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {

            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                fillUiContents_pnl(position);


            }
        });
    }


    private void  _int(){

        if (this.placeID != -1) {

            if (this.placeID != -1) {
                this.OutputTextView = (TextView) mainActivity.findViewById(placeID);
            }

            if (OutputTextView.getTag() != null) {
                smallTime = (SmallTime) OutputTextView.getTag();
               /*t_hour12 = smt.hour12;
                t_hour24 = smt.hour24;
                t_minute = smt.minute;
                t_ampm = smt.ampm;*/


                wp_timeName.setSelectedItemPosition(smallTime.getTimeNameId());
                fillUiContents_pnl_init(smallTime.getTimeNameId());
                sb_duration.setProgress(smallTime.getMinutesAfterTimeName()/snap);

            } else {

                if(inputDate != null) {
                    myTime = new MyTime(inputDate.getCalendar());
                    smallTime = new SmallTime(Vars.TIME.SPECIFIC, inputDate.getCalendar());
                    Toast.makeText(mainActivity, "date: "+inputDate.getFullDate(Vars.D.MILADY), Toast.LENGTH_SHORT).show();
                }else {
                    myTime = new MyTime();
                    smallTime = new SmallTime();
                }
                fillUiContents_pnl_init(0);

            }
        }

    }
    //On Weel onItemSelected
    private void fillUiContents_pnl(int position){
        selectedTime = position;
        txt_after.setText("بعد "  + timeNameList.get(position) );
        txt_startTime.setText(""+timeNameList.get(position));
        txt_endTime.setText(""+timeNameList.get(position+1));
        if(inputDate != null) {
            myTime = new MyTime(inputDate.getCalendar());
            smallTime.setCalendar(inputDate.getCalendar());
            Toast.makeText(mainActivity, "date: "+inputDate.getFullDate(Vars.D.MILADY), Toast.LENGTH_SHORT).show();
        }else {
            myTime = new MyTime();
        }
        startTime = Float.valueOf(myTime.getPayerTimeAt(position));
        Float endTime = Float.valueOf(myTime.getPayerTimeAt(position+1));
        Float deltaTime = endTime - startTime;
        myTime.floatToTime24(deltaTime);

        sb_duration.setProgress(0);
        sb_duration.setMax((int)(deltaTime*60/snap));
        txt_duration.setText(""+ myTime.floatToTime24(deltaTime)+" - "+ sb_duration.getMax());
    }

    private void fillUiContents_pnl_init(int position){
        selectedTime = position;
        txt_after.setText("بعد "  + timeNameList.get(position) );
        txt_startTime.setText(""+timeNameList.get(position));
        txt_endTime.setText(""+timeNameList.get(position+1));
        if(inputDate != null) {
            if(OutputTextView.getTag()!= null)
                smallTime = (SmallTime)OutputTextView.getTag();
            else
                smallTime.setCalendar(inputDate.getCalendar());
            myTime = new MyTime(inputDate.getCalendar());

            Toast.makeText(mainActivity, "date: "+inputDate.getFullDate(Vars.D.MILADY), Toast.LENGTH_SHORT).show();
        }else {
            myTime = new MyTime();
        }
        startTime = Float.valueOf(myTime.getPayerTimeAt(position));
        Float endTime = Float.valueOf(myTime.getPayerTimeAt(position+1));
        Float deltaTime = endTime - startTime;
        myTime.floatToTime24(deltaTime);
        int dd = smallTime.getMinutesAfterTimeName();
        sb_duration.setMax((int)(deltaTime*60/snap));
        sb_duration.setProgress(dd);
        txt_duration.setText(""+ myTime.floatToTime24(deltaTime)+" - "+ sb_duration.getMax());
    }


    private void _actionListener() {


        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);



        View.OnClickListener btnOnclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tab_related.setVisibility(View.GONE);
                tab_specific.setVisibility(View.GONE);


                if (v.getId() == R.id.tabBtn_specify) {
                    tab_specific.setVisibility(View.VISIBLE);
                    selectedTab = SPECIFIC_TAB;
                } else {
                    tab_related.setVisibility(View.VISIBLE);
                    selectedTab = RELATED_TAB;
                }

            }
        };
        tabBtn_specify.setOnClickListener(btnOnclick);
        tabBtn_related.setOnClickListener(btnOnclick);
    }


    @Override
    public void onClick(View v) {
        this.dismiss();
        if (v.getId() == R.id.ok) {

            if(selectedTab == RELATED_TAB){
                if (this.placeID != -1) {
                    smallTime.setTimeType(Vars.TIME.RELATED);
                    this.SetTime(this.placeID,smallTime);
                }


            }else {
                smallTime.setTimeType(Vars.TIME.SPECIFIC);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    mytime = String.valueOf(tp.getHour()) + ":" + String.valueOf(tp.getMinute());
                    smallTime.setTime(tp.getHour(),tp.getMinute());

                    //t_hour24 = tp.getHour24();
                    //t_minute = tp.getMinute();
                } else {
                    mytime = String.valueOf(tp.getCurrentHour()) + ":" + String.valueOf(tp.getCurrentMinute());
                    smallTime.setTime(tp.getCurrentHour(),tp.getCurrentMinute());
                   // t_hour24 = tp.getCurrentHour();
                    //t_minute = tp.getCurrentMinute();

                }

                Toast.makeText(getContext(), mytime, Toast.LENGTH_SHORT).show();


                this.dismiss();
                if (this.placeID != -1) {
                    this.SetTime2(this.placeID, smallTime);
                }
            }
        }
//        AddTask2 ma = (AddTask2)  getActivity();
//        ma.SetTime(t_h12,t_m, t_ampm);
    }

    public void SetTime(int placeID,SmallTime smallTime) {
        //Toast.makeText(getBaseContext(), "Time is:"+th+":"+tm ,Toast.LENGTH_SHORT).show();
        TextView tv = (TextView) mainActivity.findViewById(placeID);
        //tv.setText(th + ":" + tm + " " + t_ampm);
        tv.setText(txt_resultTime.getText()+" ("+txt_resultTimeAt.getText()+")");

       // SmallTime st = new SmallTime(Vars.TIME.SPECIFIC,smallTime.getCalendar(),wp_timeName.getCurrentItemPosition(),sb_duration.getProgress()*snap );

        tv.setTag(smallTime);

    }
    public void SetTime_output(TextView tv,SmallTime smallTime) {

        myTime = new MyTime(smallTime.getCalendar());
        int[] gg = SmallTime.convert_minutesToDayHourMin_Array(smallTime.getMinutesAfterTimeName());

        String dd = "بعد "+myTime.getPayerNameAt(smallTime.getTimeNameId())+" بـ "+ gg[1]+":"+ gg[2]+" ";

        String hh = ""+ myTime.floatToTime12(smallTime.getTimeFloat(),false);



        tv.setText(hh+" ("+dd+")");

        // SmallTime st = new SmallTime(Vars.TIME.SPECIFIC,smallTime.getCalendar(),wp_timeName.getCurrentItemPosition(),sb_duration.getProgress()*snap );

        tv.setTag(smallTime);

    }
    public void SetTime2(int placeID, SmallTime smallTime) {

        TextView tv = (TextView) mainActivity.findViewById(placeID);
        tv.setText(smallTime.getHour12_Hour() + ":" + smallTime.getMinute() + " " + smallTime.getHour12_ampm());

        tv.setTag(smallTime);

    }
//    public void SetTime(int placeID, int th, int tm, String t_ampm) {
//        //Toast.makeText(getBaseContext(), "Time is:"+th+":"+tm ,Toast.LENGTH_SHORT).show();
//        TextView tv = (TextView) mainActivity.findViewById(this.placeID);
//        tv.setText(th + ":" + tm + " " + t_ampm);
//        tv.setTag(new smallTim1e(Vars.TIME.SPECIFIC,t_h24,t_h12,tm,t_ampm));
//
//    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        txt_duration.setText(""+ MyTime.minToDayHourMin(progress*snap)[1]+":"+ MyTime.minToDayHourMin(progress*snap)[2]);
        txt_resultTimeAt.setText("بعد "+txt_startTime.getText()+" بـ "+ MyTime.minToDayHourMin(progress*snap)[1]+":"+ MyTime.minToDayHourMin(progress*snap)[2]+" ");
        Float ttime = startTime + (progress*snap/60f);
        txt_resultTime.setText(""+ myTime.floatToTime12(ttime,false));
        if(!flag_isLoadingView)
            smallTime.setTime(myTime.floatToTimeArray(ttime)[0],myTime.floatToTimeArray(ttime)[1]);
        Log.i(Vars.TAG, "SmallTime -- H:"+smallTime.getHour24()+" - M:"+smallTime.getMinute()+" - day:"+smallTime.getCalendar().get(Calendar.DAY_OF_YEAR));
//        t_hour24 = myTime.floatToTimeArray(ttime)[0];
//        t_minute = myTime.floatToTimeArray(ttime)[1];


        //Toast.makeText(mContext, ""+progress, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }




    public void SetOutputLocation(int placeID) {
        this.placeID = placeID;

    }

    public void setInputDate(MyDate inputDate) {
        this.inputDate = inputDate;
    }


    public void setOutput(SmallTime st,TextView txt_time) {


        this.SetTime_output(txt_time, st);


//        Toast.makeText(getContext(), mytime, Toast.LENGTH_SHORT).show();


     //   this.SetTime2(txt_time.getId(), smallTime);


    }


    public SmallTime createSmallTime(int timeType, int hour24, int hour12, int minute, String ampm){
          SmallTime sm =  new SmallTime( timeType,inputDate.getCalendar()) ;
          sm.setTime(hour24,minute);
          return sm;

    }

    // Must change it to myTime
  /*  public class smallTim1e21212 {
        public  int timeName;
        public  int afterMinutes;
        public int timeType;
        public int hour24,hour12,minute;
        public String ampm;

        public smallTim1e(int timeType, int hour24, int hour12, int minute, String ampm) {
            this.timeType = timeType; //Vars.TIME.SPECIFIC;
            this.hour24 = hour24;
            this.hour12 = hour12;
            this.minute = minute;
            this.ampm = ampm;
        }

        public smallTim1e(int timeType, int hour24, int hour12, int minute, String ampm,int timeName,int afterMinutes) {
            this.timeType = timeType; //Vars.TIME.SPECIFIC;
            this.hour24 = hour24;
            this.hour12 = hour12;
            this.minute = minute;
            this.ampm = ampm;
            this.timeName = timeName;
            this.afterMinutes = afterMinutes;
        }
    }*/
}

