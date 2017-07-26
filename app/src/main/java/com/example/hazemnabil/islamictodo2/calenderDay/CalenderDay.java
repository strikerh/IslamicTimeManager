package com.example.hazemnabil.islamictodo2.calenderDay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazemnabil.islamictodo2.ActivityMaster;
import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.CheckableLinearLayout;
import com.example.hazemnabil.islamictodo2.DbConnections;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.addTask.AddTask2;
import com.example.hazemnabil.islamictodo2.colection.DimensionConverter;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.SmallDate;
import com.example.hazemnabil.islamictodo2.objData.Task;

import java.util.Calendar;
import java.util.List;

public class CalenderDay extends ActivityMaster
        implements NavigationView.OnNavigationItemSelectedListener,FragmentListener,WeekTabFragment.WeekTabListener {


    private static final String TAG = Vars.TAG + "_CalenderDay";

    //for Draggable tab
    private ConstraintLayout.LayoutParams txt_notDatedParams;
    private int yDelta;
    DimensionConverter dimensionConverter;
    private TextView txt_notDated;
    private TaskHasDateFragment fragment;

    public Calendar currentDay;
    public SmallDate smallCurrentDay;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;   // The {@link ViewPager} that will host the section contents.


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "________________________ 2.onStart: ");
       // mViewPager.getCurrentItem();

        Log.i(TAG, "_________________________________________________ Activate: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "________________________ 1.onCreate: ");
        currentDay = Calendar.getInstance();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Toast.makeText(this, "يوم " +extras.getInt("day")+" "+extras.getInt("month")+" "+extras.getInt("year"), Toast.LENGTH_SHORT).show();
            currentDay.set(extras.getInt("year"),extras.getInt("month"),extras.getInt("day"));
        }

        smallCurrentDay = new SmallDate(currentDay);

        _activityInit();

        _makeDraggablePnl();

        _createTaskFrag();

        _CreateTopWeekTab();

    }

    private void _activityInit() {
        Log.i(TAG, "___________________ 1.1_activityInit: ");

        setContentView(R.layout.activity_calender_day);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DbConnections db = new DbConnections(this);

        //Change Font
        ViewGroup gr =(ViewGroup)findViewById(R.id.drawer_layout);
        ChangeFonts hh = new ChangeFonts(this,gr);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void _makeDraggablePnl() {
        Log.i(TAG, "___________________ 1.2_makeDraggablePnl: ");
        /**
         *    Make draggable pnl
         */
        ConstraintLayout _root = (ConstraintLayout) findViewById(R.id._root);
        txt_notDated = (TextView) findViewById(R.id.txt_notdated);

        //AttributeSet hh =  txt_notDated.attr
        txt_notDatedParams = (ConstraintLayout.LayoutParams) txt_notDated.getLayoutParams();
        dimensionConverter = new DimensionConverter(this);
        txt_notDatedParams.topMargin = dimensionConverter.ratioToPx(70);
        Log.i(TAG, "onCreate:dimensionConverter.displayMetrics: " + dimensionConverter.pxToDp(dimensionConverter.displayMetrics.heightPixels));

        txt_notDated.setLayoutParams(txt_notDatedParams);

        makeDraggable();
    }

    private void _createTaskFrag() {
        Log.i(TAG, "___________________ 1.3_createTaskFrag: ");
        fragment = (TaskHasDateFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        fragment.updateView(smallCurrentDay.day,smallCurrentDay.month011,smallCurrentDay.year);

        //TaskHasDateFragment fragment2 = (TaskHasDateFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
       // fragment2.updateView(smallCurrentDay.day,smallCurrentDay.month011,smallCurrentDay.year);
    }

    public void _CreateTopWeekTab(){
        Log.i(TAG, "___________________ 1.4_CreateTopWeekTab: ");


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),smallCurrentDay);
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);


        Calendar c1 = currentDay;

        int pagerPosition;

        pagerPosition = c1.get(Calendar.WEEK_OF_YEAR);
        c1.add(Calendar.YEAR,-1);
        pagerPosition += c1.getMaximum(Calendar.WEEK_OF_YEAR);
        pagerPosition -= 1;

        mViewPager.setCurrentItem(pagerPosition);

        WeekTabFragment frag =(WeekTabFragment)((SectionsPagerAdapter) mViewPager.getAdapter()).getItem(pagerPosition);


        mViewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {// show if the Viewpager is touch down or dragged or up
                Log.i(TAG, "_________L________ onPageScrollStateChanged: "+state);
            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              //  Log.i(TAG, "________L_________ onPageScrolled: "+position);
            }

            public void onPageSelected(int position) {
                // Check if this is the page you want.
                Log.i(TAG, "_______L__________ onPageSelected: "+position);
                //this;
                WeekTabFragment frag =(WeekTabFragment)((SectionsPagerAdapter) mViewPager.getAdapter()).getItem(position);
                List<Fragment> fragList = getSupportFragmentManager().getFragments();
                for (int i = 0; i < fragList.size(); i++) {
                    if(fragList.get(i) instanceof WeekTabFragment){
                        WeekTabFragment wtf= (WeekTabFragment) fragList.get(i);
                        if (wtf.tabNum == position){
                            wtf.checkDay(smallCurrentDay);
                        }
                    }
                }

            }
        });

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void makeDraggable() {
        Log.i(TAG, "______________ 1.2.1.makeDraggable: ");
        txt_notDated.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "_________________ onTouch: ");
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
    public Context getContext() {
        Log.i(TAG, "_________________ getContext: ");
        return this;
    }

    @Override
    public void onFragmentListClicked(Task item) {
        Log.i(TAG, "_________________ onFragmentListClicked: ");





    }


    @Override
    protected void onResume() {

        super.onResume();
        fragment.updateView(smallCurrentDay.day,smallCurrentDay.month011,smallCurrentDay.year);
    }

    @Override
    public void onWeekTabClicked(CheckableLinearLayout view, String test) {
        Log.i(TAG, "_________________ onWeekTabClicked: ");
        TextView tx = (TextView)view.getChildAt(1);
        TaskHasDateFragment fragment = (TaskHasDateFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        currentDay.set(view.nDay,view.nMonth011,view.nYear);
        smallCurrentDay.set(view.nDay,view.nMonth011,view.nYear);
        fragment.updateView(view.nDay,view.nMonth011,view.nYear);
        Log.i(Vars.TAG, "onClick: ++++++++++++++++++ "+tx.getText() +" "+ view.nTab +" "+ view.nDay +" "+ view.nYear);

    }

    @Override
    public void onWeekTabLoaded(WeekTabFragment frag,int tabNum) {
        Log.i(TAG, "_________________ onWeekTabLoaded: ");
        frag.checkDay(smallCurrentDay);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_get_today) {

//            myDate = new MyDate();
//            currentYear = myDate.getYear();
//            currentMonth = myDate.getMonth011();
//
//            _prepareUI_MonthBar();
//            mViewPager.setCurrentItem(mPagerAdapter.calculatePosOfDate(myDate.getMonth011(),myDate.getYear()));
            return true;


        }else if (id == R.id.menu_add_new) {
            Intent myIntent = new Intent(this,AddTask2.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * *************************** 1  Adapter *********************************************************************************
     */
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        Calendar mCal;

        public SectionsPagerAdapter(FragmentManager fm, SmallDate smDay) {
            super(fm);
            Log.i(TAG, "_____________ PagerAdapter__ PagerAdapter(Constructor): ");
            mCal = Calendar.getInstance();
            mCal.set(smDay.day,smDay.month011,smDay.year);


        }

        @Override
        public Fragment getItem(int position) {
            Log.i(TAG, "_____________ PagerAdapter__ getItem " +position +" :");
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return WeekTabFragment.newInstance(position);
        }

        @Override
        public int getCount() {
           // Log.i(TAG, "_____________ PagerAdapter__ getCount: 160");
            return 160;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.i(TAG, "_____________ PagerAdapter__ getPageTitle "+position+" :");
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "hghjghj 3";
            }
            return null;
        }
    }


}
