package org.example.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    private String title;
    private String description;
    private int id;
    private Status status;
    private long duration;
    private LocalDateTime startTime;

    public Task(String title, String description, int id, Status status, long duration, LocalDateTime startTime) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String title, String description, int id, long duration, LocalDateTime startTime) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.duration = duration;
        this.startTime = startTime;
        this.status = Status.NEW;
    }

    public Task() {
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

    public TaskType getType() {
        return TaskType.TASK;
    }

    public LocalDateTime getEndTime() {
        if (startTime != null) {
            return startTime.plusMinutes(duration);
        }
        return null;
    }

    public String getStringEndTime() {
        return getEndTime().toString();
    }

    public long getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getStringStartTime() {
        if (startTime != null) {
            return startTime.toString();
        }
        return null;
    }

    @Override
    public String toString() {
        return title + ", " +
                description + ", " +
                id + ", " +
                status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) && Objects.equals(description, task.description) && id == task.id &&
                Objects.equals(status, task.status);
    }
}

