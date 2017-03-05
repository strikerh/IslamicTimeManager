package com.example.hazemnabil.islamictodo2;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by hazem.nabil on 16/02/2017.
 */

public class PopTimePiker extends DialogFragment implements View.OnClickListener{
    private View view1;
    private TimePicker tp ;
    private AppCompatActivity mainActivity;
    private int placeID =-1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = (AppCompatActivity)  getActivity();
        view1 =  inflater.inflate(R.layout.popup_time_piker, container, false);
        tp = (TimePicker) view1.findViewById(R.id.timePicker);
        Button bu = (Button) view1.findViewById(R.id.button);
        bu.setOnClickListener(this);

        return view1;

    }

    @Override
    public void onClick(View v) {
        String mytime;
        int t_h24 =0 ,t_h12 =0 ,t_m = 0;
        String ampm = "am";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mytime = String.valueOf(tp.getHour())+":"+String.valueOf(tp.getMinute());

            Toast.makeText(getContext(),mytime,Toast.LENGTH_SHORT).show();

            t_h24 = tp.getHour();
            t_m =tp.getMinute();
            if(t_h24 >=12){
                t_h12 = t_h24 - 12;
                ampm = "pm";
            }else{
                t_h12 = t_h24;
            }
        }
        this.dismiss();
        if(this.placeID !=-1) {
            this.SetTime(this.placeID, t_h12, t_m,  ampm);
        }
//        AddTask2 ma = (AddTask2)  getActivity();
//        ma.SetTime(t_h12,t_m, ampm);
    }

    public  void SetTime(int placeID ,int th,int tm,String ampm) {
        //Toast.makeText(getBaseContext(), "Time is:"+th+":"+tm ,Toast.LENGTH_SHORT).show();
        TextView tv = (TextView) mainActivity.findViewById(R.id.txt_time);
        tv.setText(th+":"+tm+" "+ampm);
    }

    public  void SetOutputLocation(int placeID) {
        this.placeID = placeID;
    }
}
