package com.example.hazemnabil.islamictodo2.calenderMonth.objData;

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
