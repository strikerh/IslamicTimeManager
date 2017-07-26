package com.example.hazemnabil.islamictodo2.addTask;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hazem.nabil on 16/02/2017.
 */

public class PopDatePiker extends DialogFragment implements View.OnClickListener{
    View view1;
    private int defaultDateType = Vars.MILADY;
    private int neededDateType = defaultDateType ;

    public static final int LONG = 0;
    public static final int SHORT = 1;
    private int mOutFormat = LONG;

    private MyDate today;
    private MyDate sDate;
    private String[] sdateStr = new String[3];
   // private DatePicker dp ;
    private AppCompatActivity mComtext;
    private int placeID =-1;

    private WheelPicker wp_Day, wp_Month, wp_Year;
    private TextView txt_MiladyDate, txt_HijryDate;
    private TextView txt_outputLocation;
    private RadioGroup radioGroup;
    private RadioButton rd_hijry,rd_milady;


    private ArrayList<Integer> years;
    private ArrayList<String> months;
    private ArrayList<Integer> days;

    private int selectedType = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view1 =  inflater.inflate(R.layout.p2_popup_date_piker, container, false);
        getDialog().setTitle("تحديد التاريخ");
        mComtext = (AppCompatActivity)  getActivity();

        _init();

        _prepareWpYearAndMonth();
        _prepareTheDefault();
        changeTextField();

        _setListener();
        _actionFooter();

         ChangeFonts.overrideFonts(mComtext,view1);

        return view1;

    }
    private void _init() {

        txt_outputLocation = (TextView) mComtext.findViewById(placeID);

        wp_Day = (WheelPicker) view1.findViewById(R.id.wheel_day);
        wp_Month = (WheelPicker) view1.findViewById(R.id.wheel_month);
        wp_Year = (WheelPicker) view1.findViewById(R.id.wheel_year);
        txt_MiladyDate = (TextView) view1.findViewById(R.id.txt_milady);
        txt_HijryDate = (TextView) view1.findViewById(R.id.txt_hijry);

        radioGroup = (RadioGroup)  view1.findViewById(R.id.rdg_datetype1);
        rd_hijry = (RadioButton)   view1.findViewById(R.id.rd_hijry1);
        rd_milady = (RadioButton)   view1.findViewById(R.id.rd_milady1);

        //set the default data  =  Needed
        if(neededDateType == Vars.MILADY)
            rd_milady.setChecked(true);
        if(neededDateType == Vars.HIJRY)
            rd_hijry.setChecked(true);


        if(radioGroup.getCheckedRadioButtonId() == R.id.rd_milady1){
            selectedType = Vars.MILADY;
        }
        if(radioGroup.getCheckedRadioButtonId() == R.id.rd_hijry1){
            selectedType = Vars.HIJRY;
        }



        //restore ald data
        if(txt_outputLocation.getTag()!=null){
            sDate = (MyDate) txt_outputLocation.getTag();
        }else {
            sDate = new MyDate(selectedType);
        }

        selectedType =  convertSdateTypeTo(selectedType);
        //neededDateType = convertSdateTypeTo(neededDateType);

    }

    private void _prepareWpYearAndMonth() {
        years= new ArrayList<>();
        months= new ArrayList<>();
        days= new ArrayList<>();

        for (int i = sDate.getYear()-20; i < sDate.getYear()+20; i++) {
            years.add(i);
        }
        for (int i = 0; i < 12; i++) {
            months.add((i+1)+"."+ MyDate.getMonthNameByType(i, sDate.getType()));

        }
        prepareWpDays();
        wp_Year.setData(years);
        wp_Month.setData(months);





    }

    private void _prepareTheDefault(){
        // set default date (today)
        wp_Year.setSelectedItemPosition(years.indexOf(sDate.getYear()));
        wp_Month.setSelectedItemPosition(sDate.getMonth011());
        wp_Day.setSelectedItemPosition(days.indexOf(sDate.getDay()));
    }

    private void changeTextField() {

        int  otherType;
        if(selectedType == Vars.MILADY)
            otherType = Vars.HIJRY; else  otherType = Vars.MILADY;


        if(mOutFormat == LONG) {
            sdateStr[selectedType] = sDate.getFullDate(selectedType);
            sdateStr[otherType] = sDate.getFullDate(otherType);
        }else {
            sdateStr[selectedType] = sDate.getShortDate(selectedType);
            sdateStr[otherType] = sDate.getShortDate(otherType);
        }

        txt_MiladyDate.setText(sdateStr[Vars.MILADY]);
        txt_HijryDate.setText(sdateStr[Vars.HIJRY]) ;
    }

    private void _setListener() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if(group.getCheckedRadioButtonId() == R.id.rd_milady1){
                    selectedType = Vars.MILADY;
                }
                if(group.getCheckedRadioButtonId() == R.id.rd_hijry1){
                    selectedType = Vars.HIJRY;
                }

                selectedType =  convertSdateTypeTo(selectedType);

                _prepareWpYearAndMonth();
                _prepareTheDefault();
                changeTextField();
            }
        });

        wp_Month.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                sDate.setDate(sDate.getDay(), wp_Month.getCurrentItemPosition(), sDate.getYear());
                sDate.setAltCalendar();
                changeTextField();
            }
        });
        wp_Year.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                sDate.setDate(sDate.getDay(), sDate.getMonth011(),(int)data);
                sDate.setAltCalendar();
                changeTextField();
            }
        });
        wp_Day.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                sDate.setDate(wp_Day.getCurrentItemPosition()+1 , sDate.getMonth011(), sDate.getYear());
                sDate.setAltCalendar();
                changeTextField();
            }
        });
    }

    private void _actionFooter() {
        Button btnOk = (Button) view1.findViewById(R.id.ok);
        Button btn_cancel = (Button) view1.findViewById(R.id.cancel);
        btnOk.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }






    private int convertSdateTypeTo(int type){
        if(type == Vars.HIJRY){
            if(sDate.getType()!= Vars.HIJRY) {
                sDate.convertTo(Vars.HIJRY);
                sDate.setAltCalendar();
            }
        }else {
            type = Vars.MILADY;
            if(sDate.getType()!= Vars.MILADY) {
                sDate.convertTo(Vars.MILADY);
                sDate.setAltCalendar();
            }

        }
        return type;
    }


    private void prepareWpDays(){
        days = new ArrayList<>();
        for (int i = 1; i < sDate.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH)+1; i++) {
            days.add(i);
        }
        wp_Day.setData(days);
    }



    @Override
    public void onClick(View v) {

        this.dismiss();
        if(v.getId() == R.id.ok)
            if(this.placeID !=-1) {


                txt_outputLocation.setText(sdateStr[neededDateType]);
                txt_outputLocation.setTag(sDate);

            }
    }

    public  void SetOutputLocation(int placeID) {
       this.placeID = placeID;
    }
    public  void SetOutputLocation(int placeID,int format) {
        mOutFormat = format;
        this.placeID = placeID;
    }
    public  void SetNeededDateType(int DateType) {
        this.neededDateType = DateType;
    }


}
