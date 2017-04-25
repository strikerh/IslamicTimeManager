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
                ((TextView) v).setText(convertToArabic(((TextView) v).getText()));
            }
        } catch (Exception e) {
        }
    }

    public String convertToArabic(CharSequence  value)
    {
        String newValue =   (((((((((((value+"").replaceAll("1", "١")).replaceAll("2", "٢")).replaceAll("3", "٣")).replaceAll("4", "٤")).replaceAll("5", "٥")).replaceAll("6", "٦")).replaceAll("7", "٧")).replaceAll("8", "٨")).replaceAll("9", "٩")).replaceAll("0", "٠"));
        return newValue;
    }
}
