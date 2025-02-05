package org.example;

import java.util.HashMap;

public class Manager {

    private HashMap<Integer, Task> taskList;
    private HashMap<Integer, Epic> epicList;
    private HashMap<Integer, SubTask> subTaskList;
    public int taskId = 0;
    public int epicId = 0;
    public int subTaskId = 0;

    public Manager(){
        this.taskList = new HashMap<>();
        this.epicList = new HashMap<>();
        this.subTaskList = new HashMap<>();
    }


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
        Task searchTask = null;
        for(Integer id: taskList.keySet()){
            if(id.equals(taskId)){
                searchTask = taskList.get(taskId);
            }
        }
        return searchTask;
    }

    public SubTask getSubTaskById(int subTaskId){
        SubTask searchTask = null;
        for(Integer id: subTaskList.keySet()){
            if(id.equals(subTaskId)){
                searchTask = subTaskList.get(subTaskId);
            }
        }
        return searchTask;
    }

    public Epic getEpicById(int epicId){
        Epic searchEpic = null;
        for(Integer id: epicList.keySet()){
            if(id.equals(epicId)){
                searchEpic = epicList.get(epicId);
            }
        }
        return searchEpic;
    }

    public void createTask(Task newTask){
        taskList.put(newTask.getId(), newTask);
    }

    public void createSubTask(SubTask newSubTask){
        subTaskList.put(newSubTask.getId(), newSubTask);
        epicList.get(newSubTask.getEpicId()).getSubTaskList().put(newSubTask.getId(), newSubTask);
        updateStatusEpic(epicList.get(newSubTask.getEpicId()));
    }

    public void createEpic(Epic newEpic){
        epicList.put(newEpic.getId(), newEpic);
    }

    public void changeTask(Task chagedTask, int idOLdTask){
        for(Integer id: taskList.keySet()){
            if(id.equals(idOLdTask)){
                taskList.put(id, chagedTask);
            }
        }
    }

    public void changeSubTask(SubTask changedSubtask, int idOLdSubTask){
        for(Integer id: subTaskList.keySet()){
            if(id.equals(idOLdSubTask)){
                subTaskList.put(id, changedSubtask);
                epicList.get(changedSubtask.getEpicId()).getSubTaskList().put(id, changedSubtask);
                updateStatusEpic(epicList.get(changedSubtask.getEpicId()));
            }
        }
    }

    public void changeEpic(Epic changedEpic, int idOLdEpic){
        for(Integer id: epicList.keySet()){
            if(id.equals(idOLdEpic)){
                epicList.put(id, changedEpic);
            }
        }
    }

    public void deleteTaskById(int taskId){
        for(Integer id: taskList.keySet()){
            if (id.equals(taskId)){
                taskList.remove(taskId);
            }
        }
    }

    public void deleteSubTaskById(int subTaskId){
        for(Integer id: subTaskList.keySet()){
            if(id.equals(subTaskId)){
                subTaskList.remove(subTaskId);
                return;
            }
        }
    }

    public void deleteEpicById(int epicId){
        for(Integer id: epicList.keySet()){
            if(id.equals(epicId)){
                epicList.get(epicId).getSubTaskList().clear();
                epicList.remove(epicId);
                return;
            }
        }
    }

    public void updateStatusEpic(Epic epic){
        int counterSubTask = 0;
        for (SubTask subTask: epic.getSubTaskList().values()){
            if(subTask.getStatus() == Status.IN_PROGRESS){
                epic.setStatus(Status.IN_PROGRESS);
            }else if(subTask.getStatus() == Status.DONE){
                counterSubTask++;
            }
        }
        if(counterSubTask == epic.getSubTaskList().size()){
            epic.setStatus(Status.DONE);
        }
    }

    public HashMap<Integer, SubTask> getEpicSubTask(Epic epic){
        return epic.getSubTaskList();
    }

}

