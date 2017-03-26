package com.example.hazemnabil.islamictodo2.calenderMonth;

/**
 * Created by hazem.nabil on 26/03/2017.
 */

public class Objects {
    public class MoTask {
        public String taskName;
        public String taskCategory;
        public String taskCategoryColor;
        public String taskTags;
        public Boolean isDone;
        public MoDays parentDay;

        /// Constructor
        public MoTask(MoDays parentDay, String taskName, String taskCategory, String taskCategoryColor, String taskTags, Boolean isDone) {
            this.parentDay = parentDay;
            this.taskName = taskName;
            this.taskCategory = taskCategory;
            this.taskCategoryColor = taskCategoryColor;
            this.taskTags = taskTags;
            this.isDone = isDone;
        }

        /// Getter
        public String getTaskName() {
            return taskName;
        }
        public String getTaskCategory() {
            return taskCategory;
        }
        public String getTaskCategoryColor() {
            return taskCategoryColor;
        }
        public String getTaskTags() {
            //TODO: need to add other method to get array of tasks (not just strings with column",")
            return taskTags;
        }
        public Boolean getDone() {
            return isDone;
        }

        /// Setter
        public void setDone(Boolean done) {
            isDone = done;
        }
    }

    public class MoDays {

        public String dayOfWeek;
        public int dayNum;
        public int dayNumAlt;
        public int monthNum;
        public int monthNumAlt;
        public MoTask[] tasks;
        public MoMonth parentMonth;

        public MoDays(MoMonth parentMonth ,String dayOfWeek, int dayNum, MoTask[] tasks) {
            this.parentMonth = parentMonth;
            this.dayOfWeek = dayOfWeek;
            this.dayNum = dayNum;
            this.dayNumAlt = dayNumAlt;
            this.tasks = tasks;
            //TODO: any DayAlt must auto calculated
            //TODO: get the month from parentMonth
        }

        /// Getter
        public String getDayOfWeek() {
            return dayOfWeek;
        }
        public int getDayNum() {
            return dayNum;
        }
        public int getDayNumAlt() {
            return dayNumAlt;
        }
        public int getMonthNum() {
            return monthNum;
        }
        public int getMonthNumAlt() {
            return monthNumAlt;
        }
        public MoTask[] getTasks() {
            return tasks;
        }

        public String getMonthName() {
            return "Name of the month";
        }
        public String getMonthNameAlt() {
            return "Name of the Alt month";
        }

    }

    public class MoMonth {

        public static final int Milady  = 1;
        public static final int HEJRY  = 2;

        public String monthName;
        public String monthNameAlt; // always must be 2 months separated by ","
        public int monthNum;
        public int dateType = Milady;
        public int year;
        public int yearAlt;
        public int daysCount;

        public MoMonth(int monthNum, int year,int dateType, int daysCount) {
            this.monthName = monthName;
            this.monthNameAlt = monthNameAlt;
            this.monthNum = monthNum;
            this.dateType = dateType;
            this.year = year;
            this.yearAlt = yearAlt;
            this.daysCount = daysCount;
        }
    }
}


