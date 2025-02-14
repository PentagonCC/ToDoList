package org.example.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {

    private final HashMap<Integer, SubTask> subTaskList = new HashMap<>();
    private LocalDateTime endTime;

    public Epic(String title, String description, int id, long duration, LocalDateTime startTime) {
        super(title, description, id, duration, startTime);
    }


    public Epic(String title, String description, int id, Status status, long duration, LocalDateTime startTime) {
        super(title, description, id, status, duration, startTime);
    }

    public Epic(String title, String description, int id, Status status, long duration, LocalDateTime startTime, LocalDateTime endTime) {
        super(title, description, id, status, duration, startTime);
        this.endTime = endTime;
    }

    public HashMap<Integer, SubTask> getSubTaskList() {
        return subTaskList;
    }

    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String getStringEndTime() {
        if (endTime == null) {
            return "null";
        }
        return getEndTime().toString();
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(getTitle(), epic.getTitle()) && Objects.equals(getDescription(), epic.getDescription()) &&
                getId() == epic.getId() && Objects.equals(getStatus(), epic.getStatus());
    }
}
