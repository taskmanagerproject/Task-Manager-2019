package com.example.nikhil.taskmanager.model;

public class Tasks {
    private String ID;

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {

        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getPriority() {
        return this.Priority;
    }

    public void setPriority(String priority) {
        this.Priority = priority;
    }

    public String getAssignTo() {
        return this.assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getCreator() {
        return this.Creator;
    }

    public void setCreator(String creator) {
        this.Creator = creator;
    }

    public String getDate_of_creator() {
        return this.Date_of_creator;
    }

    public void setDate_of_creator(String date_of_creator) {
        this.Date_of_creator = date_of_creator;
    }

    private String Title;
    private String Description;
    private String Priority;
    private String assignTo;
    private String Creator;
    private String Date_of_creator;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
