package com.example.hazemnabil.islamictodo2.addTask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hazem.nabil on 16/02/2017.
 */

public class PopTimePiker extends DialogFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private View view1;
    private TimePicker tp;
    private AppCompatActivity mainActivity;
    private int placeID = -1;
    private WheelPicker wp_timeName;
    private int selectedTime = 0;
    private int pnl = 0;

    String mytime;
    int t_h24 = 0, t_h12 = 0, t_m = 0;
    String ampm = "am";
    private Context mContext;
    private List<String> timeNameList;
    public MyTime myTime;

    final private int snap = 5;

    private TextView txt_after;
    private Button btnOk;
    private TextView txt_startTime;
    private TextView txt_endTime;
    public TextView txt_duration;
    private SeekBar sb_duration;
    private TextView txt_resultTime;
    private TextView txt_resultTimeAt;

    private Float startTime;
    private MyDate inputDate;
    private LinearLayout tab1;
    private LinearLayout tab2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = (AppCompatActivity) getActivity();
        view1 = inflater.inflate(R.layout.p2_popup_time_piker, container, false);
        this.mContext = (AppCompatActivity)  getActivity();


        this.tp = (TimePicker) view1.findViewById(R.id.timePicker);
        this.btnOk = (Button) view1.findViewById(R.id.ok);
        this.txt_after = (TextView)view1.findViewById(R.id.afterLabel);
        this.txt_startTime = (TextView)view1.findViewById(R.id.txt_startTime);
        this.txt_endTime = (TextView)view1.findViewById(R.id.txt_endTime);
        this.txt_after = (TextView)view1.findViewById(R.id.afterLabel);
        this.wp_timeName = (WheelPicker) view1.findViewById(R.id.wheel_timeName);
        this.txt_duration = (TextView)view1.findViewById(R.id.txt_duration);
        this.sb_duration = (SeekBar)view1.findViewById(R.id.sb_duration);
        this.txt_resultTime = (TextView)view1.findViewById(R.id.txt_resultTime);
        this.txt_resultTimeAt = (TextView)view1.findViewById(R.id.txt_resultTimeAt);

         this.tab1 = (LinearLayout)view1.findViewById(R.id.tap1);
         this.tab2 = (LinearLayout)view1.findViewById(R.id.tap2);
        tab2.setVisibility(View.GONE);

        getDialog().setTitle("تحديد الوقت");

        _actionFooter();


       // btnOk.setOnClickListener(this);
        sb_duration.setOnSeekBarChangeListener(this);

        this.timeNameList= Arrays.asList( getResources().getStringArray(R.array.timeNames));
        ArrayList timeNameListForWp = new ArrayList( timeNameList);
        timeNameListForWp.remove(timeNameListForWp.size()-1);
        //timeNameList.add((i+1)+"."+ MyDate.getMonthNameByType(i, sDate.getType()));
        wp_timeName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        wp_timeName.setData(timeNameListForWp);

        wp_timeName.setTypeface(ChangeFonts.getTypeface(mContext));

        wp_timeName.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {





            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                prepare_pnl(position);
//              selectedTime = position;
//                txt_after.setText("بعد "  + timeNameList.get(position) );
//                txt_startTime.setText(""+timeNameList.get(position));
//                txt_endTime.setText(""+timeNameList.get(position+1));
//                if(inputDate != null) {
//                    myTime = new MyTime(inputDate.getCalendar());
//                    Toast.makeText(mainActivity, "date: "+inputDate.getFullDate(Vars.D.MILADY), Toast.LENGTH_SHORT).show();
//                }else {
//                    myTime = new MyTime();
//                }
//                 startTime = Float.valueOf(myTime.getPayerTimeAt(position));
//                Float endTime = Float.valueOf(myTime.getPayerTimeAt(position+1));
//                Float deltaTime = endTime - startTime;
//                myTime.floatToTime24(deltaTime);
//
//                sb_duration.setProgress(0);
//                sb_duration.setMax((int)(deltaTime*60/snap));
//                txt_duration.setText(""+ myTime.floatToTime24(deltaTime)+" - "+ sb_duration.getMax());

            }
        });

        _int();




        Button tabBtn_specify = (Button) view1.findViewById(R.id.tabBtn_specify);
        Button tabBtn_related = (Button) view1.findViewById(R.id.tabBtn_related);


        View.OnClickListener btnOnclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                tab1.setVisibility(View.GONE);
                tab2.setVisibility(View.GONE);


                if(v.getId()== R.id.tabBtn_specify){
                    tab1.setVisibility(View.VISIBLE);
                    pnl = 0;
                }else {
                    tab2.setVisibility(View.VISIBLE);
                    pnl = 1;
                }

            }
        };
        tabBtn_specify.setOnClickListener(btnOnclick);
        tabBtn_related.setOnClickListener(btnOnclick);



        ChangeFonts.overrideFonts(mContext,view1);
        return view1;

    }


    private void  _int(){
        TextView tv;
        if (this.placeID != -1) {
            tv = (TextView) mainActivity.findViewById(placeID);

            if (tv.getTag() != null) {
                SmallTime smt = (SmallTime)tv.getTag();
               t_h12 = smt.hour12;
                t_h24 = smt.hour24;
                t_m = smt.minute;
                ampm = smt.ampm;
                wp_timeName.setSelectedItemPosition(smt.timeName);
                prepare_pnl(smt.timeName);
                sb_duration.setProgress(smt.afterMinutes/snap);

            } else {
                prepare_pnl(0);

            }
        }

    }

    private void prepare_pnl(int position){
        selectedTime = position;
        txt_after.setText("بعد "  + timeNameList.get(position) );
        txt_startTime.setText(""+timeNameList.get(position));
        txt_endTime.setText(""+timeNameList.get(position+1));
        if(inputDate != null) {
            myTime = new MyTime(inputDate.getCalendar());
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


    private void _actionFooter() {
      //  Button btnOk = (Button) view1.findViewById(R.id.ok);
        Button btn_cancel = (Button) view1.findViewById(R.id.cancel);

        btnOk.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        this.dismiss();
        if (v.getId() == R.id.ok) {

            if(pnl == 0){

                if (this.placeID != -1) {


                    if (t_h24 >= 12) {
                        t_h12 = t_h24 - 12;
                        ampm = "pm";
                    } else {
                        t_h12 = t_h24;
                    }

                    this.SetTime(this.placeID, t_h12, t_m, ampm);
                }


            }else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    mytime = String.valueOf(tp.getHour()) + ":" + String.valueOf(tp.getMinute());
                    t_h24 = tp.getHour();
                    t_m = tp.getMinute();
                } else {
                    mytime = String.valueOf(tp.getCurrentHour()) + ":" + String.valueOf(tp.getCurrentMinute());
                    t_h24 = tp.getCurrentHour();
                    t_m = tp.getCurrentMinute();

                }

                Toast.makeText(getContext(), mytime, Toast.LENGTH_SHORT).show();


                if (t_h24 >= 12) {
                    t_h12 = t_h24 - 12;
                    ampm = "pm";
                } else {
                    t_h12 = t_h24;
                }

                this.dismiss();
                if (this.placeID != -1) {
                    this.SetTime2(this.placeID, t_h12, t_m, ampm);
                }
            }
        }
//        AddTask2 ma = (AddTask2)  getActivity();
//        ma.SetTime(t_h12,t_m, ampm);
    }

    public void SetTime(int placeID, int th, int tm, String ampm) {
        //Toast.makeText(getBaseContext(), "Time is:"+th+":"+tm ,Toast.LENGTH_SHORT).show();
        TextView tv = (TextView) mainActivity.findViewById(placeID);
        //tv.setText(th + ":" + tm + " " + ampm);
        tv.setText(txt_resultTime.getText()+" ("+txt_resultTimeAt.getText()+")");
        tv.setTag(new SmallTime(Vars.TIME.SPECIFIC,t_h24,t_h12,tm,ampm,wp_timeName.getCurrentItemPosition(),sb_duration.getProgress()*snap));

    }
    public void SetTime2(int placeID, int th, int tm, String ampm) {
        //Toast.makeText(getBaseContext(), "Time is:"+th+":"+tm ,Toast.LENGTH_SHORT).show();
        TextView tv = (TextView) mainActivity.findViewById(placeID);
        tv.setText(th + ":" + tm + " " + ampm);
       // tv.setText(txt_resultTime.getText()+" ("+txt_resultTimeAt.getText()+")");
        tv.setTag(new SmallTime(Vars.TIME.SPECIFIC,t_h24,t_h12,tm,ampm));

    }
//    public void SetTime(int placeID, int th, int tm, String ampm) {
//        //Toast.makeText(getBaseContext(), "Time is:"+th+":"+tm ,Toast.LENGTH_SHORT).show();
//        TextView tv = (TextView) mainActivity.findViewById(this.placeID);
//        tv.setText(th + ":" + tm + " " + ampm);
//        tv.setTag(new SmallTime(Vars.TIME.SPECIFIC,t_h24,t_h12,tm,ampm));
//
//    }

    public void SetOutputLocation(int placeID) {
        this.placeID = placeID;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        txt_duration.setText(""+ MyTime.minToDayHourMin(progress*snap)[1]+":"+ MyTime.minToDayHourMin(progress*snap)[2]);
        txt_resultTimeAt.setText("بعد "+txt_startTime.getText()+" بـ "+ MyTime.minToDayHourMin(progress*snap)[1]+":"+ MyTime.minToDayHourMin(progress*snap)[2]+" ");
        Float ttime = startTime + (progress*snap/60f);
        txt_resultTime.setText(""+ myTime.floatToTime12(ttime,false));
        t_h24 = myTime.floatToTimeArray(ttime)[0];
        t_m = myTime.floatToTimeArray(ttime)[1];


        //Toast.makeText(mContext, ""+progress, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setInputDate(MyDate inputDate) {
        this.inputDate = inputDate;
    }

    // Must change it to myTime
    public class SmallTime {
        public  int timeName;
        public  int afterMinutes;
        public int timeType;
        public int hour24,hour12,minute;
        public String ampm;

        public SmallTime(int timeType, int hour24, int hour12, int minute, String ampm) {
            this.timeType = timeType; //Vars.TIME.SPECIFIC;
            this.hour24 = hour24;
            this.hour12 = hour12;
            this.minute = minute;
            this.ampm = ampm;
        }

        public SmallTime(int timeType, int hour24, int hour12, int minute, String ampm,int timeName,int afterMinutes) {
            this.timeType = timeType; //Vars.TIME.SPECIFIC;
            this.hour24 = hour24;
            this.hour12 = hour12;
            this.minute = minute;
            this.ampm = ampm;
            this.timeName = timeName;
            this.afterMinutes = afterMinutes;
        }
    }
}

