package com.example.hazemnabil.islamictodo2.colection;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by hazem.nabil on 4/20/2017.
 */

public class DimensionConverter {
    private Context mContext ;
    public DisplayMetrics displayMetrics;


    public  DimensionConverter(Context mContext) {
        this.mContext = mContext;

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        this.displayMetrics = metrics;
    }

    public  DimensionConverter(Context mContext,String pxOrDp) {
        this(mContext);
        int dm = -1;
        if(pxOrDp.contains("dp") ){
            dm =Integer.parseInt( pxOrDp.replace("dp", ""));
            pxToDp(dm);

       }else if(pxOrDp.contains("px") ){
           dm =Integer.parseInt( pxOrDp.replace("px", ""));
           pxToDp(dm);
       }


    }


    public int pxToDp(int px) {
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    public int dpToPx(int dp) {

        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int pxToRatio(int px) {

        return Math.round( px*100/displayMetrics.heightPixels);
    }
    public int ratioToPx(int ratio) {

        return Math.round(displayMetrics.heightPixels *ratio/100);
    }
}
