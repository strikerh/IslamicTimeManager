package com.example.hazemnabil.islamictodo2.calenderMonth2_old;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazemnabil.islamictodo2.ActivityMaster;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;

public class CalenderMonth3Activity extends ActivityMaster
        implements NavigationView.OnNavigationItemSelectedListener , CalenderFragment.OnListFragmentInteractionListener {

    int currentMonth;
    int currentYear;

    MyDate myDate;
    ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        myDate = new MyDate();

        currentYear = myDate.getYear();
        currentMonth = myDate.getMonth011();

        setContentView(R.layout.activity_calender_month3);
        _activityInit();
        _prepareMothCalendar();
        _preparePagerFragment();

    }

    private void _activityInit() {


        //Change Font
       // ViewGroup gr =(ViewGroup)findViewById(R.id.drawer_layout);
        //new ChangeFonts(this,gr);


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
    private void _prepareMothCalendar() {

        //Prepare Month Bar
        TextView t1 = (TextView) findViewById(R.id.txt_month);
        TextView t2 = (TextView) findViewById(R.id.txt_altMonths);

        //gridview.setAdapter(new DaysAdapter(this,metrics,mm));

        t1.setText(myDate.getMonthName()+" "+ myDate.getYear());
        t2.setText(myDate.getAltMonths());




    }

    private void _preparePagerFragment(){
      //  Log.i(TAG, "___________________ 1.4_CreateTopWeekTab: ");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);



        mViewPager.setCurrentItem(mSectionsPagerAdapter.calculatePosOfDate(myDate.getMonth011(),myDate.getYear()));

//        WeekTabFragment frag =(WeekTabFragment)((PagerAdapter) mViewPager.getAdapter()).getItem(0);


        mViewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {// show if the Viewpager is touch down or dragged or up
                //Log.i(Vars.TAG, "_________L________ onPageScrollStateChanged: "+state);
            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //  Log.i(TAG, "________L_________ onPageScrolled: "+position);
            }

            public void onPageSelected(int position) {
                int[] monthYear = mSectionsPagerAdapter.calculateDateOfPos(position);
                currentMonth = monthYear[0];
                currentYear  = monthYear[1];
                myDate.setDate(1,currentMonth,currentYear);
                _prepareMothCalendar();

                // Check if this is the page you want.
                //Log.i(Vars.TAG, "_______L__________ onPageSelected: "+position);
                //this;
//                WeekTabFragment frag =(WeekTabFragment)((CalenderDay.PagerAdapter) mViewPager.getAdapter()).getItem(position);
//                List<Fragment> fragList = getSupportFragmentManager().getFragments();
//                for (int i = 0; i < fragList.size(); i++) {
//                    if(fragList.get(i) instanceof WeekTabFragment){
//                        WeekTabFragment wtf= (WeekTabFragment) fragList.get(i);
//                        if (wtf.tabNum == position){
//                            wtf.checkDay(smallCurrentDay);
//                        }
//                    }
//                }

            }
        });

    }


    @Override
    public void onListFragmentInteraction(int pos) {

        Toast.makeText(this, "this is pos " +pos, Toast.LENGTH_SHORT).show();
    }

    public void btn_prevMonth(View view) {
        currentMonth --;

        if(currentMonth<0){ currentMonth =11;currentYear--;}

        myDate.setDate(1,currentMonth,currentYear);

        _prepareMothCalendar();
        mViewPager.setCurrentItem(mSectionsPagerAdapter.calculatePosOfDate(myDate.getMonth011(),myDate.getYear()));

    }
    public void btn_nextMonth(View view) {
        currentMonth ++;
        if(currentMonth>11){ currentMonth =0;currentYear++;}
        myDate.setDate(1,currentMonth,currentYear);
        _prepareMothCalendar();
        mViewPager.setCurrentItem(mSectionsPagerAdapter.calculatePosOfDate(myDate.getMonth011(),myDate.getYear()));
    }




    /**
     * *************************** 1  Adapter *********************************************************************************
     */
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        int firstYear = 2000;
        int lastYear = 2030;
        int monthCount = (lastYear - firstYear)*12; // 360
        //Calendar mCal;
        int year;
        int month011;


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            //this.year = year;
            //this.month011 = month011;


        }

        @Override
        public Fragment getItem(int position) {
           // Log.i(TAG, "_____________ PagerAdapter__ getItem " +position +" :");
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            int[] monthYear = calculateDateOfPos(position);
            CalenderFragment gg = CalenderFragment.newInstance(monthYear[0], monthYear[1]);
            //gg.updateView(smDay.month011, smDay.year);

            return gg;
        }

        @Override
        public int getCount() {
            // Log.i(TAG, "_____________ PagerAdapter__ getCount: 160");
            return monthCount;
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

        @Override
        public CharSequence getPageTitle(int position) {
           // Log.i(TAG, "_____________ PagerAdapter__ getPageTitle "+position+" :");
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
