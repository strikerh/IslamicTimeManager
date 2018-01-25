package com.example.hazemnabil.islamictodo2.colection;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by hazem.nabil on 4/20/2017.
 */

public class DimensionConverter {
    private Context mContext ;
    public DisplayMetrics displayMetrics;
    public int topHeight ;


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
    public float dpToPx_Float(int dp) {

        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int pxToRatio(int px) {

        return Math.round( px*100/(displayMetrics.heightPixels - getStatusBarHeight()-getToolBarHeight()));
    }
    public int ratioToPx(int ratio) {

        return Math.round((displayMetrics.heightPixels - getStatusBarHeight()-getToolBarHeight()) *ratio/100);
    }

    private int getNavigationHeight(){
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public int  getToolBarHeight() {
        int result = 0;

        int resourceId = mContext.getResources().getIdentifier("action_bar_size", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        if (result == 0){
            result = dpToPx(56);
        }
        return result;
    }
}
