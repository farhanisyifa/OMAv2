package com.example.oma_v13;

public class Task {
    private String taskName;
    private String taskFrom;
    private String taskDetail;
    private String taskDue;

    public Task(){}

    public Task (String viewTaskName, String viewFromEvent, String viewTaskDetail, String viewTaskDue){
        this.taskName = viewTaskName;
        this.taskFrom = viewFromEvent;
        this.taskDetail = viewTaskDetail;
        this.taskDue = viewTaskDue;

    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskFrom() {
        return taskFrom;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public String getTaskDue() {
        return taskDue;
    }
}

