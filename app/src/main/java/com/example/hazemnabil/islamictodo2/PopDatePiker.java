package com.example.hazemnabil.islamictodo2;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hazem.nabil on 16/02/2017.
 */

public class PopDatePiker extends DialogFragment implements View.OnClickListener{
    View view1;
    private DatePicker dp ;
    private AppCompatActivity mainActivity;
    private int placeID =-1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainActivity = (AppCompatActivity)  getActivity();
        view1 =  inflater.inflate(R.layout.popup_date_piker, container, false);
        dp = (DatePicker) view1.findViewById(R.id.datePicker2);
        Button bu = (Button) view1.findViewById(R.id.button);
        bu.setOnClickListener(this);


        return view1;

    }

    @Override
    public void onClick(View v) {
        String mydate;
        int d_day =0 ,d_month = 0 , d_year =0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mydate = dp.getDayOfMonth()+"/"+dp.getMonth()+"/" +dp.getYear();

            Toast.makeText(getContext(),mydate,Toast.LENGTH_SHORT).show();
            d_day = dp.getDayOfMonth();
            d_month =dp.getMonth()+1;
            d_year = dp.getYear();
        }
        this.dismiss();

        if(this.placeID !=-1) {
            this.SetDate(this.placeID, d_day, d_month, d_year);
        }
    }

    public  void SetDate(int placeID,int tday,int tmonth, int tyear) {
        Toast.makeText(mainActivity.getBaseContext(), "Date is:"+tday+"/"+tmonth+"/"+tyear  ,Toast.LENGTH_SHORT).show();
        TextView tv = (TextView) mainActivity.findViewById(placeID);
        tv.setText(tday+"/"+tmonth+"/"+tyear);
    }

    public  void SetOutputLocation(int placeID) {
       this.placeID = placeID;
    }


}
