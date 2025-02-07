package org.example;

import java.util.HashMap;


public class InMemoryTaskManager implements TaskManager {

    private final HashMap<Integer, Task> taskList;
    private final HashMap<Integer, Epic> epicList;
    private final HashMap<Integer, SubTask> subTaskList;
    private final HistoryManager lastViewList = Managers.getDefaultHistory();
    public int taskId = 0;
    public int epicId = 0;
    public int subTaskId = 0;

    public InMemoryTaskManager() {
        this.taskList = new HashMap<>();
        this.epicList = new HashMap<>();
        this.subTaskList = new HashMap<>();
    }

    @Override
    public HashMap<Integer, Task> getTaskList() {
        return taskList;
    }

    @Override
    public HashMap<Integer, Epic> getEpicList() {
        return epicList;
    }

    @Override
    public HashMap<Integer, SubTask> getSubTaskList() {
        return subTaskList;
    }

    @Override
    public void clearTaskList() {
        taskList.clear();
    }

    @Override
    public void clearSubTaskList() {
        subTaskList.clear();
    }

    @Override
    public void clearEpicList() {
        clearSubTaskList();
        epicList.clear();
    }

    @Override
    public String getTaskById(int taskId) {
        Task searchTask = taskList.get(taskId);
        if (searchTask != null) {
            if (lastViewList.getHistory().size() == 10) {
                lastViewList.getHistory().remove(0);
            }
            lastViewList.getHistory().add(searchTask);
        }
        return searchTask != null ? searchTask.toString() : "Задача не найдена";
    }

    @Override
    public String getSubTaskById(int subTaskId) {
        SubTask searchTask = subTaskList.get(subTaskId);
        if (searchTask != null) {
            if (lastViewList.getHistory().size() == 10) {
                lastViewList.getHistory().remove(0);
            }
            lastViewList.getHistory().add(searchTask);
        }
        return searchTask != null ? searchTask.toString() : "Подзадача не найдена";
    }

    @Override
    public String getEpicById(int epicId) {
        Epic searchEpic = epicList.get(epicId);
        if (searchEpic != null) {
            if (lastViewList.getHistory().size() == 10) {
                lastViewList.getHistory().remove(0);
            }
            lastViewList.getHistory().add(searchEpic);
        }
        return searchEpic != null ? searchEpic.toString() : "Epic не найден";
    }

    @Override
    public void createTask(Task newTask) {
        taskList.put(newTask.getId(), newTask);
    }

    @Override
    public void createSubTask(SubTask newSubTask) {
        subTaskList.put(newSubTask.getId(), newSubTask);
        epicList.get(newSubTask.getEpicId()).getSubTaskList().put(newSubTask.getId(), newSubTask);
        updateStatusEpic(epicList.get(newSubTask.getEpicId()));
    }

    @Override
    public void createEpic(Epic newEpic) {
        epicList.put(newEpic.getId(), newEpic);
    }

    @Override
    public void changeTask(Task chagedTask, int idOLdTask) {
        for (Integer id : taskList.keySet()) {
            if (id.equals(idOLdTask)) {
                taskList.put(id, chagedTask);
            }
        }
    }

    @Override
    public void changeSubTask(SubTask changedSubtask, int idOLdSubTask) {
        for (Integer id : subTaskList.keySet()) {
            if (id.equals(idOLdSubTask)) {
                subTaskList.put(id, changedSubtask);
                epicList.get(changedSubtask.getEpicId()).getSubTaskList().put(id, changedSubtask);
                updateStatusEpic(epicList.get(changedSubtask.getEpicId()));
            }
        }
    }

    @Override
    public void changeEpic(Epic changedEpic, int idOLdEpic) {
        for (Integer id : epicList.keySet()) {
            if (id.equals(idOLdEpic)) {
                epicList.put(id, changedEpic);
            }
        }
    }

    @Override
    public void deleteTaskById(int taskId) {
        for (Integer id : taskList.keySet()) {
            if (id.equals(taskId)) {
                taskList.remove(taskId);
            }
        }
    }

    @Override
    public void deleteSubTaskById(int subTaskId) {
        for (Integer id : subTaskList.keySet()) {
            if (id.equals(subTaskId)) {
                subTaskList.remove(subTaskId);
                return;
            }
        }
    }

    @Override
    public void deleteEpicById(int epicId) {
        for (Integer id : epicList.keySet()) {
            if (id.equals(epicId)) {
                epicList.get(epicId).getSubTaskList().clear();
                epicList.remove(epicId);
                return;
            }
        }
    }

    @Override
    public void updateStatusEpic(Epic epic) {
        int counterSubTask = 0;
        for (SubTask subTask : epic.getSubTaskList().values()) {
            if (subTask.getStatus() == Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
            } else if (subTask.getStatus() == Status.DONE) {
                counterSubTask++;
            }
        }
        if (counterSubTask == epic.getSubTaskList().size()) {
            epic.setStatus(Status.DONE);
        }
    }

    @Override
    public HashMap<Integer, SubTask> getEpicSubTask(Epic epic) {
        return epic.getSubTaskList();
    }
}
