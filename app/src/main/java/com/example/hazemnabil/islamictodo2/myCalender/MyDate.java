package com.example.hazemnabil.islamictodo2.myCalender;

import com.example.hazemnabil.islamictodo2.ChangeFonts;
import com.example.hazemnabil.islamictodo2.colection.AppOptions;
import com.example.hazemnabil.islamictodo2.colection.Do;
import com.example.hazemnabil.islamictodo2.colection.Vars;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by hazem.nabil on 5/21/2017.
 */

public class MyDate {

   // private Context mContext;
    private Locale locateLang = new Locale(AppOptions.lang);

    private int mDateType = AppOptions.dateType;
    private int day;
    private int month011;
    private int year;

    private Calendar cal; //HIJRY or MILADY
    private Calendar altCal; //HIJRY or MILADY


    /// Constructor
    public MyDate() {
        this(AppOptions.dateType);
    }

    public MyDate(int dateType) {
        this.mDateType = dateType;
        if (mDateType == Vars.D.MILADY) {
            cal = Calendar.getInstance();
        } else if (mDateType == Vars.D.HIJRY) {
            cal = UmmalquraCalendar.getInstance();
        }
        int dayNum = cal.get(Calendar.DAY_OF_MONTH);
        int month_011 = cal.get(Calendar.MONTH);
        int  year = cal.get(Calendar.YEAR);

        setDate( dayNum,  month_011,  year);

    }


    public MyDate( int dayNum, int month_011, int year) {

        setDate( dayNum,  month_011,  year);

    }
    public MyDate( int dateType, int dayNum, int month_011, int year) {
        this.mDateType = dateType;
        setDate( dayNum,  month_011,  year);

    }

    public MyDate( Calendar cal) {

        if (cal.get(Calendar.YEAR) < 1700) this.mDateType = Vars.D.HIJRY;
        else this.mDateType = Vars.D.MILADY;

        this.setDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
    }



    /// Setter

    public void setDate(int day, int month_011, int year) {
        this.day = day;
        this.month011 = month_011;
        this.year = year;
        this.mDateType = checkHijryOrMilady(year);


        if(cal!= null){
            cal.set(year,month_011,day);
        }
        if(day<1 || day>28){
            setCalendar(day, month_011, year);
            //cal.set(year,month_011,day);

        }
    }
    public void setCalendar(int day, int month011, int year) {
        if (mDateType == Vars.D.MILADY) {
            cal = Calendar.getInstance();

        } else if (mDateType == Vars.D.HIJRY) {
            cal = UmmalquraCalendar.getInstance();
        }
        cal.set(year, month011, day);
        cal.setFirstDayOfWeek(AppOptions.firstDayOfWeek);

        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.month011 = cal.get(Calendar.MONTH);
        this.year = cal.get(Calendar.YEAR);

    }

    public void convertToAlt() {
        if (mDateType == Vars.D.MILADY) {
            mDateType = Vars.D.HIJRY;
            Calendar c = convertToHijry(year, month011, day);
            this.day = c.get(Calendar.DAY_OF_MONTH);
            this.month011 = c.get(Calendar.MONTH);
            this.year = c.get(Calendar.YEAR);
        } else {
            mDateType = Vars.D.MILADY;
            Calendar c = convertToMilady(year, month011, day);
            this.day = c.get(Calendar.DAY_OF_MONTH);
            this.month011 = c.get(Calendar.MONTH);
            this.year = c.get(Calendar.YEAR);
        }
    }

    public void convertTo(int MiladyOrHijry) {
        if (MiladyOrHijry == Vars.D.HIJRY) {
            if (mDateType != Vars.D.HIJRY) {
                mDateType = Vars.D.HIJRY;
                Calendar c = convertToHijry(year, month011, day);
                this.day = c.get(Calendar.DAY_OF_MONTH);
                this.month011 = c.get(Calendar.MONTH);
                this.year = c.get(Calendar.YEAR);
                this.cal = null;
                this.altCal = null;
            }
        }
        if (MiladyOrHijry == Vars.D.MILADY) {
            if (mDateType != Vars.D.MILADY) {
                mDateType = Vars.D.MILADY;
                Calendar c = convertToMilady(year, month011, day);
                this.day = c.get(Calendar.DAY_OF_MONTH);
                this.month011 = c.get(Calendar.MONTH);
                this.year = c.get(Calendar.YEAR);
                this.cal = null;
                this.altCal = null;
            }
        }


    }


    ///Getter

    public int getDay(){
        return day;
    }
    public int getMonth011(){
        return month011;
    }
    public int getYear(){
        return year;
    }




    public int getAltDay(){

        return getAltCalendar().get(Calendar.DAY_OF_MONTH);
    }
    public int getAltMonth011(){
        return getAltCalendar().get(Calendar.MONTH);
    }
    public int getAltYear(){
        return getAltCalendar().get(Calendar.YEAR);
    }




    public Calendar getCalendar() {
        if (cal == null) {
            setCalendar( day,  month011,  year);
        }
        return cal;
    }
    public Calendar getAltCalendar() {
        if(altCal==null){
            setAltCalendar();
        }
        return altCal;
    }

    public void setAltCalendar() {

        Calendar cal1 = getCalendar();
        if (mDateType == Vars.D.MILADY) {
            //altCal = convertToHijry(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH),cal1.get(Calendar.DAY_OF_MONTH) );
            altCal = convertToHijry(this.year, this.month011,cal1.get(Calendar.DAY_OF_MONTH) );
        }else {
            altCal = convertToMilady(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH),cal1.get(Calendar.DAY_OF_MONTH));
        }

    }

    public Data getDateData() {
        Data dateData = new Data();
        Calendar cal = getCalendar();
        dateData.nDay = cal.get(Calendar.DAY_OF_MONTH);
        dateData.nMonth011 = cal.get(Calendar.MONTH);
        dateData.nMonth112 = cal.get(Calendar.MONTH) + 1;
        dateData.nYear = cal.get(Calendar.YEAR);

        dateData.sDayOfWeek = getDayName(cal.get(Calendar.DAY_OF_WEEK));                    //cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, locateLang);
        dateData.sMonth = getMonthName(cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));     //cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, locateLang);
        dateData.sDay_Month = dateData.nDay +" "+  dateData.sMonth;
        return dateData;
    }
    public Data getAltDateData() {
        Data dateData = new Data();
        Calendar cal = getAltCalendar();
        dateData.nDay = cal.get(Calendar.DAY_OF_MONTH);
        dateData.nMonth011 = cal.get(Calendar.MONTH);
        dateData.nMonth112 = cal.get(Calendar.MONTH) + 1;
        dateData.nYear = cal.get(Calendar.YEAR);

        dateData.sDayOfWeek = getDayName(cal.get(Calendar.DAY_OF_WEEK));                    //cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, locateLang);
        dateData.sMonth = getMonthName(cal.get(Calendar.MONTH),cal.get(Calendar.YEAR));     //cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, locateLang);
        return dateData;
    }


    public int getDayOfWeekNum(){
        return getCalendar().get(Calendar.DAY_OF_WEEK);
    }

    public String getMonthName(){
        return  getMonthName(getCalendar().get(Calendar.MONTH),getCalendar().get(Calendar.YEAR));               //getCalendar().getDisplayName(Calendar.MONTH,Calendar.LONG,locateLang);
    }
    public String getAltMonths(){
        return getAltMonths(this.month011);
    }

    public String getAltMonths(int month011){
        Calendar c1 = getCalendar();
        c1.set(Calendar.DAY_OF_MONTH,1);
        Calendar altc = convertToOther(c1);
        String m1 =  getMonthName(altc.get(Calendar.MONTH),altc.get(Calendar.YEAR));       //altc.getDisplayName(Calendar.MONTH,Calendar.LONG, locateLang);
        int y1 = altc.get(Calendar.YEAR);

        c1.set(Calendar.DAY_OF_MONTH,c1.getMaximum(Calendar.DAY_OF_MONTH));
         altc = convertToOther(c1);
        String m2 = getMonthName(altc.get(Calendar.MONTH),altc.get(Calendar.YEAR));         //altc.getDisplayName(Calendar.MONTH,Calendar.LONG, locateLang);
        int y2 = altc.get(Calendar.YEAR);
        String result = "";

        if(y1==y2){
            if(m1 == m2)
                result = m1+""+y1;
            else
                result = m1+", "+m2+" "+y1;
        }else {
            result = m1+" "+y1+", "+m2+" "+y2;
        }

        return result;
    }

    public int getType(){
        return mDateType;
    }

    public Calendar getCalType(int type){
        if(getType() == type)
            return getCalendar();
        else
            return getAltCalendar();
    }

    public String getFullDate(int type){
        String fullDate;
        String dayName =  MyDate.getDayName(this.getCalType(Vars.D.MILADY).get(Calendar.DAY_OF_WEEK));


            if (type ==  getType() )
                fullDate = dayName + ", "
                        + this.getDay() + " "
                        + MyDate.getMonthName(this.getMonth011(), this.getYear()) + " "
                        + this.getYear();
            else {
                fullDate = dayName + ", "
                        + getAltDay() + " "
                        + MyDate.getMonthName(this.getAltMonth011(), this.getAltYear()) + " "
                        + this.getAltYear();
            }

        return fullDate;
    }
    public String getShortDate(int dateType, boolean withSpace){
        String ShortDate;
        String slash = "/";
        if(withSpace) slash = " / ";

        if (dateType ==  getType() ) {
            if (AppOptions.lang == Vars.LANG.AR && !withSpace) {
                ShortDate = this.getYear() + slash
                        + Do.to2Digits(this.getMonth011() + 1) + slash
                        + Do.to2Digits(this.getDay());
            } else {
                ShortDate = Do.to2Digits(this.getDay()) + slash
                        + Do.to2Digits(this.getMonth011() + 1) + slash
                        + this.getYear();
            }
        }else {
            if (AppOptions.lang == Vars.LANG.AR && !withSpace) {
                ShortDate = this.getAltYear() + "/"
                        + Do.to2Digits(this.getAltMonth011() + 1) + slash
                        + Do.to2Digits(getAltDay());

            }else {
                ShortDate = Do.to2Digits(getAltDay()) + slash
                        + Do.to2Digits( this.getAltMonth011()+1)+ slash
                        + this.getAltYear();
            }
        }
        if(AppOptions.lang == Vars.LANG.AR){
            ShortDate = ChangeFonts.convertToArabic(ShortDate);
        }

        return ShortDate;
    }
    public String getShortDate(){
        return getShortDate(getType(),true);
    }
    public String getShortDate(int type){
        return getShortDate(type,true);
    }


    //////////// Other STATIC Methods //////////////////////////////////////


    public static String getMonthMilady(int m011){
//        String[] milady = Resources.getSystem().getStringArray(R.array.miladyMonths);
        if (AppOptions.lang == Vars.LANG.AR)
            return Vars.MILADY_MONTHS_AR[m011];
        else
            return Vars.MILADY_MONTHS_EN[m011];
    }


    public static String getMonthHijry(int m011){
        if (AppOptions.lang == Vars.LANG.AR)
            return Vars.HIJRY_MONTHS_AR[m011];
        else
            return Vars.HIJRY_MONTHS_EN[m011];
    }


    public static String getMonthName(int m011,int y){
        if(y>1800)
            return getMonthMilady(m011);
        else
            return getMonthHijry(m011);
    }

    public static String getMonthNameByType(int m011,int mDateType){
        if(mDateType == Vars.MILADY)
            return getMonthMilady(m011);
        else
            return getMonthHijry(m011);
    }



    public static String getDayName(int num) {
        String result;
        int i;
//        String weekAr[] = new String[7];
//        weekAr[0] = "أحد";
//        weekAr[1] = "أثنين";
//        weekAr[2] = "ثلاثاء";
//        weekAr[3] = "أربعاء";
//        weekAr[4] = "خميس";
//        weekAr[5] = "جمعة";
//        weekAr[6] = "سبت";
//
//        String weekEn[] = new String[7];
//        weekEn[0] = "Sun";
//        weekEn[1] = "Mon";
//        weekEn[2] = "Tus";
//        weekEn[3] = "Wen";
//        weekEn[4] = "Thu";
//        weekEn[5] = "Fri";
//        weekEn[6] = "Sat";

        i = AppOptions.firstDayOfWeek + num;
        if (i > 7) i -= 7;
        if (i > 7) i -= 7;


        if (AppOptions.lang == Vars.LANG.AR)
            result = Vars.DAY_NAMES_AR[i - 1];
        else
            result = Vars.DAY_NAMES_EN[i - 1];

        return result;

    }

    public static int checkHijryOrMilady(int year){
        if (year < 1700) return Vars.D.HIJRY;
        else return Vars.D.MILADY;
    }

    ////// Converter //////////////////////////
    public static Calendar convertToHijry(int year, int month, int day) {
        GregorianCalendar gCal = new GregorianCalendar(year, month, day);
        UmmalquraCalendar uCal = new UmmalquraCalendar();
        uCal.setTime(gCal.getTime());

        return uCal;
    }

    public static Calendar convertToMilady(int year, int month, int day) {
        GregorianCalendar gCal = new GregorianCalendar();
        UmmalquraCalendar uCal = new UmmalquraCalendar(year, month, day);
        gCal.setTime(uCal.getTime());

        return gCal;
    }

    public static Calendar convertToOther(int year, int month, int day) {
        Calendar oCal;
        if (year > 1700)
            oCal = convertToHijry(year, month, day);
        else
            oCal = convertToMilady(year, month, day);

        return oCal;
    }



    public static UmmalquraCalendar convertToHijry(Calendar gCal) {

        UmmalquraCalendar uCal = new UmmalquraCalendar();
        uCal.setTime(gCal.getTime());

        return uCal;
    }

    public static Calendar convertToMilady(Calendar uCal) {
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(uCal.getTime());

        return gCal;
    }

    public static Calendar convertToOther(Calendar cal) {
        Calendar oCal;
        if (cal.get(Calendar.YEAR) > 1700)
            oCal = convertToHijry(cal);
        else
            oCal = convertToMilady(cal);

        return oCal;
    }





    public class Data {
        public int nDay;
        public int nMonth011;
        public int nMonth112;
        public int nYear;

        public String sDayOfWeek;
        public String sMonth;
        public String sDay_Month;

    }

}







