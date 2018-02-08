package com.example.hazemnabil.islamictodo2.viewTask;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hazemnabil.islamictodo2.R;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.example.hazemnabil.islamictodo2.myCalender.MyDate;
import com.example.hazemnabil.islamictodo2.myCalender.MyTime;
import com.example.hazemnabil.islamictodo2.objData.Task;
import com.example.hazemnabil.islamictodo2.viewTask.dummy.DummyContent;

import java.util.Calendar;

/**
 * A fragment representing a single TaskItem detail screen.
 * This fragment is either contained in a {@link TaskItemListActivity}
 * in two-pane mode (on tablets) or a {@link TaskItemDetailActivity}
 * on handsets.
 */
public class TaskItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private Task taskData;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            taskData  = new Task(this.getContext());
            int xId = ((TaskItemDetailActivity) this.getContext()).getIntent().getIntExtra("item_id",0);
            taskData.getById(xId);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

                appBarLayout.setTitle(taskData._name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.p10_taskitem_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (taskData != null) {
            ((TextView) rootView.findViewById(R.id.a_txt_description)).setText(taskData._description);
            ((TextView) rootView.findViewById(R.id.a_txt_categoty)).setText(taskData._catName);
            Calendar cal  = Calendar.getInstance();
            cal.setTimeInMillis(taskData._stime_timestamp*1000);
            MyTime mytime = new MyTime(cal);
            MyDate mydate = new MyDate(cal);

            ((TextView) rootView.findViewById(R.id.a_txt_date)).setText(mydate.getFullDate(Vars.D.MILADY));
            ((TextView) rootView.findViewById(R.id.a_txt_time)).setText(taskData._sTime_string+" "+mytime.getTime(true));
            ((TextView) rootView.findViewById(R.id.a_txt_period)).setText(taskData._duration_in_minutes);


        }

        return rootView;
    }
}
