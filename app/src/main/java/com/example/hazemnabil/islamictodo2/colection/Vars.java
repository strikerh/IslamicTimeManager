package com.example.hazemnabil.islamictodo2.colection;

/**
 * Created by hazem.nabil on 4/27/2017.
 */

public class Vars {
    public static final String  TAG = "zoma";
    public static final int MILADY = 1;
    public static final int HIJRY = 2;

    public static class LANG {
        public static final String AR = "ar";
        public static final String EN = "en";
    }
    public static class D {
        public static final int MILADY = 1;
        public static final int HIJRY = 2;
    }
    public static class TIME {
        public static final int NULL = 0;
        public static final int SPECIFIC = 1;
        public static final int RELATED = 2;

    }
    public static class FIRST_ACTIVITY {
        public static final int MONTH_ACTIVITY = 0;
        public static final int DAY_ACTIVITY = 1;
        public static final int WEEK_ACTIVITY = 2;

    }

    public static class REPEAT {
        public static final int DAILY = 0;
        public static final int WEEKLY = 1;
        public static final int MONTHLY = 2;
        public static final int[] REPEAT_TYPE  ={DAILY,WEEKLY,MONTHLY};
    }
    public static class DATE_STATUE {
        public static final int NO_DATE_No_TIME = 0;
        public static final int HAS_DATE_HAS_TIME = 1;
        public static final int HAS_DATE_NO_TIME = 2;
        public static final int No_DATE_HAS_TIME = 2;

    }



    public static final String[] MILADY_MONTHS_AR = {"يناير","فبراير","مارس","ابريل","مايو","يونيو","يوليو","أغسطس","سبتمبر","أكتوبر","نوفمبر","ديسمبر"};
    public static final String[] MILADY_2_MONTHS_AR ={"كانون الثاني","شباط"," آذار"," نيسان","أيار","حزيران","تموز","آب","أيلول","تشرين الأول","تشرين الثاني","كانون الأول"};
    public static final String[] HIJRY_MONTHS_AR = { " محرم"," صفر","ربيع الأول","ربيع الآخر","جمادى الأولى","جمادى الآخرة"," رجب"," شعبان"," رمضان"," شوال","ذو القعدة","ذو الحجة"};
    public static final String[] HIJRY_MONTHS_SHORT_AR = { " محرم"," صفر","ربيع1","ربيع2","جمادى1","جمادى2"," رجب"," شعبان"," رمضان"," شوال","القعدة","الحجة"};

    public static final String[] MILADY_MONTHS_EN = {"يناير","فبراير","مارس","ابريل","مايو","يونيو","يوليو","أغسطس","سبتمبر","أكتوبر","نوفمبر","ديسمبر"};
    public static final String[] MILADY_2_MONTHS_EN ={"كانون الثاني","شباط"," آذار"," نيسان","أيار","حزيران","تموز","آب","أيلول","تشرين الأول","تشرين الثاني","كانون الأول"};
    public static final String[] HIJRY_MONTHS_EN = { " محرم"," صفر","ربيع الأول","ربيع الآخر","جمادى الأولى","جمادى الآخرة"," رجب"," شعبان"," رمضان"," شوال","ذو القعدة","ذو الحجة"};

    public static final String[] DAY_NAMES_AR = { "أحد","أثنين","ثلاثاء","أربعاء","خميس","جمعة","سبت"};
    public static final String[] DAY_NAMES_EN ={"Sun","Mon","Tus","Wen","Thu","Fri","Sat"};


}