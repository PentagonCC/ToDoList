package org.example;

import java.util.HashMap;

public class Epic extends Task {

    private final HashMap<Integer, SubTask> subTaskList;

    public Epic(String title, String description, int id) {
        super(title, description, id);
        this.subTaskList = new HashMap<>();
    }

    public HashMap<Integer, SubTask> getSubTaskList() {
        return subTaskList;
    }

}
