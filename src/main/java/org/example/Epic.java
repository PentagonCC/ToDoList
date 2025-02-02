package org.example;

import java.util.HashMap;

public class Epic extends Task {

    private HashMap<Integer, SubTask> subTaskList;
//    private String title;
//    private String description;
//    private int id;
//    private Status status;

    public Epic(String title, String description, int id, Status status){
        super(title, description, id, status);
    }

    public HashMap<Integer, SubTask> getSubTaskList() {
        return subTaskList;
    }
}
