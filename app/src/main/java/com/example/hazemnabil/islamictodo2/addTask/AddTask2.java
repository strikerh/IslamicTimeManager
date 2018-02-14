package com.example.hazemnabil.islamictodo2.addTask;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.example.hazemnabil.islamictodo2.ActivityMaster;
import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.colection.AppOptions;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;
import com.example.hazemnabil.islamictodo2.myCalender.SmallTime;
import com.example.hazemnabil.islamictodo2.objData.Categories;
import com.example.hazemnabil.islamictodo2.objData.Task;
import com.example.hazemnabil.islamictodo2.spinner.Spinner_adapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTask2 extends ActivityMaster
        implements NavigationView.OnNavigationItemSelectedListener  {


    //////////////////////////
    public static final String TASK_ID = "task_id";
    public static final String STATE = "state";
    public static final int ADD_MODE = 0;
    public static final int EDIT_MODE = 1;

    private  int pageState = ADD_MODE ;
    private Typeface myFont;


    public String value = "";

    public SubTaskFragment f;

    public LinearLayout pnl_week;
    private Spinner_adapter myadapter;
    private  TextView txt_repeatDate;
    private ViewGroup gr;
    private TextView txt_date;
    private TextView txt_period;
    private Spinner sp_timeName;
    private WheelPicker wp_repeatCount;
    private Spinner sp_Category;
    private Spinner sp_Tag;
    private Spinner repeatType;
    private EditText txt_name;
    private EditText txt_description;
    private TextView txt_time;
    private CheckBox starImportance;

    private MyDate myCurrentDay;

    private SparseArray<Categories.Category> sp;
    // Edit
    private int taskId;
    private Task taskData;

    /////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task2);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.get("state") == null || extras.getInt("state") == ADD_MODE) {
                pageState = ADD_MODE;
                Toast.makeText(this, "يوم " + extras.getInt("day") + " " + extras.getInt("month") + " " + extras.getInt("year"), Toast.LENGTH_SHORT).show();
                myCurrentDay = new MyDate();
                myCurrentDay.setDate(extras.getInt("day"), extras.getInt("month"), extras.getInt("year"));
            }
            if(extras.getInt("state") == EDIT_MODE){
                pageState = EDIT_MODE;
                if(extras.get("task_id")!= null){

                    taskData = new Task(this);
                    taskId = extras.getInt("task_id");
                    taskData.getById(taskId);


                }else {
                    pageState = ADD_MODE;
                }

            }
        }
        _initUiConnection();
        _init();

        _prepareTimeGroup();
        _prepareRepeatGroup();
        _prepareCategory();

        _FillAllFields_ifUpdate();

    }

    private void _FillAllFields_ifUpdate() {
        if(pageState == EDIT_MODE){
            txt_name.setText(taskData._name);
            txt_description.setText(taskData._description);
            sp_Category.setSelection(sp.indexOfKey(taskData._category));

             starImportance.setChecked(taskData._importance == 1);

             txt_period.setTag(""+taskData._duration_in_minutes);
             PopDurationPiker popDurationPiker = new PopDurationPiker();
            int[] ff = popDurationPiker.minToDayHourMin(Integer.valueOf(taskData._duration_in_minutes));

             txt_period.setText(popDurationPiker.prepareDuration(ff[0],ff[1],ff[2]));

            JSONObject  dt= taskData.getDateTimeFromObj();
            if(dt!= null)
            try {
                if(dt.getInt(Task.DT_DATE_STATUE) == Task.HAS_DATE_HAS_TIME || dt.getInt(Task.DT_DATE_STATUE) == Task.HAS_DATE_NO_TIME) {
                    txt_date.setText(dt.getString(Task.DT_DATE_TXT));
                    MyDate mdate = new MyDate(dt.getInt(Task.DT_DATE_TYPE),dt.getInt(Task.DT_DAY),(dt.getInt(Task.DT_MONTH)-1),dt.getInt(Task.DT_YEAR));
                    PopDatePiker pop = new PopDatePiker();
                    pop.setOutput(mdate,txt_date);

                    if(dt.getInt(Task.DT_DATE_STATUE) == Task.HAS_DATE_HAS_TIME){
                        txt_time.setText(dt.getString(Task.DT_TIME_TXT));

                        Calendar cal = Calendar.getInstance();
                        cal.set(dt.getInt(Task.DT_YEAR),dt.getInt(Task.DT_MONTH)-1,dt.getInt(Task.DT_DAY), dt.getInt(Task.DT_HOURS_24), dt.getInt(Task.DT_MINUTES), 0);
                        SmallTime st = new SmallTime(dt.getInt(Task.DT_TIME_TYPE),cal);
                        PopTimePiker popTimePiker = new PopTimePiker();
                        popTimePiker.setOutput(st,txt_time);
                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private  void _initUiConnection(){
        gr = (ViewGroup) findViewById(R.id.a_content_add_task2);

        // name Group
        txt_name = (EditText) findViewById(R.id.a_txt_taskName);
        txt_description = (EditText) findViewById(R.id.a_txt_description);

        // Time Group

        txt_date = (TextView) findViewById(R.id.a_txt_date);
        txt_period = (TextView) findViewById(R.id.a_txt_period);
        sp_timeName = (Spinner) findViewById(R.id.a_sp_TimeName);
        txt_time = (TextView) findViewById(R.id.a_txt_time);
        starImportance = (CheckBox)findViewById(R.id.ckb_star) ;

        //repeat Group
        repeatType = (Spinner) findViewById(R.id.a_sp_RepeatType);
        wp_repeatCount = (WheelPicker)findViewById(R.id.a_num2);
        txt_repeatDate = (TextView) findViewById(R.id.a_repeatDate);
        pnl_week = (LinearLayout) findViewById(R.id.a_pnl_week);
        sp_Category = (Spinner) findViewById(R.id.a_sp_Groups);
        sp_Tag = (Spinner) findViewById(R.id.a_sp_Tags);

        if(myCurrentDay!= null) {
            txt_date.setTag(myCurrentDay);
            txt_date.setText(myCurrentDay.getFullDate(AppOptions.dateType));


        }

    }

    private void _init() {


        //--- Font ---
        // myFont = Typeface.createFromAsset(getAssets(), "NotoKufiArabic-Regular.ttf");
        ChangeFonts.overrideFonts(this, gr);

        //--- toolbar ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  toolbar.getMenu().getItem()


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }



    private void _prepareTimeGroup() {





        //--- Spinners ---


        myadapter = new Spinner_adapter(this, R.array.TimeNames, sp_timeName, myFont, R.layout.p2_my_spinner_style);
        sp_timeName.setAdapter(myadapter);

    }

    private void _prepareRepeatGroup() {



        //String[] mTestArray = getResources().getStringArray(R.array.TimeNames);
        myadapter = new Spinner_adapter(this, R.array.repeatType, repeatType, myFont, R.layout.p2_my_spinner_style);
        repeatType.setAdapter(myadapter);



        repeatType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String value = parent.getItemAtPosition(position).toString();

                if (position == 1) {
                    pnl_week.setVisibility(View.VISIBLE);
                } else {
                    pnl_week.setVisibility(View.GONE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        MyDate today = new MyDate();
        txt_repeatDate.setText(today.getShortDate());




        ArrayList<String> arrlist = new ArrayList();
        for (int i = 0; i <500 ; i++) {
            if(i==0)
                arrlist.add("لمدة عام");
            else
                arrlist.add(i+"");
        }
        wp_repeatCount.setData(arrlist);
        wp_repeatCount.setSelectedItemPosition(1);
        wp_repeatCount.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {

                // you can call or do what you want with your EditText here




                int num1 = position;

                Calendar calendar = Calendar.getInstance(); // creates a new calendar instance


                if (num1 > 0) {


                    if (repeatType.getSelectedItemId() == 0) {    //-------------- Daily
                        calendar.add(Calendar.DAY_OF_MONTH, num1);

                    } else if (repeatType.getSelectedItemId() == 1) {   //-------------- Weekly
                        calendar.add(Calendar.DAY_OF_MONTH, num1 * 7);

                    } else if (repeatType.getSelectedItemId() == 2) {   //-------------- Monthly
                        calendar.add(Calendar.MONTH, num1);
                    }


                } else {

                    calendar.add(Calendar.YEAR, 2);

                }

                MyDate mydate = new MyDate(calendar);
                //String newDateString = Do.to2Digits(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + Do.to2Digits(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
                //newDateString = ChangeFonts.convertToArabic(newDateString);

                txt_repeatDate.setText(mydate.getShortDate());
                txt_repeatDate.setTag(new MyDate(calendar));


            }
        });
    //////////////////////////////////// END  _ prepare Category Group  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    public void _prepareCategory() {


        Categories cats = new Categories(this);
        sp = cats.getCategories();

        myadapter = new Spinner_adapter(this, cats, sp_Category, myFont, R.layout.p2_my_spinner_style);
        sp_Category.setAdapter(myadapter);
        ImageButton btn_add_category = (ImageButton)findViewById(R.id.btn_add_category) ;
        btn_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
                PopAddNewCategory popAddNewCategory = new PopAddNewCategory();
                popAddNewCategory.SetOutputLocation(R.id.a_txt_date);
                //pop.SetNeededDateType(selected);
                popAddNewCategory.show( manager,null);

            }
        });




//        String[] mTags = new String[3];
//        mTags[0] = "مشتريات";
//        mTags[1] = "مشروع ا";
//        mTags[2] = "مشروع ب";



        //myadapter = new Spinner_adapter(this, mTags, sp_Tag, myFont, R.layout.p2_my_spinner_style);
        //sp_Tag.setAdapter(myadapter);


    }

    // ---- Activity Options ------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(pageState == EDIT_MODE) {
            getMenuInflater().inflate(R.menu.edit_task2, menu);
        }else {
            getMenuInflater().inflate(R.menu.add_task2, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                addToDatabase();

                break;
            case R.id.action_cancel:
                finish();
                break;
            case R.id.action_settings:

                break;

        }


        return super.onOptionsItemSelected(item);
    }

    // ---- Buttons Events --------------------------------





    public void addSubTask(View view) {
        Toast.makeText(this,"add",Toast.LENGTH_SHORT).show();
        f =(SubTaskFragment) getSupportFragmentManager().findFragmentById(R.id.a_fragment3);


        AlertDialog.Builder addSubTaskDialogBuilder = new AlertDialog.Builder(this);
        addSubTaskDialogBuilder.setTitle("إضافة مهمة فرعية");

        // Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        input.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        addSubTaskDialogBuilder.setView(input);
        // Set up the buttons
        addSubTaskDialogBuilder.setPositiveButton("إضافة", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                value = input.getText().toString();
                f.onAddSubtask(value);
            }
        });
        addSubTaskDialogBuilder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog ald = addSubTaskDialogBuilder.create();
        ald.show();

        //ald.setOnDismissListener();
        input.requestFocus();



    }
    public void removeSubTask(View view) {
        Toast.makeText(this,"remove",Toast.LENGTH_SHORT).show();
        SubTaskFragment f =(SubTaskFragment) getSupportFragmentManager().findFragmentById(R.id.a_fragment3);
        f.onRemoveSubtask(2);

    }


    // --------- END -------------- Group Switchs (Show & Hide Groups)  ----- /\


    // ---- Show POPs windows (DatePiker - TimePiker)  ----- \/
    public void openCalender(View view) {
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();




        int selected = AppOptions.dateType;

        PopDatePiker pop = new PopDatePiker();
        pop.SetOutputLocation(R.id.a_txt_date);
        //pop.SetNeededDateType(selected);
        pop.show( manager,null);




    }

    // ---- Show POPs windows (DatePiker - TimePiker)  ----- \/
    public void openDurationPiker(View view) {
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();

        TextView txt_period = (TextView) findViewById(R.id.a_txt_period) ;
        PopDurationPiker pop = new PopDurationPiker();
        pop.setDefault(Integer.valueOf((String) txt_period.getTag()));
        pop.SetOutputLocation(R.id.a_txt_period);

        pop.show( manager,null);

    }

    public void openCalender2(View view) {
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        PopDatePiker pop = new PopDatePiker();
        pop.SetOutputLocation(R.id.a_repeatDate,PopDatePiker.SHORT);
        pop.show( manager,null);


    }

    public void openTimePiker(View view) {
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        PopTimePiker pop = new PopTimePiker();
        pop.setInputDate((MyDate)txt_date.getTag());
        pop.SetOutputLocation(R.id.a_txt_time);
        pop.show( manager,null);


    }



    private void addToDatabase() {

        /////////////////////////////
        int dateType;

            dateType = AppOptions.dateType ;



        //////////////////////////////////////////



        ///////////////////////////////////////////


        Task task = new Task(this);


        if(txt_name.getText().toString()!= "")
            task.setName(txt_name.getText().toString());

        if(txt_description.getText().toString()!= "")
            task.setDescription(txt_description.getText().toString());

        int dateStatus = Task.NO_DATE_NO_TIME;
        if (txt_date.getTag() != null) {
            MyDate sDate = (MyDate) txt_date.getTag();

            if (txt_time.getTag() != null) {
                SmallTime stime = (SmallTime) txt_time.getTag();
                dateStatus = Task.HAS_DATE_HAS_TIME;

                task.setStartingTime(
                        sDate.getType(),
                        dateStatus,
                        sDate.getDay(),
                        sDate.getMonth011(),
                        sDate.getYear(),
                        Vars.TIME.SPECIFIC,
                        stime.getHour24(),
                        stime.getMinute()
                );
            } else {
                dateStatus = Task.HAS_DATE_NO_TIME;
                task.setStartingTime(
                        sDate.getType(),
                        dateStatus,
                        sDate.getDay(),
                        sDate.getMonth011(),
                        sDate.getYear(),
                        Vars.TIME.NULL,
                        0,
                        0
                );
            }
        }


        if (txt_period.getTag() != null) {
            task._duration_in_minutes = txt_period.getTag().toString();
        }



        int imp = 0;
        if(starImportance.isChecked()) imp = 1;
        task.setImportance(imp);

            task.setCategory((int)((View)sp_Category.getSelectedView().getTag()).getTag());


            task.setTags(sp_Tag.getSelectedItemPosition());

        task.setSubTasks(); //todo



        if(!task._name.isEmpty()) {
            if(pageState == ADD_MODE) {
                task.db_saveMe();
            }else {
                task.db_updateMe(taskId);
            }


            finish();
        }else {
            Toast.makeText(this, "من فضلك لا تترك المهمة فارغة", Toast.LENGTH_LONG).show();
            txt_name.requestFocus();

        }





    }



    /////////////////////////////////////////////////////////////


    //TODO: weeks color on the old version (22).
    //TODO:AddTask Move tool.
    //TODO: insert button.
    //TODO:Insert on Database.

}



