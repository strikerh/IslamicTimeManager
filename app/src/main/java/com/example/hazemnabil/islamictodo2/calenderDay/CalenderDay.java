package com.example.hazemnabil.islamictodo2.calenderDay;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.ActivityMaster;
import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.DbConnections;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.colection.DimensionConverter;
import com.example.hazemnabil.islamictodo2.objData.MoDays;
import com.example.hazemnabil.islamictodo2.objData.MoMonth;
import com.example.hazemnabil.islamictodo2.objData.Task;

public class CalenderDay extends ActivityMaster implements NavigationView.OnNavigationItemSelectedListener,FragmentListener {






    private static final String TAG = "zoma";
    private ConstraintLayout.LayoutParams txt_notDatedParams;
    private int xDelta;
    private int yDelta;
    DimensionConverter dimensionConverter;
    private TextView txt_notDated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_day);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DbConnections db = new DbConnections(this);

        //Change Font
        ViewGroup gr =(ViewGroup)findViewById(R.id.drawer_layout);
        ChangeFonts hh = new ChangeFonts(this,gr);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ConstraintLayout _root = (ConstraintLayout) findViewById(R.id._root);
        txt_notDated = (TextView) findViewById(R.id.txt_notdated);

        //AttributeSet hh =  txt_notDated.attr
        txt_notDatedParams = (ConstraintLayout.LayoutParams) txt_notDated.getLayoutParams();
        dimensionConverter = new DimensionConverter(this);
        txt_notDatedParams.topMargin = dimensionConverter.ratioToPx(70);
        Log.i(TAG, "onCreate:dimensionConverter.displayMetrics: " + dimensionConverter.pxToDp(dimensionConverter.displayMetrics.heightPixels));

        txt_notDated.setLayoutParams(txt_notDatedParams);

        makeDraggable();

        // txt_notDated.set

        View ff = findViewById(R.id.mainTask);
        android.app.Fragment myFragment = getFragmentManager().findFragmentById(R.id.fragment2);


        Log.i(TAG, "onCreate: " + myFragment);


    }

    private void makeDraggable() {

        txt_notDated.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                //   Log.i(TAG, "onTouch: jhksdjhfkjhsdkjfhskdjfhkshfkshdfhskdjf");

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        yDelta = Y - txt_notDatedParams.topMargin;
                        Log.i(TAG, "onTouch: ACTION_DOWN");
                        break;

                    case MotionEvent.ACTION_UP:
                        Log.i(TAG, "onTouch: ACTION_UP");
                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        Log.i(TAG, "onTouch: ACTION_POINTER_DOWN");
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        Log.i(TAG, "onTouch: ACTION_POINTER_UP");
                        break;

                    case MotionEvent.ACTION_MOVE:

                        txt_notDatedParams.topMargin = Y - yDelta;
                        txt_notDated.setLayoutParams(txt_notDatedParams);
                        Log.i(TAG, "onTouch: ACTION_MOVE: " + dimensionConverter.pxToRatio(txt_notDatedParams.topMargin));
                        break;

                }


                return true;
            }
        });

    }

    @Override
    public MoDays onChangeDay() {

        MoMonth moMonth = new MoMonth(this, 4, 2017);
        MoDays day = moMonth.getMoDay(10);
        Log.i(TAG, "onListFragmentInteraction: 777777777777777777777777");
        return day;
    }

    @Override
    public void onFragmentListClicked(Task item) {

        Log.i(TAG, "onFragmentListClicked: 33333333333333333333");
    }


}
