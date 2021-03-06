package com.example.hazemnabil.islamictodo2.addTask;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.DbConnections;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.StartActivity;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;
import com.example.hazemnabil.islamictodo2.objData.Categories;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hazem.nabil on 16/02/2017.
 */

public class PopAddNewCategory extends DialogFragment implements View.OnClickListener{
    View view1;




    private AppCompatActivity mContext;
    private int placeID = -1;
    private Button selected_color;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view1 =  inflater.inflate(R.layout.p2_popup_add_category, container, false);
        getDialog().setTitle("تحديد التاريخ");
        mContext = (AppCompatActivity)  getActivity();

        _init();
        _actionFooter();

         ChangeFonts.overrideFonts(mContext,view1);

        return view1;

    }
    private void _init() {

         selected_color =(Button) view1.findViewById(R.id.selected_color);




    }



    public void selectColor(View view) {
        Button v = (Button)view;
        ColorDrawable buttonColor = (ColorDrawable) v.getBackground();
        int colorId = buttonColor.getColor();
        selected_color.setBackgroundColor(colorId);
    }




    private void _actionFooter() {
        Button btnOk = (Button) view1.findViewById(R.id.ok);
        Button btn_cancel = (Button) view1.findViewById(R.id.cancel);
        //btnOk.setOnClickListener(this);
        //  btn_cancel.setOnClickListener(this);

        LinearLayout lout = (LinearLayout) view1;
        for (int i = 0; i < lout.getChildCount(); i++) {
            if (lout.getChildAt(i) instanceof LinearLayout) {
                LinearLayout ll = (LinearLayout) lout.getChildAt(i);
                for (int j = 0; j < ll.getChildCount(); j++) {
                    if (ll.getChildAt(j) instanceof Button) {
                        Log.i(Vars.TAG, "_actionFooter: btn_");
                        ll.getChildAt(j).setOnClickListener(this);


                    }
                }
            }

        }

    }




    public long insertCategory(String name,String color){

         DbConnections db = new DbConnections(mContext);

        return db.insertCategory( name, color, "" , "");
    }



    @Override
    public void onClick(View v) {
        Log.i(Vars.TAG, "onClick: btn_"+ (v instanceof Button) +"_"+v.getId() );

        if(v.getId() == R.id.ok) {
            this.dismiss();
            TextView txt_CategoryName = (TextView) view1.findViewById(R.id.txt_category_name);

            ColorDrawable buttonColor = (ColorDrawable) selected_color.getBackground();
            int colorId = buttonColor.getColor();
            String hexColor = String.format("#%06X", (0xFFFFFF & colorId));

            insertCategory(""+txt_CategoryName.getText(),hexColor);
            ((AddTask2)mContext)._prepareCategory();
        }
        if(v.getId() == R.id.cancel) {
            this.dismiss();


        }
        if(v.getId() == -1){
            Log.i(Vars.TAG, "onClick: btn_"+v.getBackground());
            selectColor(v);
        }
    }

    public  void SetOutputLocation(int placeID) {
       this.placeID = placeID;
    }






}
