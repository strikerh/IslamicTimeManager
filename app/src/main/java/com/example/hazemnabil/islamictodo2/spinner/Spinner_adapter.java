package com.example.hazemnabil.islamictodo2.spinner;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.R;

/**
 * Created by hazem.nabil on 01/03/2017.
 */


public class Spinner_adapter extends BaseAdapter {

        private LayoutInflater mInflater;

        private String[] theArray;
        private Spinner mySpinner;
        private Typeface myFont;
        private int my_spinner_layout;

        public Spinner_adapter(AppCompatActivity con) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);
        }
        public Spinner_adapter(AppCompatActivity con, String[] COUNTRIES,Spinner mySpinner,Typeface myFont,int my_spinner_layout) {
            this.theArray = COUNTRIES;
            this.mySpinner = mySpinner;
            this.myFont = myFont;
            this.my_spinner_layout = my_spinner_layout;
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);
        }
    public Spinner_adapter(AppCompatActivity con, int idOfArray,Spinner mySpinner,Typeface myFont,int my_spinner_layout) {
        this.theArray = con.getResources().getStringArray(idOfArray);
        this.mySpinner = mySpinner;
        this.myFont = myFont;
        this.my_spinner_layout = my_spinner_layout;
        // TODO Auto-generated constructor stub
        mInflater = LayoutInflater.from(con);
    }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return theArray.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return theArray[position];
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
            holder.setText("" + theArray[position]);

            return v;
        }

    }