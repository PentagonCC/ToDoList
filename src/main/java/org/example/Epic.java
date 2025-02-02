package org.example;

import java.util.HashMap;

public class Epic extends Task {

    private HashMap<Integer, SubTask> subTaskList;
    private String title;
    private String description;
    private int id;
    private Status status;

    public Epic(String title, String description, int id, Status status){
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = Status.NEW;
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
}
