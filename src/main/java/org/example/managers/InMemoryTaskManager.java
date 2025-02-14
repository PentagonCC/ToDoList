package org.example.managers;

import org.example.models.Epic;
import org.example.models.Status;
import org.example.models.SubTask;
import org.example.models.Task;

import java.time.LocalDateTime;
import java.util.*;


public class InMemoryTaskManager implements TaskManager {

    protected final HashMap<Integer, Task> taskList;
    protected final HashMap<Integer, Epic> epicList;
    protected final HashMap<Integer, SubTask> subTaskList;
    protected final HistoryManager historyManager = Managers.getDefaultHistory();
    public int taskId = 0;
    public int epicId = 0;
    public int subTaskId = 0;

    final static Comparator<Task> COMPARATOR = Comparator.comparing(Task::getStartTime,
            Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Task::getId);

    protected Set<Task> prioritizedTask = new TreeSet<>(COMPARATOR);

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
        prioritizedTask.add(newTask);
    }

    @Override
    public void createSubTask(SubTask newSubTask) {
        subTaskList.put(newSubTask.getId(), newSubTask);
        epicList.get(newSubTask.getEpicId()).getSubTaskList().put(newSubTask.getId(), newSubTask);
        updateStatusEpic(epicList.get(newSubTask.getEpicId()));
        setEpicDateTime(newSubTask.getEpicId());
        prioritizedTask.add(newSubTask);
    }

    @Override
    public void createEpic(Epic newEpic) {
        epicList.put(newEpic.getId(), newEpic);
    }

    @Override
    public void changeTask(Task chagedTask, int idTask) {
        if (taskList.get(idTask) != null) {
            taskList.put(idTask, chagedTask);
            prioritizedTask.add(chagedTask);
        }
    }

    @Override
    public void changeSubTask(SubTask changedSubtask, int idTask) {
        if (subTaskList.get(idTask) != null) {
            subTaskList.put(idTask, changedSubtask);
            epicList.get(changedSubtask.getEpicId()).getSubTaskList().put(idTask, changedSubtask);
            updateStatusEpic(epicList.get(changedSubtask.getEpicId()));
            prioritizedTask.add(changedSubtask);
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
            prioritizedTask.remove(taskList.get(taskId));
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
            prioritizedTask.remove(subTaskList.get(subTaskId));
        }
    }

    @Override
    public void deleteEpicById(int epicId) {
        if (epicList.get(epicId) != null) {
            for (SubTask subTask : epicList.get(epicId).getSubTaskList().values()) {
                if (subTask.getEpicId() == epicId) {
                    prioritizedTask.remove(subTask);
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
    public void setEpicDateTime(int epicId) {
        Map<Integer, SubTask> subTasks = epicList.get(epicId).getSubTaskList();
        if (subTasks.isEmpty()) {
            epicList.get(epicId).setDuration(0L);
            epicList.get(epicId).setStartTime(null);
            epicList.get(epicId).setEndTime(null);
            return;
        }
        LocalDateTime epicStartTime = null;
        LocalDateTime epicEndTime = null;
        long epicDuration = 0L;
        for (SubTask subTask : subTasks.values()) {
            LocalDateTime subtaskStartTime = subTask.getStartTime();
            LocalDateTime subtaskEndTime = subTask.getEndTime();
            if (subtaskStartTime != null) {
                if (epicStartTime == null || subtaskStartTime.isBefore(epicStartTime)) {
                    epicStartTime = subtaskStartTime;
                }
            }
            if (subtaskEndTime != null) {
                if (epicEndTime == null || subtaskEndTime.isAfter(epicEndTime)) {
                    epicEndTime = subtaskEndTime;
                }
            }
            epicDuration += subTask.getDuration();
        }
        epicList.get(epicId).setStartTime(epicStartTime);
        epicList.get(epicId).setEndTime(epicEndTime);
        epicList.get(epicId).setDuration(epicDuration);
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTask);
    }

    @Override
    public HashMap<Integer, SubTask> getEpicSubTask(Epic epic) {
        return epic.getSubTaskList();
    }
}
