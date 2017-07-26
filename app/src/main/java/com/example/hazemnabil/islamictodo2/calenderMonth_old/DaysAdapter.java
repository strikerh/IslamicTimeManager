package com.example.hazemnabil.islamictodo2.calenderMonth_old;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.objData.MoMonth;

/**
 * Created by hazem.nabil on 26/03/2017.
 */

public class DaysAdapter extends BaseAdapter {

    private static final String TAG = "zoma";
    public Context mContext;
    public DisplayMetrics displayMetrics;
    public MoMonth moMonth;
    public  int llHeight;


    private LayoutInflater mInflater;
    public DaysAdapter(Context c){
        this.mContext = c  ;
    }
    public DaysAdapter(Context c, MoMonth mm,int llHeight) {
        this.mContext = c;
        this.moMonth = mm;
        this.llHeight = llHeight;

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        this.displayMetrics = metrics;
    }

    @Override
    public int getCount() {
        return 7*7;
    } //49

    @Override
    public Object getItem(int position) {
        return moMonth;
    }

    @Override
    public long getItemId(int position) {
        return position;
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

            /* -------  Link with the xml Views   ---------- */
            TextView txtDay = (TextView) col.findViewById(R.id.txt_day);
            TextView txtDayAlt = (TextView) col.findViewById(R.id.txt_dayAlt);
            LinearLayout taskCont = (LinearLayout) col.findViewById(R.id.taskCont);
            LinearLayout MainLayout = (LinearLayout)col.findViewById(R.id.eveyDay);
            ListView lstTasks = (ListView)col.findViewById(R.id.lst_taskCont);


            TextView dayText = new TextView(mContext);
            Log.i(TAG, "MainLayout: dayText.getLineHeight: "+dayText.getLineHeight());
            MainLayout.setMinimumHeight((llHeight - dayText.getLineHeight()-15)/6);



            if(dayPosition<moMonth.calenderDaysTable.length) {
                txtDay.setText(moMonth.getCalDayDataAt(dayPosition,"day_n"));
                txtDayAlt.setText(moMonth.getCalDayDataAt(dayPosition,"dayWithMonth_alt_s"));

                lstTasks.setAdapter(new TasksAdapter(mContext,moMonth.getMoTasksAt(dayPosition)));
                MainLayout.setTag(moMonth.getMoDay(dayPosition));

                if(moMonth.getCalDayDataAt(dayPosition,"month") != String.valueOf(moMonth.month_n112) ){
                    MainLayout.setBackgroundResource(R.drawable.bg_calendermonth_days_inactive);
                    lstTasks.setAlpha(Float.parseFloat("0.5"));

                }

            }
        }else {

            TextView dayText = new TextView(mContext);
            dayText.setText(daysName[position]);
            dayText.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            dayText.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            dayText.setBackgroundColor(Color.WHITE);
            dayText.setTextColor(Color.BLACK);
           // dayText.setHeight(80);

            dayText.setMaxLines(1);
            dayText.setTextSize(12);
            Log.i(TAG, "getView: dayText.getLineHeight: "+dayText.getLineHeight());

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

    public void fixDimension(int rowDim){

    }

    public int pxToDp(int px) {
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    public int dpToPx(int dp) {

        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
