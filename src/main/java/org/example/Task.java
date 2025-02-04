package org.example;

public class Task {

    private String title;
    private String description;
    private int id;
    private Status status;

    public Task(String title, String description, int id, Status status){
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public Task(String title, String description, int id){
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = Status.NEW;
    }

    public Task(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

