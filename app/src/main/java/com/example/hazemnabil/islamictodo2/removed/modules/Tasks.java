package com.example.hazemnabil.islamictodo2.removed.modules;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hazem.nabil on 4/13/2017.
 */
@Deprecated
public class Tasks {
    public Tasks() {

    }

    public  ArrayList<Task>  getWhere(int table , String itemName) {
        return null ;
    }


    /*************************************************
     *                  OBJECTS
     *************************************************/
    public class Task{
        // fields:
        private int id;
        private String name;
        private String description;
        private boolean isDone;
        private boolean isArchived;

        private Tasks.TaskDate   taskDate;
        private Tasks.TaskTime taskTime;
        private Tasks.TaskRepeat repeat;
        private Tasks.TaskImportance importance;
        private Tasks.TaskSubTasks subTasks;
        private Tasks.TaskUser user;
        private Tasks.TaskCategory category;
        private Tasks.TaskTags tag;

        private Date creationDateTime = new Date();
        private Date lastEditDateTime = new Date();

        public Task(String name, String description) {

            this.name = name;
            this.description = description;
        }
    }


    /*************************************************
     *                Inner OBJECTS
     *************************************************/

    public class TaskTime {
        private int id;
        private boolean isTimeNameOnly;
        private int timeNameIndex;
        private Time timeFrom;
        private Time timeTo;

        public TaskTime(int id, boolean isTimeNameOnly, int timeNameIndex, Time timeFrom, Time timeTo) {
            this.id = id;
            this.isTimeNameOnly = isTimeNameOnly;
            this.timeNameIndex = timeNameIndex;
            this.timeFrom = timeFrom;
            this.timeTo = timeTo;
        }
    }

    public class TaskDate {
        private int id;
        private boolean isHejry;
        Date date ;

        public TaskDate(int id, boolean isHejry, Date date) {
            this.id = id;
            this.isHejry = isHejry;
            this.date = date;
        }
    }

    public class TaskRepeat {
        private int id;
        private int repeatType;
        private ArrayList<Short> weeklyDays ;   //0:saturday, 1:sunday, 2:Monday, .... 6:friday;
        private int count;

        public TaskRepeat(int id, int repeatType, ArrayList<Short> weeklyDays, int count) {
            this.id = id;
            this.repeatType = repeatType;
            this.weeklyDays = weeklyDays;
            this.count = count;
        }
    }

    public class TaskImportance {
        private int id;
        private int stars;

    }

    public class TaskSubTasks {
        private TaskSubTask[] TaskSubTasks;
        private TaskSubTask TaskSubTask;

        public class TaskSubTask {
            private int id;
            private String taskName;
            private boolean isDone;
            private Date dateOfDone;
            private Date createdDate;
            private int order;

            public TaskSubTask(int id, String taskName, boolean isDone, Date dateOfDone, Date createdDate) {
                this.id = id;
                this.taskName = taskName;
                this.isDone = isDone;
                this.dateOfDone = dateOfDone;
                this.createdDate = createdDate;
                this.order = id;
            }
            public void setOrder(int order){
                this.order = order;
            }

        }

    }

    public class TaskUser {
        private int id;
        private String name;

        public TaskUser(int id,String name) {
            this.id = id;
            this.name = name;
        }
    }

    public class TaskCategory {
        private int id;
        private String name;
        private String color;
        private String description;
        private String source;
    }

    public class TaskTags {
        private TaskTag TaskTag;
        private ArrayList<TaskTag> TaskTags;

        public class TaskTag {
            private int id;
            private String name;
            private String color;
            private int CategoryIndex;
            private String Description;

        }

    }
}
