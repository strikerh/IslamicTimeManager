package com.example.hazemnabil.islamictodo2.addTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TaskContent {

    /**
     * An array of sample (Task) items.
     */
    public static final List<TaskItem> ITEMS = new ArrayList<TaskItem>();

    /**
     * A map of sample (Task) items, by ID.
     */
    public static final Map<String, TaskItem> ITEM_MAP = new HashMap<String, TaskItem>();

//    private static final int COUNT = 10;
//
//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createTaskItem(i));
//        }
//    }
    private static int id =0;
/*    static {
        createTaskItem("انانمتانمتانم");
        createTaskItem("انانمتانمتانم");
        createTaskItem("انانمتانمتانم");
        createTaskItem("انانمتانمتانم");
        createTaskItem("انانمتانمتانم");
        createTaskItem("انانمتانمتانم");
        createTaskItem("انانمتانمتانم");
    }*/


    public static void createTaskItem(String txt, boolean chkd  ) {
        id++;
        TaskItem item =  new TaskItem(String.valueOf(id), txt + id, chkd);

        ITEMS.add(0,item);
        ITEM_MAP.put(item.id, item);
       // return item;
    }
    public static void createTaskItem(String txt) {
          createTaskItem(txt ,false);
    }

    public static void removeTaskItem(int  id) {
        ITEMS.remove(id);
        ITEM_MAP.remove(id);
    }

    /**
     * A Task item representing a piece of content.
     */
    public static class TaskItem {
        public final String id;
        public final String content;
        public final boolean cheked;

        public TaskItem(String id, String content, boolean cheked) {
            this.id = id;
            this.content = content;
            this.cheked = cheked;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
