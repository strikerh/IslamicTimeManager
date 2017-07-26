package com.example.hazemnabil.islamictodo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hazemnabil.islamictodo2.calenderDay.CalenderDay;
import com.example.hazemnabil.islamictodo2.colection.AppOptions;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.monthCalender.ActivityMonth;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_start);

        DbConnections db = new DbConnections(this);
        db.dummyData();

        Intent myIntent;
        if(AppOptions.firstActivity == Vars.FIRST_ACTIVITY.MONTH_ACTIVITY)
             myIntent = new Intent(this,ActivityMonth.class);

        else if(AppOptions.firstActivity == Vars.FIRST_ACTIVITY.DAY_ACTIVITY)
             myIntent = new Intent(this,CalenderDay.class);

//        else if(AppOptions.firstActivity == Vars.FIRST_ACTIVITY.WEEK_ACTIVITY)
//            myIntent = new Intent(this,ActivityMonth.class);

        else
            myIntent = new Intent(this,ActivityMonth.class);

        startActivity(myIntent);
        finish();
    }
}
