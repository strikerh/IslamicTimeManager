package com.example.hazemnabil.islamictodo2.calenderMonth;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.calenderMonth.objData.MoMonth;

public class CalenderMonth2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Typeface myFont;

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

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        Toast.makeText(CalenderMonth2.this, "win:"+String.valueOf(height), Toast.LENGTH_LONG).show();

        MoMonth mm = new MoMonth(3,2017);
        GridView gridview = (GridView) findViewById(R.id.grd_calender);
        gridview.setAdapter(new DaysAdapter(this,metrics,mm));
        gridview2 = gridview;

        TextView t1 = (TextView)findViewById(R.id.txt_month) ;
        t1.setText(mm.getMonthName());
        TextView t2 = (TextView)findViewById(R.id.txt_altMonths) ;
        t2.setText(mm.getMonthNameAlt());


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(CalenderMonth2.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }
    GridView gridview2;
    @Override
    public void onResume(){
        super.onResume();

       // Toast.makeText(CalenderMonth2.this, "getMeasuredHeight:"+String.valueOf(gridview2.getMeasuredHeight()), Toast.LENGTH_LONG).show();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
