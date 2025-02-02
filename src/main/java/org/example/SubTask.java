package org.example;

public class SubTask extends Task {

    private String title;
    private String description;
    private int id;
    private int epicId;
    private Status status;

    public SubTask(String title, String description, int id, int epicId, Status status) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.epicId = epicId;
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

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
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
