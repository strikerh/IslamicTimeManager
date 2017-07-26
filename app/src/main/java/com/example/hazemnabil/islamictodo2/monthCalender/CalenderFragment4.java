package com.example.hazemnabil.islamictodo2.monthCalender;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.colection.DimensionConverter;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CalenderFragment4 extends Fragment {

    //  Customize parameter argument names
    private static final String ARG_MONTH011 = "column-month011";
    private static final String ARG_YEAR = "column-year";

    // Customize parameters

    private int month011;
    private int year;
    private OnListFragmentInteractionListener mListener;
    private MoMonth2 mMonth;

    private Context mContext;

    public boolean flagIsLoaded = false;
    public boolean flagLVL1_IsLoaded = false;
    // UI Views
    View view;
    TextView[] txtDaysName;
    LinearLayout[] v_ll_dayContainer;
    TextView[] v_txt_day;
    TextView[] v_txt_dayAlt;
    LinearLayout[] v_ll_taskCont;

    LinearLayout.LayoutParams layoutParams;
    LinearLayout.LayoutParams layoutParams2;
    LinearLayout.LayoutParams layoutParams3;


    private DimensionConverter dc;

   // ArrayList<MoTask>[] mTasksByDAys;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CalenderFragment4() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CalenderFragment4 newInstance(int month011, int year) {
        CalenderFragment4 fragment = new CalenderFragment4();
        Bundle args = new Bundle();
        args.putInt(ARG_MONTH011, month011);
        args.putInt(ARG_YEAR, year);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {  //2
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            month011 = getArguments().getInt(ARG_MONTH011);
            year = getArguments().getInt(ARG_YEAR);
        }else {
            MyDate myDate = new MyDate();
            month011 = myDate.getMonth011();
            year = myDate.getYear();

        }
    }  //2

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  //3
                             Bundle savedInstanceState) {



//
//        long startTime = System.currentTimeMillis();

         view = inflater.inflate(R.layout.p6_fragment_calender_list, container, false);

//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = stopTime - startTime;
//        Log.i(Vars.TAG, "onCreateView inflater: "+ elapsedTime);


        view.setRotationY(180);
        mContext = view.getContext();

        dc = new DimensionConverter(mContext);
        mMonth = new MoMonth2(mContext,month011,year);

       // mMonth = new MoMonth2(view.getContext(),month011,year);


        _prepareUI_CalenderTable(view);
        _FillUI_CalenderTable();



        flagIsLoaded = true;

        //<<<

        return view;
    }
    int v_ll_taskCont_height;
    public  void updateLVL1(final boolean isFirstTime){
    v_ll_dayContainer[31].post(new Runnable() {
        @Override
        public void run() {

                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                v_ll_taskCont_height = v_ll_dayContainer[0].getHeight() - (v_txt_day[0].getHeight() * 2) - 1;
                layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, v_ll_taskCont_height);
                layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 4);

           ///  mTasksByDAys = mMonth.prepareTasksLists_arr();

                for (int i = 0; i < 42; i++) {
                    int thisDay = 0;
                    if (!mMonth.dayField_Holders_arr[i].isOutOfTheMonth) {
                        thisDay = mMonth.getMyDateAt(i).getDay();
                        if (isFirstTime) {
                            prepareUI_createTaskContainer(i, mMonth.getTasksListAt(thisDay, false), false);
                        }else {
                            prepareUI_createTaskContainer(i, mMonth.getTasksListAt(thisDay, true), true);
                        }


                    } else
                        if (isFirstTime)
                            prepareUI_createTaskContainer(i, null, false);


                    if (isFirstTime)
                        prepareUI_createAltTextView(i);


                }

            flagLVL1_IsLoaded = true;
        }
    });
}

    private void prepareUI_createAltTextView(int i ) {
        v_txt_dayAlt[i] = new TextView(mContext);
        v_txt_dayAlt[i].setLayoutParams(layoutParams);
        v_txt_dayAlt[i].setTextSize(8);
        Calendar cal = mMonth.dayField_Holders_arr[i].myDate.getAltCalendar();
        v_txt_dayAlt[i].setText( cal.get(Calendar.DAY_OF_MONTH)+ " "+Vars.HIJRY_MONTHS_SHORT_AR[ cal.get(Calendar.MONTH)] );
        v_txt_dayAlt[i].setTextColor(0xff000000);

        v_ll_dayContainer[i].addView(v_txt_dayAlt[i]);
    }

    private void prepareUI_createTaskContainer(int i,ArrayList<MoTask> moTasks,boolean forceUpdate) {
       boolean flag_hasMore = false;
        if(!forceUpdate) {
            v_ll_taskCont[i] = new LinearLayout(mContext);
            v_ll_taskCont[i].setLayoutParams(layoutParams2);
            v_ll_taskCont[i].setOrientation(LinearLayout.VERTICAL);
            v_ll_taskCont[i].setWeightSum(1);
            //  v_ll_taskCont[i].setBackgroundColor(0xffddddff);
            v_ll_dayContainer[i].addView(v_ll_taskCont[i]);



        }else {
           v_ll_taskCont[i].removeAllViews();
        }
        if (moTasks!=null) {
            int maxTasksCount = 0;
            Float restSpace = 0f;
            maxTasksCount = (int) (v_ll_taskCont_height / dc.dpToPx_Float(10));
            restSpace = v_ll_taskCont_height - dc.dpToPx_Float(10) * maxTasksCount;
            if (restSpace < 4) {
                maxTasksCount--;
            }


            int count = 0;
            if (moTasks.size() > maxTasksCount) {
                count = maxTasksCount;
                flag_hasMore = true;
            } else {
                count = moTasks.size();
                flag_hasMore = false;
            }


            // TaskView hh = new TaskView(mContext, mTasks.get(i).category_color, mTasks.get(i).isDone, parentViewGroup);
            if (moTasks != null) {
                for (int j = 0; j < count; j++) {
                    TaskView hh = new TaskView(mContext, moTasks.get(j).category_color, moTasks.get(j).isDone);
                    v_ll_taskCont[i].addView(hh.getTaskView());
                }
            }

            if (flag_hasMore) {
                LinearLayout v_dots = new LinearLayout(mContext);
                v_dots.setLayoutParams(layoutParams3);
                v_dots.setOrientation(LinearLayout.VERTICAL);
                v_dots.setWeightSum(1);
                v_dots.setBackgroundResource(R.drawable.dots);
                v_ll_taskCont[i].addView(v_dots);
            }

        }
    }
    private void _prepareUI_CalenderTable(View view) {
//        long startTime = System.currentTimeMillis();

        v_ll_dayContainer = new LinearLayout[42];
        v_txt_day = new TextView[42];
        v_txt_dayAlt = new TextView[42];
        v_ll_taskCont = new LinearLayout[42];



        GridLayout v_Calendar_grid = (GridLayout) view.findViewById(R.id.Calendar_grid);
        for (int i = 0; i < 42 ; i++) {
            v_ll_dayContainer[i] = (LinearLayout) v_Calendar_grid.getChildAt(i);
            v_txt_day[i] = (TextView) v_ll_dayContainer[i].getChildAt(0);
            //v_ll_taskCont[i] = (LinearLayout)v_ll_dayContainer[i].getChildAt(1);
           // v_txt_dayAlt[i] = (TextView)v_ll_dayContainer[i].getChildAt(2);

        }


//        long stopTime = System.currentTimeMillis();
//        long elapsedTime = stopTime - startTime;
//        Log.i(Vars.TAG, "_prepareUI_CalenderTable elapsedTime: "+ elapsedTime);
    }

    private void _FillUI_CalenderTable(){
        for (int i = 0; i < 42 ; i++) {
            dayHolder(i);
           // v_txt_dayAlt[i]

        }
    }
    private void dayHolder(int p){
        v_txt_day[p].setText("" + mMonth.getMyDateAt(p).getDay());
        if(mMonth.dayField_Holders_arr[p].isOutOfTheMonth){
            v_ll_dayContainer[p].setBackgroundResource(R.drawable.bg_calendermonth_days_inactive);
        }
        final int pp = p;
        v_ll_dayContainer[p].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSelectDayField(mMonth.getMyDateAt(pp));
                }
            }
        });


        //Check Today
        Calendar today = Calendar.getInstance();
        int thisDay = mMonth.dayField_Holders_arr[p].myDate.getDay();
        int thisMonth = mMonth.dayField_Holders_arr[p].myDate.getMonth011();
        int thisYear = mMonth.dayField_Holders_arr[p].myDate.getYear();
        int todayDay =     today.get(Calendar.DAY_OF_MONTH);
        int todayMonth =     today.get(Calendar.MONTH);
        int todayYear =     today.get(Calendar.YEAR);
        if(  thisDay == todayDay &&   thisMonth == todayMonth &&  thisYear == todayYear ){
            v_ll_dayContainer[p].setBackgroundResource(R.drawable.bg_calendermonth_days_today);
        }


    }

    public void updateView(int mMonth011, int mYear){
//        mMonth = new MoMonth2(mContext,mMonth011,mYear);
//        DaysRecyclerViewAdapter hghg = new DaysRecyclerViewAdapter(mMonth, mListener);
//        calenderTableRecyclerView.setAdapter(hghg);

    }

    @Override
    public void onAttach(Context context) {  //1
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }  //1


    //TODO: Please remove it or fix it


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_YEAR,year);
        outState.putInt(ARG_MONTH011,month011);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSelectDayField(MyDate date);
    }







    /*
       * Task View:
       * mean one TASK that is inside the DAY view inside the month calender
       *
       * this view has Task Category color and if it IS DONE or not.
       *
       * */
    public class TaskView {
        private Context mContext;
        private LinearLayout taskViewGroup;


        public TaskView(Context mContext, String color, boolean isDone) {
            this.mContext = mContext;

            taskViewGroup = new LinearLayout(mContext);
            taskViewGroup.setOrientation(LinearLayout.HORIZONTAL);


            // ImageView img_taskCat = createCategoryView(color, parentVG);

            LinearLayout ll_taskCat = createCategoryView2(color);
            ImageView img_isDone = createIsDoneView(isDone);


            taskViewGroup.addView(img_isDone);
            //taskViewGroup.addView(img_taskCat);
            taskViewGroup.addView(ll_taskCat);



        }


        public ImageView createCategoryView( String color, ViewGroup parentVG) {
            ImageView taskCategory = new ImageView(mContext);


            int tPadding = dc.dpToPx(2);
            int tWidth = parentVG.getWidth() / 7 - dc.dpToPx(10) - dc.dpToPx(5) - (tPadding * 2);
            int tHeight = dc.dpToPx(10) - (tPadding * 2);
            FrameLayout.LayoutParams tP = new FrameLayout.LayoutParams(tWidth, dc.dpToPx(10));



            taskCategory.setLayoutParams(tP);

            Bitmap bitmap = Bitmap.createBitmap(tWidth, tHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            // taskCategory.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            taskCategory.setImageBitmap(bitmap);


            // Rectangle

            Paint paint = new Paint();
            paint.setColor(Color.parseColor(color));
            paint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(0, 0, tWidth, tHeight, 20, 20, paint);


            taskCategory.setPadding(tPadding, tPadding, tPadding, tPadding);
            return taskCategory;
        }

        public LinearLayout createCategoryView2( String color) {
            LinearLayout taskCategory = new LinearLayout(mContext);
            LinearLayout taskCategoryIn = new LinearLayout(mContext);



            FrameLayout.LayoutParams tP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dc.dpToPx(10));
            FrameLayout.LayoutParams tP2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            taskCategory.setLayoutParams(tP);
            taskCategory.setPadding(dc.dpToPx(3),dc.dpToPx(3),dc.dpToPx(3),dc.dpToPx(3));

            taskCategoryIn.setLayoutParams(tP2);
            taskCategoryIn.setBackgroundColor(Color.parseColor(color));
            taskCategory.addView(taskCategoryIn);




            return taskCategory;
        }

        public ImageView createIsDoneView(boolean isDone) {
            ImageView img_isDone = new ImageView(mContext);


            img_isDone.setLayoutParams(new ViewGroup.LayoutParams(dc.dpToPx(10), dc.dpToPx(10)));


            if (isDone) {
                img_isDone.setImageResource(R.drawable.ic_done_black_24dp);
            } else {

            }
            return img_isDone;
        }

        public LinearLayout getTaskView() {
            return taskViewGroup;
        }
    }

}

