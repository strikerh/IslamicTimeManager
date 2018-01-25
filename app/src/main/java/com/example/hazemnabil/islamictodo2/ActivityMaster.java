package com.example.hazemnabil.islamictodo2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hazemnabil.islamictodo2.calenderDay.CalenderDay;
import com.example.hazemnabil.islamictodo2.monthCalender.ActivityMonth;
import com.example.hazemnabil.islamictodo2.viewTask.TaskItemListActivity;

public class ActivityMaster extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



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
        getMenuInflater().inflate(R.menu.calender_day, menu);
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

        if (id == R.id.nav_day) {
            Intent myIntent = new Intent(this, CalenderDay.class);
            startActivity(myIntent);
        } else if (id == R.id.nav_month) {
            Intent myIntent = new Intent(this, ActivityMonth.class);
            startActivity(myIntent);

        } else if (id == R.id.nav_categories) {


        } else if (id == R.id.nav_tags) {


        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_test) {
            Intent myIntent = new Intent(this, TaskItemListActivity.class);
            startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
