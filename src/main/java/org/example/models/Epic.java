package org.example.models;

import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task {

    private final HashMap<Integer, SubTask> subTaskList = new HashMap<>();

    public Epic(String title, String description, int id) {
        super(title, description, id);
    }

    public Epic(String title, String description, int id, Status status) {
        super(title, description, id, status);
    }

    public HashMap<Integer, SubTask> getSubTaskList() {
        return subTaskList;
    }

    public TaskType getType() {
        return TaskType.EPIC;
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
