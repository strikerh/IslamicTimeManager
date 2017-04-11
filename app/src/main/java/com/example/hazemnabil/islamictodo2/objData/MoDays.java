package com.example.hazemnabil.islamictodo2.objData;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class MoDays {

    public String   _dayOfWeek_s;
    public int      _dayOfWeek_n;
    public int      _day_n       ,_day_alt_n;
    public int      _month_n     ,_month_alt_n;
    public int      _year_n      ,_year_alt_n;
    public String   _month_s     ,_month_alt_s;
    public String _dayWithMonth_s,_dayWithMonth_alt_s;

    public Locale locateAr = new Locale("ar");


    public MoTask[] tasks;
    public MoMonth parentMonth;
    public Calendar  mCal;
    public Calendar  hCal;

    public MoDays(MoMonth parentMonth, int dayNum, MoTask[] tasks) {
        this.parentMonth = parentMonth;


        this.tasks = tasks;
        //TODO: any DayAlt must auto calculated
        //TODO: get the month from parentMonth

        setCalenders (parentMonth.year,parentMonth.monthNum,dayNum);
        setAllAttribute();

    }

    /// Setter

    public void setCalenders (int year,int month, int day){
        mCal = Calendar.getInstance();
        mCal.set(year,month,day);

        hCal = new UmmalquraCalendar();
        hCal.setTime(mCal.getTime());

    }
    public void setAllAttribute(){

        this._dayOfWeek_n = mCal.get(Calendar.DAY_OF_WEEK);
        this._dayOfWeek_s = mCal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT,locateAr);

        this._day_n = mCal.get(Calendar.DAY_OF_MONTH);
        this._month_n = mCal.get(Calendar.MONTH);
        this._year_n = mCal.get(Calendar.YEAR);
        this._month_s =  mCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,locateAr);
        this._dayWithMonth_s =_day_n +" "+ _month_s ;

        this._day_alt_n = hCal.get(Calendar.DAY_OF_MONTH);
        this._month_alt_n = hCal.get(Calendar.MONTH);
        this._year_alt_n = hCal.get(Calendar.YEAR);
        this._month_alt_s = hCal.getDisplayName(Calendar.MONTH,Calendar.SHORT,locateAr);
        this._dayWithMonth_alt_s =_day_alt_n +""+ _month_alt_s ;


    }


    public MoTask[] getTasks() {

        Random rand = new Random();

        int  n = rand.nextInt(10) ;

        MoTask tasks[] = new MoTask[n];

        for (int i = 0; i < n; i++) {
            tasks[i] = createRandomTask();
        }



        return tasks;
    }

    public MoTask createRandomTask() {

        String tasksNameStrings[] = new String[14];
            tasksNameStrings[0] =  "ترتيب الأفكار";
            tasksNameStrings[1] =  "تقييم الجدوى";
            tasksNameStrings[2] =  "فرصة للتعرف أكثر على السوق وعن قرب";
            tasksNameStrings[3] =  " بحث الاحتمالات الممكنة لتمويل وتنفيذ وتسويق المشروع";
            tasksNameStrings[4] =  "التخطيط ووضوح الطريق";
            tasksNameStrings[5] =  "التحقق من الجاهزية";
            tasksNameStrings[6] =  "استطلاع الصعوبات المتوقعة والاستعداد لها والاحتياط للطوارئ";
            tasksNameStrings[7] =   "تحديد المتطلبات بشكل أكثر دقة وواقعية";
            tasksNameStrings[8] =   " إظهار الجدية في العمل";
            tasksNameStrings[9] =  " تسهيل تقييم المشروع للحصول على دعم أو تمويل أو مشاركة";
            tasksNameStrings[10] =  " التقليل من احتمالية الإخفاق أو الفشل أو الخسائر";
            tasksNameStrings[11] =  " التحكم وضبط التكاليف";
            tasksNameStrings[12] =  "        التنفيذ";
            tasksNameStrings[13] =   " خطوات اعداد خطة العمل" ;
        String tasksCategoryStrings[] = new String[4];
            tasksCategoryStrings[0]="ديني";
            tasksCategoryStrings[1]="شخصي";
            tasksCategoryStrings[2]="عمل";
            tasksCategoryStrings[3]="منزل";

        int tasksCatColorStrings[] = new int[4];
            tasksCatColorStrings[0]=0xFFcc3333;
            tasksCatColorStrings[1]=0xFF1d6bbc;
            tasksCatColorStrings[2]=0xFF1dbc2c;
            tasksCatColorStrings[3]=0xFFc22cbd;
        Boolean isDoneAr[] = new Boolean[4];
        isDoneAr[0]=true;
        isDoneAr[1]=false;
        isDoneAr[2]=true;
        isDoneAr[3]=false;



        Random rand = new Random();

        int  n = rand.nextInt(13) ;


        MoDays parentDay = this;
        String taskName =tasksNameStrings[n] ;
          n = rand.nextInt(3) ;
        String taskCategory = tasksCategoryStrings[n] ;
        int taskCategoryColor = tasksCatColorStrings[n];
        String taskTags = "";
          n = rand.nextInt(3) ;
        Boolean isDone = isDoneAr[n];

        MoTask task = new MoTask( parentDay,  taskName,  taskCategory,  taskCategoryColor,  taskTags,  isDone);



        return task;
    }
}

