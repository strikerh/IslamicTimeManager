package com.example.hazemnabil.islamictodo2.addTask;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;

/**
 * Created by hazem.nabil on 16/02/2017.
 */

public class PopDurationPiker extends DialogFragment implements View.OnClickListener  , SeekBar.OnSeekBarChangeListener{
    View view1;
    AppCompatActivity mContext;
    SeekBar sk_min;
    SeekBar sk_hours;
    SeekBar sk_days;
    TextView txt_minutes;
    TextView txt_hours;
    TextView txt_days;
    TextView txt_duration;
    TextView outTxt;
    int outTxtId;

    int vMin = 0;
    int vHours = 0;
    int vDays = 0;

    private int startingValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view1 =  inflater.inflate(R.layout.p2_popup_duration_piker, container, false);
        getDialog().setTitle("تحديد المدة");
        mContext = (AppCompatActivity) getActivity();

        txt_minutes = (TextView) view1.findViewById(R.id.txt_minutes);
        txt_hours = (TextView) view1.findViewById(R.id.txt_hours);
        txt_days = (TextView) view1.findViewById(R.id.txt_days);

        txt_duration = (TextView) view1.findViewById(R.id.txt_duration);




        sk_min =(SeekBar) view1.findViewById(R.id.sk_min);
        sk_hours =(SeekBar) view1.findViewById(R.id.sk_hours);
        sk_days =(SeekBar) view1.findViewById(R.id.sk_days);

        sk_min.setOnSeekBarChangeListener(this);
        sk_hours.setOnSeekBarChangeListener(this);
        sk_days.setOnSeekBarChangeListener(this);
        sk_min.setProgress(vMin);
        sk_hours.setProgress(vHours);
        sk_days.setProgress(vDays);

        _init();

       // prepareDuration(0,0,15);


        _actionFooter();

         ChangeFonts.overrideFonts(mContext,view1);

        return view1;

    }
    private void _init() {



    }






    private void _actionFooter() {
        Button btnOk = (Button) view1.findViewById(R.id.ok);
        Button btn_cancel = (Button) view1.findViewById(R.id.cancel);
        btnOk.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }






    @Override
    public void onClick(View v) {

        this.dismiss();
        if(v.getId() == R.id.ok){
            outTxt = (TextView) mContext.findViewById(outTxtId);
            outTxt.setText( txt_duration.getText());
            outTxt.setTag(""+dayHourMinToMin(vDays,vHours,vMin));

            }
    }


    public void SetOutputLocation(int a_txt_date) {

        this.outTxtId =a_txt_date;

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if(seekBar.getId() == R.id.sk_min){
            txt_minutes.setText("" + progress+ " دقيقة");
            vMin = progress;

        }else if(seekBar.getId() == R.id.sk_hours){
            txt_hours.setText("" + progress+ " ساعة");
            vHours = progress;

        }else if(seekBar.getId() == R.id.sk_days){
            txt_days.setText("" + progress+ " يوم");
            vDays = progress;
        }


        prepareDuration(sk_days.getProgress(),sk_hours.getProgress(),sk_min.getProgress());



    }

    public String prepareDuration(int day, int hours, int min){

        if(txt_minutes != null) {
            txt_minutes.setText("" + min + " دقيقة");
            txt_hours.setText("" + hours + " ساعة");
            txt_days.setText("" + day + " يوم");
        }


        String txt = "";

        if(day !=0){
            txt +=  day + " يوم ";
        }

        if(hours !=0){
            if(day !=0)
                txt += "و ";

            txt +=  hours + " ساعة ";
        }



        String minOnly= "";
        if(min !=0){

            if(hours !=0)
                minOnly = "و ";
            minOnly +=  min + " دقيقة ";




            if(min ==15) {
                minOnly = "ربع ساعة";
                if (hours != 0)
                    minOnly = "و ربع";

            }

            if(min ==30) {
                minOnly = "نصف ساعة";
                if (hours != 0)
                    minOnly = "و نصق";

            }






            txt += minOnly;
        }
        if(txt_duration !=null)
        txt_duration.setText(txt);
        return txt;
    }

    public int[] minToDayHourMin(int min){
        int[] result = new int[3];
        result[0] =  min/24/60;
        result[1] = min/60%24;
        result[2] = min%60;

        return result;
    }

    private int dayHourMinToMin(int days,int hours, int min){
        return days*24*60+ hours*60+min;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if(seekBar.getId() == R.id.sk_min){
            txt_minutes.setTextColor(Color.RED);

        }else if(seekBar.getId() == R.id.sk_hours){
            txt_hours.setTextColor(Color.RED);

        }else if(seekBar.getId() == R.id.sk_days){
            txt_days.setTextColor(Color.RED);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(seekBar.getId() == R.id.sk_min){
            txt_minutes.setTextColor(Color.BLACK);

        }else if(seekBar.getId() == R.id.sk_hours){
            txt_hours.setTextColor(Color.BLACK);

        }else if(seekBar.getId() == R.id.sk_days){
            txt_days.setTextColor(Color.BLACK);
        }
    }

    public void setDefault(int aDefault) {
        this.startingValue = aDefault;


        int[] duration = minToDayHourMin(startingValue);

        vDays = duration[0];
        vHours = duration[1];
        vMin = duration[2];


    }
}
