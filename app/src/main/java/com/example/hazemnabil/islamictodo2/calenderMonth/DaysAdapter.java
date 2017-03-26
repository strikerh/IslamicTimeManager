package com.example.hazemnabil.islamictodo2.calenderMonth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.R;

/**
 * Created by hazem.nabil on 26/03/2017.
 */

public class DaysAdapter extends BaseAdapter {

    public Context mContext;
    private LayoutInflater mInflater;
    public DaysAdapter(Context c){
        mContext = c  ;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView m1,m2;
        View v = convertView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            convertView = mInflater.inflate(R.layout.p3_fragment_day, null);
            // holder = new ListContent();


        } else {
            //imageView = (ImageView) convertView;
        }
        m1 = (TextView) convertView.findViewById(R.id.txt_day);
        m2 = (TextView) convertView.findViewById(R.id.txt_dayAlt);
        m1.setText("sffsdfsdf");
        m2.setText("sdssss");

        convertView.setTag(m1);

        return convertView;

    }
}
