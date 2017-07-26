package com.example.hazemnabil.islamictodo2.addTask;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aigestudio.wheelpicker.WheelPicker;
import com.example.hazemnabil.islamictodo2.ActivityMaster;
import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.colection.AppOptions;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;
import com.example.hazemnabil.islamictodo2.objData.Categories;
import com.example.hazemnabil.islamictodo2.objData.Task;
import com.example.hazemnabil.islamictodo2.spinner.Spinner_adapter;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTask2 extends ActivityMaster
        implements NavigationView.OnNavigationItemSelectedListener  {


    //////////////////////////
    private Typeface myFont;
    public LinearLayout pnl_week;

    private GroupSection sTime ;

    private GroupSection sRepeat ;
    private GroupSection sImportant ;
    private GroupSection sSubtasks;

    public String value = "";


    public SubTaskFragment f;
    Spinner_adapter myadapter;


    private  TextView txt_repeatDate;
    private   EditText num;
    private LinearLayout h1;
    private LinearLayout h2;
    private LinearLayout h3;
    private ViewGroup gr;
    private RadioGroup rg_dateType;
    private RadioButton rb_milady;
    private RadioButton rb_hijry;
    private TextView txt_date;
    private Spinner sp_timeName;
    private WheelPicker wp_repeatCount;
    private Spinner sp_Important;
    private Spinner sp_Category;
    private Spinner sp_Tag;
    private Spinner repeatType;
    private EditText txt_name;
    private EditText txt_description;
    private TextView txt_time;
    private ToggleButton[] tb_weekNames = new ToggleButton[7];
    /////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task2);

        _initUiConnection();
        _init();

        _prepareMainGroups();
        _prepareTimeGroup();
        _prepareRepeatGroup();
        _prepareImportanceGroup();



        //////////////////////////////////////////





        //////////////////////////////////////////


//        TextView b = (TextView) sp_timeName.getSelectedItem();
//        b.setText("sfsfsdf");

        //  Toast.makeText(this,b.toString(),Toast.LENGTH_SHORT).show();
        //  hh.overrideFonts(this,gr);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.a_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


    }

    private  void _initUiConnection(){
        gr = (ViewGroup) findViewById(R.id.a_content_add_task2);

        // name Group
        txt_name = (EditText) findViewById(R.id.a_txt_taskName);
        txt_description = (EditText) findViewById(R.id.a_txt_description);

        // Time Group
        rg_dateType = (RadioGroup) findViewById(R.id.a_rdg_datetype);
        rb_milady = (RadioButton) findViewById(R.id.a_rd_milady);
        rb_hijry = (RadioButton) findViewById(R.id.a_rd_hijry);
        txt_date = (TextView) findViewById(R.id.a_txt_date);
        sp_timeName = (Spinner) findViewById(R.id.a_sp_TimeName);
        txt_time = (TextView) findViewById(R.id.a_txt_time);

        //repeat Group
        repeatType = (Spinner) findViewById(R.id.a_sp_RepeatType);
        wp_repeatCount = (WheelPicker)findViewById(R.id.a_num2);
        txt_repeatDate = (TextView) findViewById(R.id.a_repeatDate);
        pnl_week = (LinearLayout) findViewById(R.id.a_pnl_week);
        tb_weekNames[0] = (ToggleButton) findViewById(R.id.a_tb_0);
        tb_weekNames[1] = (ToggleButton) findViewById(R.id.a_tb_1);
        tb_weekNames[2] = (ToggleButton) findViewById(R.id.a_tb_2);
        tb_weekNames[3] = (ToggleButton) findViewById(R.id.a_tb_3);
        tb_weekNames[4] = (ToggleButton) findViewById(R.id.a_tb_4);
        tb_weekNames[5] = (ToggleButton) findViewById(R.id.a_tb_5);
        tb_weekNames[6] = (ToggleButton) findViewById(R.id.a_tb_6);


        // other group
       // sp_Important = (Spinner) findViewById(R.id.a_sp_Important);
        sp_Category = (Spinner) findViewById(R.id.a_sp_Groups);
        sp_Tag = (Spinner) findViewById(R.id.a_sp_Tags);



    }

    private void _init() {


        //--- Font ---
        // myFont = Typeface.createFromAsset(getAssets(), "NotoKufiArabic-Regular.ttf");
        ChangeFonts.overrideFonts(this, gr);

        //--- toolbar ---
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void _prepareMainGroups() {
        sTime = new GroupSection(
                R.id.a_TimeSection, R.id.a_TimeTitle, R.id.a_TimeTitleTxt, R.id.a_TimeTitleSw, R.id.a_TimeBox);

        sRepeat = new GroupSection(
                R.id.a_RepeatSection, R.id.a_RepeatTitle, R.id.a_RepeatTitleTxt, R.id.a_RepeatTitleSw, R.id.a_RepeatBox);

//        sImportant = new GroupSection(
//                R.id.a_ImportantSection, R.id.a_ImportantTitle, R.id.a_ImportantTitleTxt, R.id.a_ImportantTitleSw, R.id.a_ImportantBox);

//          sSubtasks = new GroupSection(
//                R.id.a_SubTasksSection, R.id.a_SubTasksTitle, R.id.a_SubTasksTitleTxt, null, R.id.a_SubTasksBox);


        //--- Close Groups ---
        CloseAllGroups();
    }

    private void _prepareTimeGroup() {




        if (rg_dateType.getCheckedRadioButtonId() == -1) {
            if (AppOptions.dateType == Vars.D.HIJRY)
                rb_hijry.setChecked(true);
            else
                rb_milady.setChecked(true);
        }
        rg_dateType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (txt_date.getTag() != null) {
                    MyDate sDate = (MyDate) txt_date.getTag();
                    if (checkedId == R.id.a_rd_milady)
                        txt_date.setText(sDate.getFullDate(Vars.MILADY));
                    else if (checkedId == R.id.a_rd_hijry)
                        txt_date.setText(sDate.getFullDate(Vars.HIJRY));

                }
            }
        });


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


//        num = (EditText) findViewById(R.id.a_num);
//        num.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//
//                // you can call or do what you want with your EditText here
//
//                Spinner repeatType = (Spinner) findViewById(R.id.a_sp_RepeatType);
//
//                int num1;
//                if (num.getText().length() > 0) {
//                    num1 = Integer.parseInt(num.getText().toString());
//                } else {
//                    num1 = 0;
//                }
//                if (num1 >= 0) {
//
//                    Date today = new Date();
//                    SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyy");
//                    String newDateStr = curFormater.format(today);
//                    Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
//                    calendar.setTime(today);
//
//
//                    Date NewDate = new Date();
//                    TextView hh = (TextView) findViewById(R.id.a_repeatDate);
//
//                    if (repeatType.getSelectedItemId() == 0) {    //-------------- Daily
//                        calendar.add(Calendar.DAY_OF_MONTH, num1);
//
//                    } else if (repeatType.getSelectedItemId() == 1) {   //-------------- Weekly
//                        calendar.add(Calendar.DAY_OF_MONTH, num1 * 7);
//
//                    } else if (repeatType.getSelectedItemId() == 2) {   //-------------- Monthly
//                        calendar.add(Calendar.MONTH, num1);
//                    }
//                    String newDateString = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
//
//                    try {
//                        NewDate = curFormater.parse(newDateString);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    Toast.makeText(getBaseContext(), "week: " + newDateString, Toast.LENGTH_SHORT).show();
//                    hh.setText(newDateString);
//
//                }
//
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//        });
//
//
//        num.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Toast.makeText(getBaseContext(), num.getText(), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//        num.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //  Toast.makeText(getBaseContext(),num.getText(),Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//


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
    //////////////////////////////////// END  _ prepare REPEAT Group  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void _prepareImportanceGroup() {


        //String[] mTestArray = getResources().getStringArray(R.array.TimeNames);
      //  myadapter = new Spinner_adapter(this, R.array.importantType, sp_Important, myFont, R.layout.p2_my_spinner_style);
//        sp_Important.setAdapter(myadapter);


        Categories cats = new Categories(this);
        cats.getCategories();
        String[] mTestArray = new String[3];
        mTestArray[0] = "شخصي";
        mTestArray[1] = "عمل";
        mTestArray[2] = "اسلامي";

        myadapter = new Spinner_adapter(this, cats, sp_Category, myFont, R.layout.p2_my_spinner_style);
        sp_Category.setAdapter(myadapter);



        String[] mTags = new String[3];
        mTags[0] = "مشتريات";
        mTags[1] = "مشروع ا";
        mTags[2] = "مشروع ب";

        myadapter = new Spinner_adapter(this, mTags, sp_Tag, myFont, R.layout.p2_my_spinner_style);
        sp_Tag.setAdapter(myadapter);


    }

    // ---- Activity Options ------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task2, menu);
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



    private void CloseAllGroups() {  //in OnCreate up

        h1 = (LinearLayout) findViewById(sTime.secBox_id);
        h2 = (LinearLayout) findViewById(sRepeat.secBox_id);
//        h3 = (LinearLayout) findViewById(sImportant.secBox_id);


     //   LinearLayout h4 = (LinearLayout) findViewById(sSubtasks.secBox_id);
        h1.setVisibility(View.GONE);
        h2.setVisibility(View.GONE);
    //    h3.setVisibility(View.GONE);

       // h4.setVisibility(View.GONE);

    }

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

    // ---- Group Switchs (Show & Hide Groups)  -----
    public void onSwitch_time(View view) {
        Switchat(sTime.title_switch_id,sTime.secBox_id,"ليس لها وقت محدد");
    }
    public void onSwitch_repeat(View view) {
        Switchat(sRepeat.title_switch_id,sRepeat.secBox_id,"غير مكرر");
    }
    public void onSwitch_important(View view) {
        Switchat(sImportant.title_switch_id,sImportant.secBox_id,"غير محدد");
    }
    public void onSwitch_subtasks(View view) {
        Switchat(sSubtasks.title_switch_id,sSubtasks.secBox_id,"ليس بها مهام فرعية");
    }

    private void Switchat(int swichName,int parentGroup,String txt){
        Switch sw1 = (Switch) findViewById(swichName) ;
        LinearLayout group_time = (LinearLayout) findViewById(parentGroup);

        if (sw1.isChecked()){
            group_time.setVisibility(View.VISIBLE);
            sw1.setText("");

        }else{
            group_time.setVisibility(View.GONE);
            sw1.setText(txt);
        }
    }
    // --------- END -------------- Group Switchs (Show & Hide Groups)  ----- /\


    // ---- Show POPs windows (DatePiker - TimePiker)  ----- \/
    public void openCalender(View view) {
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();




        int selected = -1;
        if(rg_dateType.getCheckedRadioButtonId() == R.id.a_rd_milady){
            selected = Vars.MILADY;
        }else if(rg_dateType.getCheckedRadioButtonId() == R.id.a_rd_hijry){
            selected = Vars.HIJRY;
        }
        PopDatePiker pop = new PopDatePiker();
        pop.SetOutputLocation(R.id.a_txt_date);
        pop.SetNeededDateType(selected);
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
        pop.SetOutputLocation(R.id.a_txt_time);
        pop.show( manager,null);


    }



    private void addToDatabase() {

        /////////////////////////////
        int dateType;
        if (rg_dateType.getCheckedRadioButtonId() == R.id.a_rd_hijry)
            dateType = Vars.D.HIJRY;
        else
            dateType = Vars.D.MILADY;



        //////////////////////////////////////////



        ///////////////////////////////////////////


        Task task = new Task(this);


        if(txt_name.getText().toString()!= "")
            task.setName(txt_name.getText().toString());

        if(txt_description.getText().toString()!= "")
            task.setDescription(txt_description.getText().toString());


            if (txt_date.getTag() != null) {
                MyDate sDate = (MyDate) txt_date.getTag();

                if (txt_time.getTag() != null) {
                    PopTimePiker.SmallTime stime = (PopTimePiker.SmallTime) txt_time.getTag();

                    task.setStartingTime(
                            dateType,
                            sDate.getDay(),
                            sDate.getMonth011(),
                            sDate.getYear(),
                            Vars.TIME.SPECIFIC,
                            stime.hour24,
                            stime.minute
                    );
                }else {
                    task.setStartingTime(
                            dateType,
                            sDate.getDay(),
                            sDate.getMonth011(),
                            sDate.getYear(),
                            Vars.TIME.NULL,
                            0,
                            0
                    );
                }
            }



        if(sRepeat.switchBtn.isChecked() ) {
            MyDate toDate = null;
            String toDateStr = "";
            if(txt_repeatDate.getTag()!=null) {
                toDate = (MyDate) txt_repeatDate.getTag();
                toDateStr =  toDate.getShortDate(Vars.MILADY, false);
            }


            task.setRepeat(
                    repeatType.getSelectedItemPosition(),
                    wp_repeatCount.getCurrentItemPosition(),
                    toDateStr,
                    "02456" //TODO: Week Selected
            );
        }
        CheckBox starImportance = (CheckBox)findViewById(R.id.ckb_star) ;
        int imp = 0;
        if(starImportance.isChecked()) imp = 1;
        task.setImportance(imp);

//        if(sImportant.switchBtn.isChecked())
//            task.setImportance(sp_Important.getSelectedItemPosition());
//
//        if(sImportant.switchBtn.isChecked())
            task.setCategory((int)((View)sp_Category.getSelectedView().getTag()).getTag());

      //  if(sImportant.switchBtn.isChecked())
            task.setTags(sp_Tag.getSelectedItemPosition());

        task.setSubTasks(); //todo



        if(!task._name.isEmpty()) {
            task.db_saveMe();
            finish();
        }else {
            Toast.makeText(this, "من فضلك لا تترك المهمة فارغة", Toast.LENGTH_LONG).show();
            txt_name.requestFocus();

        }





    }


    /////////////////////////////////////////////////////////////
class  GroupSection {

        public int mainSec_id;
        public int secTitle_id;
        public int title_txt_id;
        public int title_switch_id;
        public int title_icon_id;
        public int secBox_id;


        public Switch switchBtn;

        public String title_swich_txt;
        public String title_txt_txt;

        public GroupSection(int mainSec_id) {


        }

        public GroupSection( int mainSec_id, int secTitle_id, int title_txt_id, int title_switch_id, int secBox_id) {
            this.mainSec_id = mainSec_id;
            this.secTitle_id = secTitle_id;
            this.title_txt_id = title_txt_id;
            this.title_switch_id = title_switch_id;
            this.title_icon_id = title_icon_id;
            this.secBox_id = secBox_id;
            switchBtn = (Switch) findViewById(title_switch_id);




        }
    }

    //TODO: weeks color on the old version (22).
    //TODO:AddTask Move tool.
    //TODO: insert button.
    //TODO:Insert on Database.

}



