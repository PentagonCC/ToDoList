package org.example;

import org.example.managers.InMemoryTaskManager;
import org.example.models.Epic;
import org.example.models.Status;
import org.example.models.SubTask;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic("HM", "made hm", manager.epicId++);
        Epic epic1 = new Epic("pool", "go swim", manager.epicId++);
        SubTask subTask = new SubTask("made math", "calculate number", manager.subTaskId++, epic.getId());
        SubTask subTask1 = new SubTask("made math", "calculate number 2", manager.subTaskId++, epic.getId());
        SubTask subTask2 = new SubTask("search", "search place for swimming", manager.subTaskId++, epic1.getId());
        manager.createEpic(epic);
        manager.createEpic(epic1);
        manager.createSubTask(subTask);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);
        subTask2.setStatus(Status.IN_PROGRESS);
//        subTask1.setStatus(Status.IN_PROGRESS);
        manager.changeSubTask(subTask2, subTask2.getId());
//        subTask1.setStatus(Status.DONE);
//        manager.changeSubTask(subTask1, subTask1.getId());
//        subTask2.setStatus(Status.DONE);
//        manager.changeSubTask(subTask2, subTask2.getId());
        manager.getEpicById(1);
//        manager.getSubTaskById(1);
//        System.out.println(manager.history());
//        manager.getEpicById(0);
//        manager.getEpicById(1);
//        manager.getSubTaskById(1);
//        System.out.println(manager.historyManager.getHistory());
//        System.out.println(manager.getEpicList());
        manager.deleteSubTaskById(2);
        System.out.println(epic1.getStatus());
//        manager.deleteEpicById(0);
//        System.out.println("----------------------------------------------");
//        System.out.println(manager.historyManager.getHistory());
//        System.out.println("----------------------------------------------");
//        System.out.println(epic1.getSubTaskList());
    }
}
