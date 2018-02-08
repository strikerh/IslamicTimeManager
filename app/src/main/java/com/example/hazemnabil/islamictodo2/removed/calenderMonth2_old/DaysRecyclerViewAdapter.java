package com.example.hazemnabil.islamictodo2.removed.calenderMonth2_old;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.monthCalender.MoMonth2;
import com.example.hazemnabil.islamictodo2.monthCalender.MoTask;
import com.example.hazemnabil.islamictodo2.colection.DimensionConverter;
import com.example.hazemnabil.islamictodo2.dummy.DummyContent.DummyItem;
import com.example.hazemnabil.islamictodo2.myCalender.SmallDate;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link CalenderFragment.OnListFragmentInteractionListener}.
 *
 * Get ready MoMonth from the CalenderFragment and just fill this DayRecycleView(ViewHolder)
 */
public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewAdapter.ViewHolder> {

    private ArrayList<MoTask>[] mTasksByDAys; // all tasks of the month categorized by the days
    private MoMonth2 mMonth;
    private final CalenderFragment.OnListFragmentInteractionListener mListener;

    public DaysRecyclerViewAdapter(MoMonth2 mMonth, CalenderFragment.OnListFragmentInteractionListener listener) {
        this.mMonth = mMonth;
        this.mListener = listener; //interface for the context

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.p5_fragment_day, parent, false);
        //   vh.add(new ViewHolder(view,parent));
        //   return vh.get(vh.size()-1);
        return new ViewHolder(view, parent);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        // position on calender Table
        if (position < mMonth.dayField_Holders_arr.length) {

            holder.prepareViews(position);
            int dayOnThisPosition = mMonth.dayField_Holders_arr[position].myDate.getDay();

            if (holder.isInMonth()) {
                   mTasksByDAys = mMonth.prepareTasksLists_arr();
                 holder.fillTasks(mTasksByDAys[dayOnThisPosition]);
            } else {
                holder.vView.setBackgroundResource(R.drawable.bg_calendermonth_days_inactive);
                // holder.vView.setAlpha(Float.parseFloat("0.5"));
            }



            if (holder.isInMonth()) {
                Calendar now = Calendar.getInstance();
                if(now.getTimeInMillis() >  mMonth.dayField_Holders_arr[position].myDate.getCalendar().getTimeInMillis() ){
                    //holder.vView.setAlpha(Float.parseFloat("0.5"));
                }

            }


            //Change the height of the ViewHolder
            ViewGroup.LayoutParams tP = holder.vView.getLayoutParams();
            tP.height = holder.parentHeight / 6;
            holder.vView.setLayoutParams(tP);

        }


        holder.vView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mMonth.dayField_Holders_arr.length;
    } //42


    /*
* ViewHolder:
* mean: every DAY VIEW  inside the month Calender
* and each one create inside it there own TASKS
*
* this view has: Day of month and AltMonth with day + list of taskView.
*
* */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View vView;
        public final TextView vDay;
        public final TextView vDayAlt;
        public LinearLayout vTaskCont;
        public Context mContext;
        public ViewGroup parentViewGroup;
        public int parentHeight;
        public boolean isInMonth;
        public int positionInTable;
        public SmallDate sDate;

        //public DummyItem mTask;
        // public ArrayList<Task> mTasks;

        public ViewHolder(View view, ViewGroup parent) {
            super(view);
            vView = view;
            mContext = view.getContext();
            vDay = (TextView) view.findViewById(R.id.txt_day);
            vDayAlt = (TextView) view.findViewById(R.id.txt_dayAlt);
            vTaskCont = (LinearLayout) view.findViewById(R.id.taskCont);
            parentViewGroup = parent;
             parentHeight = parent.getHeight();


            positionInTable = -1;


            //   ViewGroup gr = (ViewGroup) vView;
            //  new ChangeFonts(mContext, gr);

        }

        public void fillTasks(ArrayList<MoTask> mTasks) {
            // this.mTasks =mTasks;
            for (int i = 0; i < mTasks.size(); i++) {
                // createTaskView(view.getContext(),"#ffdd22",false);
                TaskView hh = new TaskView(mContext, mTasks.get(i).category_color, mTasks.get(i).isDone, parentViewGroup);
                vTaskCont.addView(hh.getTaskView());
            }
        }

        public void prepareViews(int tablePos) {
            positionInTable = tablePos;
            vDay.setText("" + mMonth.dayField_Holders_arr[tablePos].myDate.getDay());
          //todo:  vDayAlt.setText("" + mMonth.dayField_Holders_arr[tablePos].myCalender._dayWithMonth_alt_s);

            sDate = new SmallDate(mMonth.dayField_Holders_arr[tablePos].myDate.getCalendar());
            isInMonth();
        }

        public boolean isInMonth() {
            if (mMonth.dayField_Holders_arr[positionInTable].myDate.getMonth011() == mMonth.month_n011) {
                isInMonth = true;
                return true;
            } else {
                isInMonth = false;
                return false;
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + sDate.toString() + "'";
        }


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
        private DimensionConverter dc;


        public TaskView(Context mContext, String color, boolean isDone, ViewGroup parentVG) {
            this.mContext = mContext;
            dc = new DimensionConverter(mContext);
            taskViewGroup = new LinearLayout(mContext);
            taskViewGroup.setOrientation(LinearLayout.HORIZONTAL);


            // ImageView img_taskCat = createCategoryView(color, parentVG);

            LinearLayout ll_taskCat = createCategoryView2(color);
            ImageView img_isDone = createIsDoneView(isDone);


            taskViewGroup.addView(img_isDone);
            //taskViewGroup.addView(img_taskCat);
            taskViewGroup.addView(ll_taskCat);

            //vTaskCont.addView(taskViewGroup);
//<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
//            android:id="@+id/mainTask"
//            android:layout_width="match_parent"
//            android:layout_height="wrap_content"
//            android:layoutDirection="rtl"
//            android:paddingTop="1dp"
//            android:paddingBottom="1dp"
//            android:onClick="onAnyViewClicked"
//            android:tag="3"
//            android:orientation="horizontal">
//
//    <ImageView
//            android:id="@+id/img_isDone"
//            android:layout_width="15dp"
//            android:layout_height="15dp"
//
//            android:tint="@color/colorAccent"
//            android:layout_gravity="top"
//                    />
//
//    <TextView
//            android:id="@+id/txt_taskCategory"
//            android:layout_width="0dp"
//            android:layout_height="wrap_content"
//            android:layout_weight="1"
//            android:background="@drawable/bg_p3_tasklist_txt"
//            android:textSize="6sp"
//            android:padding="1dp"
//            android:maxLines="1"
//            android:gravity="center_vertical"
//            android:textAlignment="center"
//
//            android:text="Button" />
//
//
//</LinearLayout>

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



            FrameLayout.LayoutParams tP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dc.dpToPx(10));
            taskCategory.setLayoutParams(tP);
            taskCategory.setBackgroundColor(Color.parseColor(color));




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
