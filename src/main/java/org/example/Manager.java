package org.example;

import java.util.HashMap;

public class Manager {

    private HashMap<Integer, Task> taskList;
    private HashMap<Integer, Epic> epicList;
    private HashMap<Integer, SubTask> subTaskList;

    public HashMap<Integer, Task> getTaskList() {
        return taskList;
    }

    public HashMap<Integer, Epic> getEpicList() {
        return epicList;
    }

    public HashMap<Integer, SubTask> getSubTaskList() {
        return subTaskList;
    }

    public void clearTaskList(){
        taskList.clear();
    }

    public void clearSubTaskList(){
        subTaskList.clear();
    }

    public void clearEpicList(){
        clearSubTaskList();
        epicList.clear();
    }

    public Task getTaskById(int taskId){
        return;
    }

    public SubTask getSubTaskById(int subTaskId){
        return;
    }

    public Epic getEpicById(int epicId){
        return;
    }

    public void createTask(Task newTask){

    }

    public void createSubTask(SubTask newSubTask){

    }

    public void createEpic(Epic newEpic){

    }

    public void changeTask(Task chagedTask){

    }

    public void changeSubTask(SubTask changedSubtask){

    }

    public void changeEpic(Epic changedEpic){

    }

    public void deleteTaskById(int taskId){

    }

    public void deleteSubTaskById(int subTaskId){

    }

    public void deleteEpicById(int epicId){

    }

    public HashMap<Integer, SubTask> getEpicSubTask(Epic epic){
        return ;
    }

}

