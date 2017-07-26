package com.example.hazemnabil.islamictodo2.myCalender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class PrayTime {

    // ---------------------- Global Variables --------------------
    private int calcMethod; // caculation method
    private int asrJuristic; // Juristic method for Asr
    private int dhuhrMinutes; // minutes after mid-day for Dhuhr
    private int adjustHighLats; // adjusting method for higher latitudes
    private int timeFormat; // time format
    private double lat; // latitude
    private double lng; // longitude
    private double timeZone; // time-zone
    private double JDate; // Julian date
    // ------------------------------------------------------------
    // Calculation Methods
    public static final int JAFARI  = 0; // Ithna Ashari
    public static final int KARACHI = 1; // University of Islamic Sciences, KARACHI
    public static final int ISNA    = 2; // Islamic Society of North America (ISNA)
    public static final int MWL     = 3; // Muslim World League (MWL)
    public static final int MAKKAH  = 4; // Umm al-Qura, MAKKAH
    public static final int EGYPT   = 5; // Egyptian General Authority of Survey
    public static final int TEHRAN  = 6; // Institute of Geophysics, University of TEHRAN
    public static final int CUSTOM  = 7; // CUSTOM Setting
    // Juristic Methods
    public static final int SHAFII = 0; // SHAFII (standard)
    public static final int HANAFI = 1; // HANAFI
    // Adjusting Methods for Higher Latitudes
    public static final int NONE = 0; // No adjustment
    public static final int MID_NIGHT = 1; // middle of night
    public static final int ONE_SEVENTH = 2; // 1/7th of night
    public static final int ANGLE_BASED = 3; // angle/60th of night
    // Time Formats
    public static final int TIME_24 = 0; // 24-hour format
    public static final int TIME_12 = 1; // 12-hour format
    public static final int TIME_12_NS = 2; // 12-hour format with no suffix
    public static final int FLOATING = 3; // floating point number
    // Time Names
    private static final String[] TIME_NAMES = { "Fajr","Sunrise","Dhuhr","Asr","Sunset","Maghrib","Isha"};
    private String InvalidTime; // The string used for invalid times
    // --------------------- Technical Settings --------------------
    private int numIterations; // number of iterations needed to compute times
    // ------------------- Calc Method Parameters --------------------
    private HashMap<Integer, double[]> methodParams;

    private double[] prayerTimesCurrent;
    private int[] offsets;

    public PrayTime() {
        // Initialize vars

        this.setCalcMethod(0);
        this.setAsrJuristic(0);
        this.setDhuhrMinutes(0);
        this.setAdjustHighLats(1);
        this.setTimeFormat(0);

        InvalidTime = "-----"; // The string used for invalid times
// Calculation Methods
   //     this.setJafari(0); // Ithna Ashari
   //     this.setKarachi(1); // University of Islamic Sciences, KARACHI
   //     this.setISNA(2); // Islamic Society of North America (ISNA)
   //     this.setMWL(3); // Muslim World League (MWL)
   //     this.setMakkah(4); // Umm al-Qura, MAKKAH
   //     this.setEgypt(5); // Egyptian General Authority of Survey
   //     this.setTehran(6); // Institute of Geophysics, University of TEHRAN
   //     this.setCustom(7); // CUSTOM Setting

// Juristic Methods
   //     this.setShafii(0); // SHAFII (standard)
   //     this.setHanafi(1); // HANAFI

// Adjusting Methods for Higher Latitudes
   //        this.setNone(0); // No adjustment
   //        this.setMidNight(1); // middle of night
   //        this.setOneSeventh(2); // 1/7th of night
   //        this.setAngleBased(3); // angle/60th of night

// Time Formats
   //        this.setTime24(0); // 24-hour format
   //        this.setTime12(1); // 12-hour format
   //        this.setTime12NS(2); // 12-hour format with no suffix
   //        this.setFloating(3); // floating point number

// Time Names
//        TIME_NAMES = new ArrayList<String>();
//        TIME_NAMES.add("Fajr");
//        TIME_NAMES.add("Sunrise");
//        TIME_NAMES.add("Dhuhr");
//        TIME_NAMES.add("Asr");
//        TIME_NAMES.add("Sunset");
//        TIME_NAMES.add("Maghrib");
//        TIME_NAMES.add("Isha");

;
// --------------------- Technical Settings --------------------

        this.setNumIterations(1); // number of iterations needed to compute
        // times

// ------------------- Calc Method Parameters --------------------

// Tuning offsets {fajr, sunrise, dhuhr, asr, sunset, maghrib, isha}
        offsets = new int[]{0, 0, 0, 0, 0, 0, 0};



        methodParams = new HashMap<Integer, double[]>();
        //0: fajr           // set the angle for calculating Fajr
        //Maghreb:
        //1:                  if == 0 set the angle for calculating Maghrib
        //                    if == 1 set the minutes after Sunset for calculating Maghrib
        //2:                  The angle Value or minutes after Sunset Value
        //
        //Isha:
        //3:                   if == 0 set the angle for calculating Isha
        //                     if == 1 set the minutes after Maghrib for calculating Isha
        //4:                   The angle Value or minutes after Maghrib Value


// JAFARI
        double[] Jvalues = { 16, 0, 4, 0, 14 };
        methodParams.put(JAFARI, Jvalues);

// KARACHI
        double[] Kvalues = { 18, 1, 0, 0, 18 };
        methodParams.put(KARACHI, Kvalues);

// ISNA
        double[] Ivalues = { 15, 1, 0, 0, 15 };
        methodParams.put( ISNA, Ivalues);

// MWL
        double[] MWvalues = { 18, 1, 0, 0, 17 };
        methodParams.put(MWL, MWvalues);

// MAKKAH
        double[] MKvalues = { 18.5, 1, 0, 1, 90 };
        methodParams.put(MAKKAH, MKvalues);

// EGYPT
        double[] Evalues = { 19.5, 1, 0, 0, 17.5 };
        methodParams.put(EGYPT, Evalues);

// TEHRAN
        double[] Tvalues = { 17.7, 0, 4.5, 0, 14 };
        methodParams.put(TEHRAN, Tvalues);

// CUSTOM
        double[] Cvalues = { 18, 1, 0, 0, 17 };
        methodParams.put(CUSTOM, Cvalues);

    }

    // ---------------------- Trigonometric Functions -----------------------
    // range reduce angle in degrees.
    private double fixAngle(double a) {
        a = a - (360 * (Math.floor(a / 360.0)));
        a = a < 0 ? (a + 360) : a;
        return a;
    }

    // range reduce hours to 0..23
    private double fixHour(double a) {
        a = a - 24.0 * Math.floor(a / 24.0);
        a = a < 0 ? (a + 24) : a;
        return a;
    }

    // radian to degree
    private double radiansToDegrees(double alpha) {
        return ((alpha * 180.0) / Math.PI);
    }

    // deree to radian
    private double degreesToRadians(double alpha) {
        return ((alpha * Math.PI) / 180.0);
    }

    // degree sin
    private double dsin(double d) {
        return (Math.sin(degreesToRadians(d)));
    }

    // degree cos
    private double dcos(double d) {
        return (Math.cos(degreesToRadians(d)));
    }

    // degree tan
    private double dtan(double d) {
        return (Math.tan(degreesToRadians(d)));
    }

    // degree arcsin
    private double darcsin(double x) {
        double val = Math.asin(x);
        return radiansToDegrees(val);
    }

    // degree arccos
    private double darccos(double x) {
        double val = Math.acos(x);
        return radiansToDegrees(val);
    }

    // degree arctan
    private double darctan(double x) {
        double val = Math.atan(x);
        return radiansToDegrees(val);
    }

    // degree arctan2
    private double darctan2(double y, double x) {
        double val = Math.atan2(y, x);
        return radiansToDegrees(val);
    }

    // degree arccot
    private double darccot(double x) {
        double val = Math.atan2(1.0, x);
        return radiansToDegrees(val);
    }

    // ---------------------- Time-Zone Functions -----------------------
    // compute local time-zone for a specific date
    private double getTimeZone1() {
        TimeZone timez = TimeZone.getDefault();
        double hoursDiff = (timez.getRawOffset() / 1000.0) / 3600;
        return hoursDiff;
    }

    // compute base time-zone of the system
    private double getBaseTimeZone() {
        TimeZone timez = TimeZone.getDefault();
        double hoursDiff = (timez.getRawOffset() / 1000.0) / 3600;
        return hoursDiff;

    }

    // detect daylight saving in a given date
    private double detectDaylightSaving() {
        TimeZone timez = TimeZone.getDefault();
        double hoursDiff = timez.getDSTSavings();
        return hoursDiff;
    }

    // ---------------------- Julian Date Functions -----------------------
    // calculate julian date from a calendar date
    private double julianDate(int year, int month, int day) {

        if (month <= 2) {
            year -= 1;
            month += 12;
        }
        double A = Math.floor(year / 100.0);

        double B = 2 - A + Math.floor(A / 4.0);

        double JD = Math.floor(365.25 * (year + 4716))
                + Math.floor(30.6001 * (month + 1)) + day + B - 1524.5;

        return JD;
    }

    // convert a calendar date to julian date (second method)
    private double calcJD(int year, int month, int day) {
        double J1970 = 2440588.0;
        Date date = new Date(year, month - 1, day);

        double ms = date.getTime(); // # of milliseconds since midnight Jan 1,
        // 1970
        double days = Math.floor(ms / (1000.0 * 60.0 * 60.0 * 24.0));
        return J1970 + days - 0.5;

    }

    // ---------------------- Calculation Functions -----------------------
    // References:
    // http://www.ummah.net/astronomy/saltime
    // http://aa.usno.navy.mil/faq/docs/SunApprox.html
    // compute declination angle of sun and equation of time

    /*
     B= latitude of place
     L= longitude of place
     R= reference longitude (i.e. TIME BAND x 15)
     H= height above sea level in metres
     D= declination angle of sun from celestial equator (-ve in southern hemisphere)
     T= equation of time
     G= twilight angle
     */
    private double[] sunPosition(double jd) {

        double D = jd - 2451545;
        double g = fixAngle(357.529 + 0.98560028 * D);
        double q = fixAngle(280.459 + 0.98564736 * D);
        double L = fixAngle(q + (1.915 * dsin(g)) + (0.020 * dsin(2 * g)));

        // double R = 1.00014 - 0.01671 * [self dcos:g] - 0.00014 * [self dcos: (2*g)];
        double e = 23.439 - (0.00000036 * D);
        double d = darcsin(dsin(e) * dsin(L));
        double RA = (darctan2((dcos(e) * dsin(L)), (dcos(L)))) / 15.0;
        RA = fixHour(RA);
        double EqT = q / 15.0 - RA;
        double[] sPosition = new double[2];
        sPosition[0] = d;
        sPosition[1] = EqT;

        return sPosition;
    }

    // compute equation of time
    private double equationOfTime(double jd) {
        double eq = sunPosition(jd)[1];
        return eq;
    }

    // compute declination angle of sun
    private double sunDeclination(double jd) {
        double d = sunPosition(jd)[0];
        return d;
    }

    // compute mid-day (Dhuhr, Zawal) time
    private double computeMidDay(double t) {
        double T = equationOfTime(this.getJDate() + t);
        double Z = fixHour(12 - T);
        return Z;
    }

    // compute time for a given angle G
    private double computeTime(double G, double t) {

        double D = sunDeclination(this.getJDate() + t);
        double Z = computeMidDay(t);
        double Beg = -dsin(G) - dsin(D) * dsin(this.getLat());
        double Mid = dcos(D) * dcos(this.getLat());
        double V = darccos(Beg / Mid) / 15.0;

        return Z + (G > 90 ? -V : V);
    }

    // compute the time of Asr
    // SHAFII: step=1, HANAFI: step=2
    private double computeAsr(double step, double t) {
        double D = sunDeclination(this.getJDate() + t);
        double G = -darccot(step + dtan(Math.abs(this.getLat() - D)));
        return computeTime(G, t);
    }

    // ---------------------- Misc Functions -----------------------
    // compute the difference between two times
    private double timeDiff(double time1, double time2) {
        return fixHour(time2 - time1);
    }

    // -------------------- Interface Functions --------------------
    // return prayer times for a given date
    private ArrayList<String> getDatePrayerTimes(int year, int month, int day,
                                                 double latitude, double longitude, double tZone) {
        this.setLat(latitude);
        this.setLng(longitude);
        this.setTimeZone(tZone);
        this.setJDate(julianDate(year, month, day));
        double lonDiff = longitude / (15.0 * 24.0);
        this.setJDate(this.getJDate() - lonDiff);
        return computeDayTimes();
    }

    // return prayer times for a given date
    public ArrayList<String> getPrayerTimes(Calendar date, double latitude,
                                            double longitude, double tZone) {

        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DATE);

        return getDatePrayerTimes(year, month + 1, day, latitude, longitude,
                tZone);
    }

    // set custom values for calculation parameters
    private void setCustomParams(double[] params) {

        for (int i = 0; i < 5; i++) {
            if (params[i] == -1) {
                params[i] = methodParams.get(this.getCalcMethod())[i];
                methodParams.put(this.getCustom(), params);
            } else {
                methodParams.get(this.getCustom())[i] = params[i];
            }
        }
        this.setCalcMethod(this.getCustom());
    }





    // set the angle for calculating Fajr
    public void setFajrAngle(double angle) {
        double[] params = { angle, -1, -1, -1, -1 };
        setCustomParams(params);
    }

    // set the angle for calculating Maghrib
    public void setMaghribAngle(double angle) {
        double[] params = { -1, 0, angle, -1, -1 };
        setCustomParams(params);

    }

    // set the angle for calculating Isha
    public void setIshaAngle(double angle) {
        double[] params = { -1, -1, -1, 0, angle };
        setCustomParams(params);

    }

    // set the minutes after Sunset for calculating Maghrib
    public void setMaghribMinutes(double minutes) {
        double[] params = { -1, 1, minutes, -1, -1 };
        setCustomParams(params);

    }

    // set the minutes after Maghrib for calculating Isha
    public void setIshaMinutes(double minutes) {
        double[] params = { -1, -1, -1, 1, minutes };
        setCustomParams(params);

    }

    // convert double hours to 24h format
    public String floatToTime24(double time) {

        String result;

        if (Double.isNaN(time)) {
            return InvalidTime;
        }

        time = fixHour(time + 0.5 / 60.0); // add 0.5 minutes to round
        int hours = (int) Math.floor(time);
        double minutes = Math.floor((time - hours) * 60.0);

        if ((hours >= 0 && hours <= 9) && (minutes >= 0 && minutes <= 9)) {
            result = "0" + hours + ":0" + Math.round(minutes);
        } else if ((hours >= 0 && hours <= 9)) {
            result = "0" + hours + ":" + Math.round(minutes);
        } else if ((minutes >= 0 && minutes <= 9)) {
            result = hours + ":0" + Math.round(minutes);
        } else {
            result = hours + ":" + Math.round(minutes);
        }
        return result;
    }

    // convert double hours to 12h format
    public String floatToTime12(double time, boolean noSuffix) {

        if (Double.isNaN(time)) {
            return InvalidTime;
        }

        time = fixHour(time + 0.5 / 60); // add 0.5 minutes to round
        int hours = (int) Math.floor(time);
        double minutes = Math.floor((time - hours) * 60);
        String suffix, result;
        if (hours >= 12) {
            suffix = "pm";
        } else {
            suffix = "am";
        }
        hours = ((((hours + 12) - 1) % (12)) + 1);
 /*
 * hours = (hours + 12) - 1; int hrs = (int) hours % 12; hrs += 1;
 */
        if (noSuffix == false) {
            if ((hours >= 0 && hours <= 9) && (minutes >= 0 && minutes <= 9)) {
                result = "0" + hours + ":0" + Math.round(minutes) + " "
                        + suffix;
            } else if ((hours >= 0 && hours <= 9)) {
                result = "0" + hours + ":" + Math.round(minutes) + " " + suffix;
            } else if ((minutes >= 0 && minutes <= 9)) {
                result = hours + ":0" + Math.round(minutes) + " " + suffix;
            } else {
                result = hours + ":" + Math.round(minutes) + " " + suffix;
            }

        } else {
            if ((hours >= 0 && hours <= 9) && (minutes >= 0 && minutes <= 9)) {
                result = "0" + hours + ":0" + Math.round(minutes);
            } else if ((hours >= 0 && hours <= 9)) {
                result = "0" + hours + ":" + Math.round(minutes);
            } else if ((minutes >= 0 && minutes <= 9)) {
                result = hours + ":0" + Math.round(minutes);
            } else {
                result = hours + ":" + Math.round(minutes);
            }
        }
        return result;

    }

    // convert double hours to 12h format with no suffix
    public String floatToTime12NS(double time) {
        return floatToTime12(time, true);
    }

    // ---------------------- Compute Prayer Times -----------------------
    // compute prayer times at given julian date
    private double[] computeTimes(double[] times) {  //    <<<<<<<----------------------------------------------------------------------------------------------------------

        double[] t = dayPortion(times);

        double Fajr = this.computeTime( 180 - methodParams.get(this.getCalcMethod())[0], t[0]);

        double Sunrise = this.computeTime(180 - 0.833, t[1]);

        double Dhuhr = this.computeMidDay(t[2]);
        double Asr = this.computeAsr(1 + this.getAsrJuristic(), t[3]);
        double Sunset = this.computeTime(0.833, t[4]);

        double Maghrib = this.computeTime( methodParams.get(this.getCalcMethod())[2], t[5]);
        double Isha = this.computeTime( methodParams.get(this.getCalcMethod())[4], t[6]);

        double[] CTimes = { Fajr, Sunrise, Dhuhr, Asr, Sunset, Maghrib, Isha };

        return CTimes;

    }

    // compute prayer times at given julian date
    private ArrayList<String> computeDayTimes() {
        double[] times = { 5, 6, 12, 13, 18, 18, 18 }; // default times

        for (int i = 1; i <= this.getNumIterations(); i++) {
            times = computeTimes(times);
        }

        times = adjustTimes(times);
        times = tuneTimes(times);

        return adjustTimesFormat(times);
    }

    // adjust times in a prayer time array
    private double[] adjustTimes(double[] times) {
        for (int i = 0; i < times.length; i++) {
            times[i] += this.getTimeZone() - this.getLng() / 15;
        }

        times[2] += this.getDhuhrMinutes() / 60; // Dhuhr
        if (methodParams.get(this.getCalcMethod())[1] == 1) // Maghrib
        {
            times[5] = times[4] + methodParams.get(this.getCalcMethod())[2]
                    / 60;
        }
        if (methodParams.get(this.getCalcMethod())[3] == 1) // Isha
        {
            times[6] = times[5] + methodParams.get(this.getCalcMethod())[4]
                    / 60;
        }

        if (this.getAdjustHighLats() != this.getNone()) {
            times = adjustHighLatTimes(times);
        }

        return times;
    }

    // convert times array to given time format
    private ArrayList<String> adjustTimesFormat(double[] times) {

        ArrayList<String> result = new ArrayList<String>();

        if (this.getTimeFormat() == FLOATING) {
            for (double time : times) {
                result.add(String.valueOf(time));
            }
            return result;
        }

        for (int i = 0; i < 7; i++) {
            if (this.getTimeFormat() == TIME_12) {
                result.add(floatToTime12(times[i], false));
            } else if (this.getTimeFormat() == TIME_12_NS) {
                result.add(floatToTime12(times[i], true));
            } else {
                result.add(floatToTime24(times[i]));
            }
        }
        return result;
    }

    // adjust Fajr, Isha and Maghrib for locations in higher latitudes
    private double[] adjustHighLatTimes(double[] times) {
        double nightTime = timeDiff(times[4], times[1]); // sunset to sunrise

// Adjust Fajr
        double FajrDiff = nightPortion(methodParams.get(this.getCalcMethod())[0])
                * nightTime;

        if (Double.isNaN(times[0]) || timeDiff(times[0], times[1]) > FajrDiff) {
            times[0] = times[1] - FajrDiff;
        }

// Adjust Isha
        double IshaAngle = (methodParams.get(this.getCalcMethod())[3] == 0) ? methodParams
                .get(this.getCalcMethod())[4] : 18;
        double IshaDiff = this.nightPortion(IshaAngle) * nightTime;
        if (Double.isNaN(times[6])
                || this.timeDiff(times[4], times[6]) > IshaDiff) {
            times[6] = times[4] + IshaDiff;
        }

// Adjust Maghrib
        double MaghribAngle = (methodParams.get(this.getCalcMethod())[1] == 0) ? methodParams
                .get(this.getCalcMethod())[2] : 4;
        double MaghribDiff = nightPortion(MaghribAngle) * nightTime;
        if (Double.isNaN(times[5])
                || this.timeDiff(times[4], times[5]) > MaghribDiff) {
            times[5] = times[4] + MaghribDiff;
        }

        return times;
    }

    // the night portion used for adjusting times in higher latitudes
    private double nightPortion(double angle) {
        double calc = 0;

        if (adjustHighLats == ANGLE_BASED)
            calc = (angle) / 60.0;
        else if (adjustHighLats == MID_NIGHT)
            calc = 0.5;
        else if (adjustHighLats == ONE_SEVENTH)
            calc = 0.14286;

        return calc;
    }

    // convert hours to day portions
    private double[] dayPortion(double[] times) {
        for (int i = 0; i < 7; i++) {
            times[i] /= 24;
        }
        return times;
    }

    // Tune timings for adjustments
    // Set time offsets
    public void tune(int[] offsetTimes) {

        for (int i = 0; i < offsetTimes.length; i++) { // offsetTimes length
            // should be 7 in order
            // of Fajr, Sunrise, Dhuhr, Asr, Sunset, Maghrib, Isha
            this.offsets[i] = offsetTimes[i];
        }
    }

    private double[] tuneTimes(double[] times) {
        for (int i = 0; i < times.length; i++) {
            times[i] = times[i] + this.offsets[i] / 60.0;
        }

        return times;
    }




    public int getCalcMethod() {
        return calcMethod;
    }

    public void setCalcMethod(int calcMethod) {
        this.calcMethod = calcMethod;
    }

    public int getAsrJuristic() {
        return asrJuristic;
    }

    public void setAsrJuristic(int asrJuristic) {
        this.asrJuristic = asrJuristic;
    }

    public int getDhuhrMinutes() {
        return dhuhrMinutes;
    }

    public void setDhuhrMinutes(int dhuhrMinutes) {
        this.dhuhrMinutes = dhuhrMinutes;
    }

    public int getAdjustHighLats() {
        return adjustHighLats;
    }

    public void setAdjustHighLats(int adjustHighLats) {
        this.adjustHighLats = adjustHighLats;
    }

    public int getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(int timeFormat) {
        this.timeFormat = timeFormat;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(double timeZone) {
        this.timeZone = timeZone;
    }

    public double getJDate() {
        return JDate;
    }

    public void setJDate(double jDate) {
        JDate = jDate;
    }

    private int getJafari() {
        return JAFARI;
    }

//    private void setJafari(int jafari) {
//        JAFARI = jafari;
//    }

    private int getKarachi() {
        return KARACHI;
    }

//    private void setKarachi(int karachi) {
//        KARACHI = karachi;
//    }

    private int getISNA() {
        return ISNA;
    }

//    private void setISNA(int iSNA) {
//        ISNA = iSNA;
//    }

    private int getMWL() {
        return MWL;
    }

//    private void setMWL(int mWL) {
//        MWL = mWL;
//    }

    private int getMakkah() {
        return MAKKAH;
    }

//    private void setMakkah(int makkah) {
//        MAKKAH = makkah;
//    }

    private int getEgypt() {
        return EGYPT;
    }

//    private void setEgypt(int egypt) {
//        EGYPT = egypt;
//    }

    private int getCustom() {
        return CUSTOM;
    }

//    private void setCustom(int custom) {
//        CUSTOM = custom;
//    }

    private int getTehran() {
        return TEHRAN;
    }

//    private void setTehran(int tehran) {
//        TEHRAN = tehran;
//    }

    private int getShafii() {
        return SHAFII;
    }

//    private void setShafii(int shafii) {
//        SHAFII = shafii;
//    }

    private int getHanafi() {
        return HANAFI;
    }

//    private void setHanafi(int hanafi) {
//        HANAFI = hanafi;
//    }

    private int getNone() {
        return NONE;
    }

//    private void setNone(int none) {
//        NONE = none;
//    }

    private int getMidNight() {
        return MID_NIGHT;
    }

//    private void setMidNight(int midNight) {
//        MID_NIGHT = midNight;
//    }

    private int getOneSeventh() {
        return ONE_SEVENTH;
    }

//    private void setOneSeventh(int oneSeventh) {
//        ONE_SEVENTH = oneSeventh;
//    }

    private int getAngleBased() {
        return ANGLE_BASED;
    }

//    private void setAngleBased(int angleBased) {
//        ANGLE_BASED = angleBased;
//    }

    private int getTime24() {
        return TIME_24;
    }

//    private void setTime24(int time24) {
//        TIME_24 = time24;
//    }

    private int getTime12() {
        return TIME_12;
    }

//    private void setTime12(int time12) {
//        TIME_12 = time12;
//    }

    private int getTime12NS() {
        return TIME_12_NS;
    }

//    private void setTime12NS(int time12ns) {
//        TIME_12_NS = time12ns;
//    }

    private int getFloating() {
        return FLOATING;
    }

//    private void setFloating(int floating) {
//        FLOATING = floating;
//    }

    private int getNumIterations() {
        return numIterations;
    }

    private void setNumIterations(int numIterations) {
        this.numIterations = numIterations;
    }

    public String[] getTimeNames() {
        return TIME_NAMES;
    }
}