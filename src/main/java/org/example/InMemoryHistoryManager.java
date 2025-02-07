package org.example;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> lastView = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return lastView;
    }

    public void add(Task task) {
    }

    public void remove(int id) {
    }

    private static class MyLinkedList {

        private static class Node<T> {
            public T task;
            public Node<T> next;
            public Node<T> prev;

            public Node(Node<T> prev, T task, Node<T> next) {
                this.prev = prev;
                this.next = next;
                this.task = task;
            }
        }

        private Node<Task> head;
        private Node<Task> tail;

        public void linkLast(Task task) {
            final Node<Task> currentTail = tail;
            final Node<Task> newNode = new Node<>(currentTail, task, null);
            tail = newNode;
            if (currentTail == null) {
                head = newNode;
            } else {
                currentTail.next = newNode;
            }
        }

        public List<Task> getTasks(){
            List<Task> taskList = new ArrayList<>();
            for(Node<Task> node = head; node != null; node = node.next) {
                taskList.add(node.task);
            }
            return taskList;
        }

    }
}
