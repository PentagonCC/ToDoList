package org.example.managers;

public class Managers {

    public static TaskManager getDefault() {
        return new FileBackendTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
