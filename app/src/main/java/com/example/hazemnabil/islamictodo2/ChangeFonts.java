package com.example.hazemnabil.islamictodo2;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hazem.nabil on 27/02/2017.
 */

 public class ChangeFonts {


    public ChangeFonts(Context context, View v){
        this.overrideFonts(context, v);
    }
    public void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "NotoKufiArabic-Regular.ttf"));
            }
        } catch (Exception e) {
        }
    }

}
