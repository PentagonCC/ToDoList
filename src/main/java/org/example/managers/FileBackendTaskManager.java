package org.example.managers;

import org.example.exceptions.ManagerSaveException;
import org.example.models.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileBackendTaskManager extends InMemoryTaskManager {

    private final File FILE = new File("src/main/resources/tasks.csv");
    private static final String FIRST_LINE = "id,type,title,status,description,startTime, endTime, duration, epic";

    public FileBackendTaskManager() {
    }

    protected FileBackendTaskManager loadFromFile(File file) {
        FileBackendTaskManager FBTM = new FileBackendTaskManager();
        List<Integer> taskHistoryId = new ArrayList<>();
        Map<Integer, Task> history = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            List<String> fileLines = br.lines().collect(Collectors.toList());
            for (int i = 1; i < fileLines.size(); i++) {
                if (fileLines.get(i).isEmpty() && !fileLines.get(i + 1).isEmpty()) {
                    taskHistoryId = fromString(fileLines.get(i + 1));
                    break;
                }
                String[] value = fileLines.get(i).split(",");
                Task task = taskFromString(value);
                history.put(task.getId(), task);
                switch (task.getType()) {
                    case TASK:
                        FBTM.taskList.put(task.getId(), task);
                        break;
                    case SUBTASK:
                        FBTM.subTaskList.put(task.getId(), (SubTask) task);
                        int epicId = ((SubTask) task).getEpicId();
                        FBTM.getEpicList().get(epicId).getSubTaskList().put(task.getId(), (SubTask) task);
                        FBTM.updateStatusEpic(FBTM.getEpicList().get(epicId));
                        break;
                    case EPIC:
                        FBTM.epicList.put(task.getId(), (Epic) task);
                        break;
                }
            }
            for (Integer id : taskHistoryId) {
                FBTM.historyManager.add(history.get(id));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FBTM;
    }

    private String toString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getTitle() + "," + task.getStatus() + ","
                + task.getDescription() + "," + task.getStringStartTime() + "," + task.getStringEndTime() + "," +
                task.getDuration();
    }

    private String toString(SubTask subTask) {
        return subTask.getId() + "," + subTask.getType() + "," + subTask.getTitle() + "," + subTask.getStatus() + ","
                + subTask.getDescription() + "," + subTask.getStringStartTime() + "," + subTask.getStringEndTime() +
                "," + subTask.getDuration() + "," + subTask.getEpicId();
    }

    private String toString(Epic epic) {
        return epic.getId() + "," + epic.getType() + "," + epic.getTitle() + "," + epic.getStatus() + ","
                + epic.getDescription() + "," + epic.getStringStartTime() + "," + epic.getStringEndTime() + "," +
                epic.getDuration();
    }

    private Task taskFromString(String[] value) {
        int taskId = Integer.parseInt(value[0]);
        TaskType taskType = TaskType.valueOf(value[1]);
        String taskTitle = value[2];
        Status taskStatus = Status.valueOf(value[3]);
        String taskDescription = value[4];
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (!value[5].equals("null")) {
            startTime = LocalDateTime.parse(value[5]);
            endTime = LocalDateTime.parse(value[6]);
        } else {
            startTime = null;
            endTime = null;
        }
        long duration = Long.parseLong(value[7]);
        switch (taskType) {
            case EPIC:
                return new Epic(taskTitle, taskDescription, taskId, taskStatus, duration, startTime, endTime);
            case TASK:
                return new Task(taskTitle, taskDescription, taskId, taskStatus, duration, startTime);
            case SUBTASK:
                int epicId = Integer.parseInt(value[5]);
                return new SubTask(taskTitle, taskDescription, taskId, taskStatus, epicId, duration, startTime);
        }
        return null;
    }

    private static List<Integer> fromString(String value) {
        List<Integer> historyTaskId = new ArrayList<>();
        String[] line = value.split(",");
        for (String taskId : line) {
            historyTaskId.add(Integer.parseInt(taskId));
        }
        return historyTaskId;
    }

    protected void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            bw.write(FIRST_LINE);
            bw.newLine();
            addToFile(bw);
            bw.newLine();
            List<String> taskHistoryId = new ArrayList<>();
            for (Task task : historyManager.getHistory()) {
                taskHistoryId.add(String.valueOf(task.getId()));
            }
            bw.write(String.join(",", taskHistoryId));
        } catch (IOException e) {
            throw new ManagerSaveException();
        }


    }

    private void addToFile(BufferedWriter bw) throws IOException {
        for (Epic epic : getEpicList().values()) {
            bw.write(toString(epic));
            bw.newLine();
        }
        for (Task task : getTaskList().values()) {
            bw.write(toString(task));
            bw.newLine();
        }
        for (SubTask subTask : getSubTaskList().values()) {
            bw.write(toString(subTask));
            bw.newLine();
        }
    }

    @Override
    public void clearTaskList() {
        super.clearTaskList();
        save();
    }

    @Override
    public void clearSubTaskList() {
        super.clearSubTaskList();
        save();
    }

    @Override
    public void clearEpicList() {
        super.clearEpicList();
        save();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public SubTask getSubTaskById(int subTaskId) {
        SubTask subTask = super.getSubTaskById(subTaskId);
        save();
        return subTask;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = super.getEpicById(epicId);
        save();
        return epic;
    }

    @Override
    public void createTask(Task newTask) {
        super.createTask(newTask);
        save();
    }

    @Override
    public void createSubTask(SubTask newSubTask) {
        super.createSubTask(newSubTask);
        save();
    }

    @Override
    public void createEpic(Epic newEpic) {
        super.createEpic(newEpic);
        save();
    }

    @Override
    public void changeTask(Task chagedTask, int idTask) {
        super.changeTask(chagedTask, idTask);
        save();
    }

    @Override
    public void changeSubTask(SubTask changedSubtask, int idTask) {
        super.changeSubTask(changedSubtask, idTask);
        save();
    }

    @Override
    public void changeEpic(Epic changedEpic, int idEpic) {
        super.changeEpic(changedEpic, idEpic);
        save();
    }

    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
    }

    @Override
    public void deleteSubTaskById(int subTaskId) {
        super.deleteSubTaskById(subTaskId);
        save();
    }

    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
    }

    @Override
    public void updateStatusEpic(Epic epic) {
        super.updateStatusEpic(epic);
        save();
    }

}
