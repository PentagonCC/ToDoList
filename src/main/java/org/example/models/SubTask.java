package org.example.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String title, String description, int id, int epicId, long duration, LocalDateTime timeStart) {
        super(title, description, id, duration, timeStart);
        this.epicId = epicId;
    }

    public SubTask(String title, String description, int id, Status status, int epicId, long duration, LocalDateTime timeStart) {
        super(title, description, id, status, duration, timeStart);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return super.toString() + "," + epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTask task = (SubTask) o;
        return Objects.equals(getTitle(), task.getTitle()) && Objects.equals(getDescription(), task.getDescription()) &&
                getId() == task.getId() && Objects.equals(getStatus(), task.getStatus()) && getEpicId() == task.getEpicId();
    }
}
