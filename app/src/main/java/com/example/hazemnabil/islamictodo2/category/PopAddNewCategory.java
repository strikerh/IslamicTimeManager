package com.example.hazemnabil.islamictodo2.category;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.DbConnections;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.objData.Categories;

/**
 * Created by hazem.nabil on 16/02/2017.
 */

public class PopAddNewCategory extends DialogFragment implements View.OnClickListener{
    View view1;


    public static final int ADD_MODE = 0;
    public static final int EDIT_MODE = 1;
    private int mode = ADD_MODE;
    private AppCompatActivity mContext;
    private int placeID = -1;
    private Button selected_color;
    private ItemFragment mainFragment;
    private Categories.Category categoryItem;
    private TextView txt_CategoryName;
    private Button btnOk;
    private Button btn_cancel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view1 =  inflater.inflate(R.layout.p2_popup_add_category, container, false);
        getDialog().setTitle("تحديد التاريخ");
        mContext = (AppCompatActivity)  getActivity();

        _init();
        _prepareIfEdit();
        _actionFooter();

         ChangeFonts.overrideFonts(mContext,view1);

        return view1;

    }

    private void _prepareIfEdit() {
        if(mode == EDIT_MODE){
            if(categoryItem != null){
                selected_color.setBackgroundColor(Color.parseColor(categoryItem._color));
                txt_CategoryName.setText(categoryItem._name);
                txt_CategoryName.setTag(categoryItem._id);
            }
        }
    }

    private void _init() {

         selected_color =(Button) view1.findViewById(R.id.selected_color);
         btnOk = (Button) view1.findViewById(R.id.ok);
         btn_cancel = (Button) view1.findViewById(R.id.cancel);
        txt_CategoryName = (TextView) view1.findViewById(R.id.txt_category_name);


    }



    public void selectColor(View view) {
        Button v = (Button)view;
        ColorDrawable buttonColor = (ColorDrawable) v.getBackground();
        int colorId = buttonColor.getColor();
        selected_color.setBackgroundColor(colorId);
    }





    private void _actionFooter() {

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
            if(mode == ADD_MODE) {


                ColorDrawable buttonColor = (ColorDrawable) selected_color.getBackground();
                int colorId = buttonColor.getColor();
                String hexColor = String.format("#%06X", (0xFFFFFF & colorId));

                insertCategory("" + txt_CategoryName.getText(), hexColor);
            }else if(mode == EDIT_MODE){


                ColorDrawable buttonColor = (ColorDrawable) selected_color.getBackground();
                int colorId = buttonColor.getColor();
                String hexColor = String.format("#%06X", (0xFFFFFF & colorId));

                categoryItem.updateCategory((int)txt_CategoryName.getTag(),"" + txt_CategoryName.getText(), hexColor);
            }

            mainFragment.update();

        }
        if(v.getId() == R.id.cancel) {
            this.dismiss();


        }
        if(v.getId() == -1){
            Log.i(Vars.TAG, "onClick: btn_"+v.getBackground());
            selectColor(v);
        }
    }


    public void setMode(int addOrEditMode) {
        this.mode = addOrEditMode;
    }
    public void setToEditMode(Categories.Category item) {
        this.mode = EDIT_MODE;
        this.categoryItem = item;
    }

    public void setFragment(ItemFragment fragment) {
        this.mainFragment = fragment;
    }
}
