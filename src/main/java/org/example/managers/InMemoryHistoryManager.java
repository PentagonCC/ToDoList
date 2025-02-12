package org.example.managers;

import org.example.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final MyLinkedList history = new MyLinkedList();

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void add(Task task) {
        history.linkLast(task);
    }

    @Override
    public void remove(int id) {
        history.removeNode(id);
    }

    private static class MyLinkedList {

        private Node<Task> tail;
        private Node<Task> head;

        private static class Node<T> {

            public T task;
            public Node<T> next;
            public Node<T> prev;

            public Node(Node<T> next, T task, Node<T> prev) {
                this.next = next;
                this.task = task;
                this.prev = prev;
            }
        }

        final private Map<Integer, MyLinkedList.Node<Task>> saveId = new HashMap<>();

        public void linkLast(Task task) {
            if (saveId.containsKey(task.getId())) {
                removeNode(saveId.get(task.getId()));
            }
            final Node<Task> currentTail = tail;
            final Node<Task> newNode = new Node<>(null, task, currentTail);
            tail = newNode;
            if (currentTail == null) {
                head = newNode;
            } else {
                currentTail.next = newNode;
            }
            saveId.put(task.getId(), newNode);
        }

        private void removeNode(Node<Task> node) {
            final Node<Task> prev = node.prev;
            final Node<Task> next = node.next;
            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
                node.prev = null;
            }
            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
                node.next = null;
            }
            node.task = null;
        }

        public void removeNode(int id) {
            if (saveId.containsKey(id)) {
                removeNode(saveId.get(id));
            }
        }

        private List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>();
            for (Node<Task> node = head; node != null; node = node.next) {
                tasks.add(node.task);
            }
            return tasks;
        }
    }
}
