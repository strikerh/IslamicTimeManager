package com.example.hazemnabil.islamictodo2.spinner;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.objData.Categories;

/**
 * Created by hazem.nabil on 01/03/2017.
 */


public class Spinner_adapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private String[] theArray_txt;
    private int[] theArray_id;
    private Categories allCategories;
    private Spinner mySpinner;
    private Typeface myFont;
    private int my_spinner_layout;

    public Spinner_adapter(AppCompatActivity con) {
        // TODO Auto-generated constructor stub
        mInflater = LayoutInflater.from(con);
    }
    @Deprecated
    public Spinner_adapter(AppCompatActivity con, String[] DataArray,Spinner mySpinner,Typeface myFont,int my_spinner_layout) {
        this.theArray_txt = DataArray;
        this.mySpinner = mySpinner;
        this.myFont = myFont;
        this.my_spinner_layout = my_spinner_layout;
        // TODO Auto-generated constructor stub
        mInflater = LayoutInflater.from(con);

        theArray_id = new int[DataArray.length];
        for (int i = 0; i < DataArray.length ; i++) {
            theArray_id[i] = i+1;

        }
    }

    public Spinner_adapter(AppCompatActivity con, Categories cats, Spinner mySpinner, Typeface myFont, int my_spinner_layout) {
        this.allCategories = cats;
        SparseArray<Categories.Category> spArray = cats.getCategories();
        this.mySpinner = mySpinner;
        this.myFont = myFont;
        this.my_spinner_layout = my_spinner_layout;
        // TODO Auto-generated constructor stub
        mInflater = LayoutInflater.from(con);

        theArray_id = new int[spArray.size()];
        theArray_txt = new String[spArray.size()];

        for (int i = 0; i < spArray.size() ; i++) {
            theArray_id[i] = spArray.valueAt(i)._id;
            theArray_txt[i] = spArray.valueAt(i)._name;
        }



    }

        public Spinner_adapter(AppCompatActivity con, int idOfArray,Spinner mySpinner,Typeface myFont,int my_spinner_layout) {
            this.theArray_txt = con.getResources().getStringArray(idOfArray);
            this.mySpinner = mySpinner;
            this.myFont = myFont;
            this.my_spinner_layout = my_spinner_layout;
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return theArray_txt.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return theArray_txt[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(my_spinner_layout, null);
                // holder = new ListContent();

                holder = (TextView) v.findViewById(R.id.textView1);

                v.setTag(holder);
            } else {

                holder = (TextView) v.getTag();
            }

            holder.setTypeface(myFont);
            holder.setText("" + theArray_txt[position]);
            if(theArray_id != null)
                holder.setTag(theArray_id[position]);

            return v;
        }

    }