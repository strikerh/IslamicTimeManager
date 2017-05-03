package com.example.hazemnabil.islamictodo2.objData;

import android.content.Context;

import java.util.Random;



public class MoDays extends Day{

    public MoMonth parentMonth;




    public MoDays(Context mContext, MoMonth parentMonth, int dayNum, Task[] tasks) {
        super( mContext,  dayNum, parentMonth.month_n011, parentMonth.year);

        this.parentMonth = parentMonth;

    }

    /// Setter








    public Task createRandomTask() {

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
        int taskCategory = n ;
        int taskCategoryColor = tasksCatColorStrings[n];
        int taskTags = 0;
          n = rand.nextInt(3) ;
        Boolean isDone = isDoneAr[n];

        Task task = new Task( parentDay,  taskName,  taskCategory,  taskCategoryColor,  taskTags,  isDone);



        return task;
    }
}

