package com.example.hazemnabil.islamictodo2.dummy;

import com.example.hazemnabil.islamictodo2.objData.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
@Deprecated
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 42;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }




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


       // MoDays parentDay = this;
        String taskName =tasksNameStrings[n] ;
        n = rand.nextInt(3) ;
        String taskCategory = tasksCategoryStrings[n] ;
        int taskCategoryColor = tasksCatColorStrings[n];
        String taskTags = "";
        n = rand.nextInt(3) ;
        Boolean isDone = isDoneAr[n];

       //MoTask Task = new MoTask( parentDay,  taskName,  taskCategory,  taskCategoryColor,  taskTags,  isDone);



        //return Task;
        return null;
    }





    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
