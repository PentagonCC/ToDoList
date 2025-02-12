package org.example.models;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String title, String description, int id, int epicId) {
        super(title, description, id);
        this.epicId = epicId;
    }

    public SubTask(String title, String description, int id, Status status, int epicId) {
        super(title, description, id, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public TaskType getType(){
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return super.toString() + "," + epicId;
    }
}
