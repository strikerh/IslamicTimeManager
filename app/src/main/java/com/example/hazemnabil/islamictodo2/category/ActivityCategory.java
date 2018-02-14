package com.example.hazemnabil.islamictodo2.category;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.hazemnabil.islamictodo2.ActivityMaster;
import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.objData.Categories;

public class ActivityCategory extends ActivityMaster
        implements NavigationView.OnNavigationItemSelectedListener ,ItemFragment.OnListFragmentInteractionListener {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);





        _activityInit();
        _prepareUI();


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

    private void _prepareUI() {



    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       if (id == R.id.menu_add_new) {
           FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
           PopAddNewCategory popAddNewCategory = new PopAddNewCategory();
           ItemFragment fragment = (ItemFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
           popAddNewCategory.setFragment(fragment);
           popAddNewCategory.setMode(PopAddNewCategory.ADD_MODE);
           popAddNewCategory.show( manager,null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.category, menu);
        return true;
    }



    @Override
    public void onListFragmentInteraction(View v, Categories.Category item) {
        if(v.getId() == R.id.btn_delete){
            if(item.getTasksCount() <= 0)
            item.db_deleteMe();

        }else {
            FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
            PopAddNewCategory popAddNewCategory = new PopAddNewCategory();
            ItemFragment fragment = (ItemFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
            popAddNewCategory.setFragment(fragment);
            popAddNewCategory.setToEditMode(item);
            popAddNewCategory.show( manager,null);

        }
        ItemFragment fragment = (ItemFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.update();


    }
}
