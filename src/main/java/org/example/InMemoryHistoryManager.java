package org.example;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static List<Task> lastView = new ArrayList<>();

    @Override
    public List<Task> getHistory(){
        return lastView;
    }

    public void add(Task task){
    }
}
