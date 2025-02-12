package org.example.models;

import java.util.HashMap;

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

    public TaskType getType(){
        return TaskType.EPIC;
    }

}
