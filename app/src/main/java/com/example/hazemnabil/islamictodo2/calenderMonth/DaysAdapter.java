package com.example.hazemnabil.islamictodo2.calenderMonth;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.calenderMonth.objData.MoMonth;

/**
 * Created by hazem.nabil on 26/03/2017.
 */

public class DaysAdapter extends BaseAdapter {

    public Context mContext;
    public DisplayMetrics displayMetrics;
    public MoMonth moMonth;

    private LayoutInflater mInflater;
    public DaysAdapter(Context c){
        this.mContext = c  ;
    }
    public DaysAdapter(Context c, DisplayMetrics h, MoMonth mm){
        this.mContext = c  ;
        this.displayMetrics = h;
        this.moMonth = mm;
    }

    @Override
    public int getCount() {
        return 7*6;
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
        View col;
        String[] daysName = mContext.getResources().getStringArray(R.array.daysName);
        if(position>6) {
            int dayPosition = position-6;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            col = inflater.inflate(R.layout.p3_fragment_day, parent, false);

            Log.i("hazem", "getView: "+moMonth.days[5]);


            TextView txtDay = (TextView) col.findViewById(R.id.txt_day);
            TextView txtDayAlt = (TextView) col.findViewById(R.id.txt_dayAlt);
            LinearLayout taskCont = (LinearLayout) col.findViewById(R.id.taskCont);
            taskCont.setMinimumHeight(pxToDp((displayMetrics.heightPixels - 60) / 6));

            if(dayPosition<30) {
                txtDay.setText(String.valueOf(moMonth.days[dayPosition].dayNum));
                txtDayAlt.setText(moMonth.days[dayPosition].getMonthAndDaysAlt());
            }
        }else {

            TextView dayText = new TextView(mContext);
            dayText.setText(daysName[position]);
            dayText.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            dayText.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            dayText.setBackgroundColor(Color.WHITE);
            col = dayText;

        }



        if (convertView == null) {
            // if it's not recycled, initialize some attributes
           // convertView = mInflater.inflate(R.layout.p3_fragment_day, (ViewGroup) );
            // holder = new ListContent();


        } else {
            //imageView = (ImageView) convertView;
        }


      //  convertView.setTag(m1);
        //Change Font

        Typeface myFont = Typeface.createFromAsset(mContext.getAssets(), "NotoKufiArabic-Regular.ttf");
        ChangeFonts hh = new ChangeFonts(mContext,col);

        return col;

    }
    public int pxToDp(int px) {

        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
