package com.example.hazemnabil.islamictodo2.calenderMonth_old;

import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazemnabil.islamictodo2.ActivityMaster;
import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.objData.MoDays;
import com.example.hazemnabil.islamictodo2.objData.MoMonth;

import java.util.Calendar;
import java.util.Date;
@Deprecated
public class CalenderMonth2 extends ActivityMaster
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "hazem";
    private Typeface myFont;
    private DisplayMetrics metrics = new DisplayMetrics();
    public int ll_calender_height;

    LinearLayout llCalender ;
    GridView gridview;
    TextView t1  ;
    TextView t2  ;

    int currentMonth;
    int currentYear;
    MoMonth mm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_month2);

        //Change Font
        ViewGroup gr =(ViewGroup)findViewById(R.id.content_Calender_month2);
        myFont = Typeface.createFromAsset(getAssets(), "NotoKufiArabic-Regular.ttf");
        ChangeFonts hh = new ChangeFonts(this,gr);

        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Floating Btn
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // Left Navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Start My Coding


        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        Log.i(TAG, "height DP: Screen : "+pxToDp(height,metrics));
       // Toast.makeText(CalenderMonth2.this, "win:"+String.valueOf(pxToDp(height,metrics)), Toast.LENGTH_LONG).show();

        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        currentMonth = cal.get(Calendar.MONTH)+1;
        currentYear = cal.get(Calendar.YEAR);
        createCalender1(currentMonth, currentYear);


    }







    private void createCalender1(int month, int year) {


        mm = new MoMonth(this,month, year);


        llCalender = (LinearLayout) findViewById(R.id.ll_calender);
        gridview = (GridView) findViewById(R.id.grd_calender);
        t1 = (TextView) findViewById(R.id.txt_month);
        t2 = (TextView) findViewById(R.id.txt_altMonths);

        //gridview.setAdapter(new DaysAdapter(this,metrics,mm));


        t1.setText(mm.getMonthName()+" "+ mm.year);
        t2.setText(mm.getMonthNameAlt());


        final TypedArray styledAttributes = this.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        llCalender.post(new Runnable() {
            public void run() {
                LinearLayout llCalender = (LinearLayout) findViewById(R.id.ll_calender);
                int height = llCalender.getHeight();
                ll_calender_height = llCalender.getHeight();

                Log.i(TAG, "height DP: llCalender : " + pxToDp(llCalender.getHeight(), metrics));

                gridview.setAdapter(new DaysAdapter(CalenderMonth2.this, mm, ll_calender_height));
            }
        });


        // Toast.makeText(CalenderMonth2.this,"1: "+ String.valueOf(pxToDp(llCalender.getHeight(), metrics) ),Toast.LENGTH_SHORT).show();

//now
       /* gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getBaseContext(), "pos:" + (position) + "id:" + id, Toast.LENGTH_SHORT).show();
                if (position > 6) {
                    LinearLayout hhw = (LinearLayout) v;
                    hhw.setBackgroundColor(Color.RED);
                    hhw.setOrientation(LinearLayout.VERTICAL);
                    Toast.makeText(CalenderMonth2.this, "" + (position - 6) + " - " + v.getMinimumHeight(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }



   /* public int pxToDp(int px,DisplayMetrics displayMetrics) {

        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }*/

    @Override
    public void onResume(){
        super.onResume();

       // Toast.makeText(CalenderMonth2.this, "getMeasuredHeight:"+String.valueOf(gridview.getMeasuredHeight()), Toast.LENGTH_LONG).show();
        //Toast.makeText(CalenderMonth2.this, "getHeight:"+String.valueOf(((TextView)findViewById(R.id.txt_dayAlt)).getHeight()), Toast.LENGTH_LONG).show();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calender_month2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public int pxToDp(int px,DisplayMetrics displayMetrics) {

        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void btn_nextMonth(View view) {
        currentMonth ++;
        if(currentMonth>12) {currentMonth =1;currentYear++;}
        createCalender1(currentMonth, currentYear);
    }
    public void btn_prevMonth(View view) {
        currentMonth --;
        if(currentMonth<1){ currentMonth =12;currentYear--;}
        createCalender1(currentMonth, currentYear);
    }
    public void onAnyViewClicked(View view){
        Log.i(TAG, "");
        MoDays md;
        if (view.getTag() instanceof String) {

            int nn = Integer.valueOf(view.getTag().toString());
            if (nn == 3) {
                md = (MoDays) ((LinearLayout) view.getParent().getParent().getParent()).getTag();
            } else if (nn == 2) {
                md = (MoDays) ((LinearLayout) view.getParent().getParent()).getTag();
            } else {
                md = (MoDays) ((LinearLayout) view).getTag();
            }
        }
        else {
            md = (MoDays) ((LinearLayout) view).getTag();
        }
        Log.i(TAG, md._dayWithMonth_s);
        Toast.makeText(getBaseContext(), "Just for test test test", Toast.LENGTH_SHORT).show();
    }
}
