package org.example;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Epic epic1 = new Epic("HM", "made hm", manager.id++);
        Epic epic2 = new Epic("pool", "go swim", manager.id++);
        SubTask subTask1 = new SubTask("made math", "calculate number", manager.id++, epic1.getId());
        SubTask subTask2 = new SubTask("made math", "calculate number 2", manager.id++, epic1.getId());
        SubTask subTask3 = new SubTask("search", "search place for swimming", manager.id++, epic2.getId());
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.createSubTask(subTask1);
        manager.createSubTask(subTask2);
        manager.createSubTask(subTask3);
        manager.changeSubTask(subTask1, subTask1.getId());
        System.out.println(manager.getEpicById(1));
        System.out.println(manager.getEpicList());
        manager.deleteSubTaskById(3);
        manager.deleteEpicById(1);
    }
}