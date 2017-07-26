package com.example.hazemnabil.islamictodo2.monthCalender;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.ActivityMaster;
import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.addTask.AddTask2;
import com.example.hazemnabil.islamictodo2.calenderDay.CalenderDay;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;

public class ActivityMonth extends ActivityMaster
        implements NavigationView.OnNavigationItemSelectedListener , CalenderFragment4.OnListFragmentInteractionListener {

    MyDate myDate;
    int currentMonth;
    int currentYear;
    int pagePositionNow;
    int pagePositionOld;

    boolean flag_PagerState = false;
    boolean flag_PagerIsChanged = false;

    ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        myDate = new MyDate();

        currentYear = myDate.getYear();
        currentMonth = myDate.getMonth011();

        setContentView(R.layout.activity_calender_month4);





        _activityInit();
        _prepareUI_MonthBar();
        _prepareUI_WeekDaysBar();
        _preparePagerFragment();

    }

    private void _activityInit() {


        //Change Font
        ViewGroup gr =(ViewGroup)findViewById(R.id.drawer_layout);
        new ChangeFonts(this,gr);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//
//
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void _prepareUI_MonthBar() {

        //Prepare Month Bar
        TextView t1 = (TextView) findViewById(R.id.txt_month);
        TextView t2 = (TextView) findViewById(R.id.txt_altMonths);

        //gridview.setAdapter(new DaysAdapter(this,metrics,mm));

        t1.setText(myDate.getMonthName()+" "+ ChangeFonts.convertToArabic(""+myDate.getYear() ));
        t2.setText(ChangeFonts.convertToArabic(myDate.getAltMonths() ));



    }

    private void _prepareUI_WeekDaysBar(){
        //--> Prepare the Week Bar -->>

        LinearLayout dayContainer = (LinearLayout) findViewById(R.id.llo_daysCont);

       // TextView[] txtDaysName = new TextView[7];;
        for (int i = 0; i <7 ; i++) {
            ( (TextView)dayContainer.getChildAt(i)).setText( MyDate.getDayName(i) );
        }








    }   // used in onCreateView

    private void _preparePagerFragment(){
      //  Log.i(TAG, "___________________ 1.4_CreateTopWeekTab: ");

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mPagerAdapter);
        pagePositionOld = pagePositionNow;
        pagePositionNow = mPagerAdapter.calculatePosOfDate(myDate.getMonth011(),myDate.getYear());
        if(pagePositionOld == 0)  pagePositionOld = pagePositionNow;
        mViewPager.setCurrentItem(pagePositionNow);
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                ((CalenderFragment4) ((PagerAdapter) mViewPager.getAdapter()).registeredFragments.get(pagePositionNow)).updateLVL1(true);
            }
        });



//        WeekTabFragment frag =(WeekTabFragment)((PagerAdapter) mViewPager.getAdapter()).getItem(0);


        mViewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {// show if the Viewpager is touch down or dragged or up

                if(state ==2){
                    flag_PagerState = true;
                }
                if(flag_PagerState == true && state == 0) { // check if the page slide is finished
                    flag_PagerState = false;
                    if (flag_PagerIsChanged) {
                        flag_PagerIsChanged = false;
                        CalenderFragment4 cf4 = (CalenderFragment4) ((PagerAdapter) mViewPager.getAdapter()).registeredFragments.get(pagePositionNow);
                        if (!cf4.flagLVL1_IsLoaded)
                            cf4.updateLVL1(true);
                    }
                }
               // Log.i(Vars.TAG, "_________L________ onPageScrollStateChanged: "+state +" _ "+ flag_PagerState);
            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               //   Log.i(Vars.TAG, "________L_________ onPageScrolled: "+positionOffset);
            }

            public void onPageSelected(int position) {
                int[] monthYear = mPagerAdapter.calculateDateOfPos(position);
                flag_PagerIsChanged = true;
                pagePositionOld = pagePositionNow;
                pagePositionNow = position;
                currentMonth = monthYear[0];
                currentYear  = monthYear[1];
                myDate.setDate(1,currentMonth,currentYear);
                _prepareUI_MonthBar();


            }
        });

    }


    @Override
    public void onSelectDayField(MyDate date) {

        //Toast.makeText(this, "يوم " +date.getDay()+" "+date.getMonthName()+" "+date.getYear(), Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(this,CalenderDay.class);
        myIntent.putExtra("day", date.getDay());
        myIntent.putExtra("month", date.getMonth011() );
        myIntent.putExtra("year", date.getYear());
        startActivity(myIntent);

    }

    public void btn_prevMonth(View view) {
        currentMonth --;

        if(currentMonth<0){ currentMonth =11;currentYear--;}

        myDate.setDate(1,currentMonth,currentYear);

        _prepareUI_MonthBar();
        mViewPager.setCurrentItem(mPagerAdapter.calculatePosOfDate(myDate.getMonth011(),myDate.getYear()));

    }
    public void btn_nextMonth(View view) {
        currentMonth ++;
        if(currentMonth>11){ currentMonth =0;currentYear++;}
        myDate.setDate(1,currentMonth,currentYear);
        _prepareUI_MonthBar();
        mViewPager.setCurrentItem(mPagerAdapter.calculatePosOfDate(myDate.getMonth011(),myDate.getYear()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_get_today) {

            myDate = new MyDate();
            currentYear = myDate.getYear();
            currentMonth = myDate.getMonth011();

            _prepareUI_MonthBar();
            mViewPager.setCurrentItem(mPagerAdapter.calculatePosOfDate(myDate.getMonth011(),myDate.getYear()));
            return true;


        }else if (id == R.id.menu_add_new) {
            Intent myIntent = new Intent(this,AddTask2.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calender_month3, menu);
        return true;
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                ((CalenderFragment4) ((PagerAdapter) mViewPager.getAdapter()).registeredFragments.get(pagePositionNow)).updateLVL1(false);
            }
        });
    }

    /**
     * *************************** 1  Adapter *********************************************************************************
     */
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class PagerAdapter extends FragmentStatePagerAdapter {
        int firstYear = 2000;
        int lastYear = 2030;
        int monthCount = (lastYear - firstYear)*12; // 360
        public CalenderFragment4 cCalenderFragment4;
        public SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();


        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int[] monthYear = calculateDateOfPos(position);
            cCalenderFragment4 = CalenderFragment4.newInstance(monthYear[0], monthYear[1]);
            return cCalenderFragment4;
        }

        @Override
        public int getCount() {
            return monthCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
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

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }



        public int calculatePosOfDate(int month011,int year){
            return (year-firstYear)*12 +month011;
        }
        public int[] calculateDateOfPos(int pos){
            int[] date = new int[2];
            int year = pos/12;
            int month011 = pos%12;
            date[0] = month011;
            date[1] = firstYear + year;
            return date;
        }

    }

}
