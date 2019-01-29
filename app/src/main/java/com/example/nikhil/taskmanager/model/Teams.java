package com.example.nikhil.taskmanager.model;

public class Teams {
    private String Name;
    private String id;

    public void setTeamName(String teamName) {
        this.Name = teamName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return this.Name;
    }

    public String getId() {
        return this.id;
    }
}
