package com.example.hazemnabil.islamictodo2.colection;

import android.util.Log;

/**
 * Created by hazem.nabil on 8/13/2017.
 */

public class Code {

    static long startTime;
    static long stopTime;
    static long elapsedTime;
    static String name;

    static public void start( String name1){
        name = name1;
        startTime = System.currentTimeMillis();
        //<<<
    }


    public static void stop(){
        //>>>
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        Log.i(Vars.TAG+"_Code", name +": "+ elapsedTime);
        reset();
    }
    private static void reset(){
        startTime = 0;
        stopTime = 0;
        elapsedTime = 0;
        name = "";
    }


}
