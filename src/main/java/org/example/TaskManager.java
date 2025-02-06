package org.example;

import java.util.HashMap;

interface TaskManager {

    HashMap<Integer, Task> getTaskList();

    HashMap<Integer, Epic> getEpicList();

    HashMap<Integer, SubTask> getSubTaskList();

    void clearTaskList();

    void clearSubTaskList();

    void clearEpicList();

    Task getTaskById(int taskId);

    SubTask getSubTaskById(int subTaskId);

    Epic getEpicById(int epicId);

    void createTask(Task newTask);

    void createSubTask(SubTask newSubTask);

    void createEpic(Epic newEpic);

    void changeTask(Task chagedTask, int idOLdTask);

    void changeSubTask(SubTask changedSubtask, int idOLdSubTask);

    void changeEpic(Epic changedEpic, int idOLdEpic);

    void deleteTaskById(int taskId);

    void deleteSubTaskById(int subTaskId);

    void deleteEpicById(int epicId);

    void updateStatusEpic(Epic epic);

    HashMap<Integer, SubTask> getEpicSubTask(Epic epic);

}

