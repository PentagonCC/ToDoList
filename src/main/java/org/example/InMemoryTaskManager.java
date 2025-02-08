package org.example;

import java.util.HashMap;


public class InMemoryTaskManager implements TaskManager {

    private final HashMap<Integer, Task> taskList;
    private final HashMap<Integer, Epic> epicList;
    private final HashMap<Integer, SubTask> subTaskList;
    public HistoryManager historyManager = Managers.getDefaultHistory();
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
            historyManager.add(searchTask);
        }
        return searchTask != null ? searchTask.toString() : "Задача не найдена";
    }

    @Override
    public String getSubTaskById(int subTaskId) {
        SubTask searchTask = subTaskList.get(subTaskId);
        if (searchTask != null) {
            historyManager.add(searchTask);
        }
        return searchTask != null ? searchTask.toString() : "Подзадача не найдена";
    }

    @Override
    public String getEpicById(int epicId) {
        Epic searchEpic = epicList.get(epicId);
        if (searchEpic != null) {
            historyManager.add(searchEpic);
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
    public void changeTask(Task chagedTask, int idTask) {
        if (taskList.get(idTask) != null) {
            taskList.put(idTask, chagedTask);
        }
    }

    @Override
    public void changeSubTask(SubTask changedSubtask, int idTask) {
        if (subTaskList.get(idTask) != null) {
            subTaskList.put(idTask, changedSubtask);
            epicList.get(changedSubtask.getEpicId()).getSubTaskList().put(idTask, changedSubtask);
            updateStatusEpic(epicList.get(changedSubtask.getEpicId()));

        }
    }

    @Override
    public void changeEpic(Epic changedEpic, int idEpic) {
        if (epicList.get(idEpic) != null) {
            epicList.put(idEpic, changedEpic);
        }
    }

    @Override
    public void deleteTaskById(int taskId) {
        if (taskList.get(taskId) != null) {
            taskList.remove(taskId);
        }
    }

    @Override
    public void deleteSubTaskById(int subTaskId) {
        if (subTaskList.get(subTaskId) != null) {
            Epic tempEpic = epicList.get(subTaskList.get(subTaskId).getEpicId());
            subTaskList.remove(subTaskId);
            historyManager.remove(subTaskId);
            tempEpic.getSubTaskList().remove(subTaskId);
            updateStatusEpic(tempEpic);
        }
    }

    @Override
    public void deleteEpicById(int epicId) {
        if (epicList.get(epicId) != null) {
            for (SubTask subTask : epicList.get(epicId).getSubTaskList().values()) {
                if (subTask.getEpicId() == epicId) {
                    historyManager.remove(subTask.getId());
                }
            }
            epicList.get(epicId).getSubTaskList().clear();
            epicList.remove(epicId);
            historyManager.remove(epicId);
        }
    }

    @Override
    public void updateStatusEpic(Epic epic) {
        int counterSubTask = 0;
        if (epic.getSubTaskList().isEmpty()) {
            epic.setStatus(Status.NEW);
        }
        for (SubTask subTask : epic.getSubTaskList().values()) {
            if (subTask.getStatus() == Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
            } else if (subTask.getStatus() == Status.DONE) {
                counterSubTask++;
            }
        }
        if (counterSubTask == epic.getSubTaskList().size() && counterSubTask != 0) {
            epic.setStatus(Status.DONE);
        }
    }

    @Override
    public HashMap<Integer, SubTask> getEpicSubTask(Epic epic) {
        return epic.getSubTaskList();
    }
}
