package org.example;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        Epic epic1 = new Epic("HM", "made hm", manager.epicId++);
        Epic epic2 = new Epic("pool", "go swim", manager.epicId++);
        SubTask subTask1 = new SubTask("made math", "calculate number", manager.subTaskId++, epic1.getId());
        SubTask subTask2 = new SubTask("made math", "calculate number 2", manager.subTaskId++, epic1.getId());
        SubTask subTask3 = new SubTask("search", "search place for swimming", manager.subTaskId++, epic2.getId());
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);
        manager.createSubTask(subTask3);
        subTask1.setStatus(Status.IN_PROGRESS);
        manager.changeSubTask(subTask1, subTask1.getId());
        subTask1.setStatus(Status.DONE);
        manager.changeSubTask(subTask1, subTask1.getId());
        subTask2.setStatus(Status.DONE);
        manager.changeSubTask(subTask2, subTask2.getId());
        System.out.println(manager.getEpicById(1));
        System.out.println(manager.getEpicList());
        manager.deleteSubTaskById(3);
        manager.deleteEpicById(1);
    }
}