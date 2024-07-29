package manager;

import common.HistoryManager;
import model.Task;
import model.TaskNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, TaskNode> nodes;
    private TaskNode first;
    private TaskNode last;

    public InMemoryHistoryManager() {
        nodes = new HashMap<>();
    }

    private void linkLast(Task task) {
        TaskNode newNode = new TaskNode(task, null, null);

        if (last == null) {
            first = newNode;
        } else {
            last.setNext(newNode);
            newNode.setPrev(last);
        }

        last = newNode;
        nodes.put(task.getIndex(), newNode);
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        TaskNode current = first;

        while (current != null) {
            tasks.add(current.getTask());
            current = current.getNext();
        }

        return tasks;
    }

    private void removeNode(TaskNode node) {
        TaskNode prev = node.getPrev();
        TaskNode next = node.getNext();

        if (prev == null) {
            first = next;
        } else {
            prev.setNext(next);
        }

        if (next == null) {
            last = prev;
        } else {
            next.setPrev(prev);
        }
    }

    @Override
    public void add(Task task) {
        if (nodes.containsKey(task.getIndex())) {
            remove(task.getIndex());
        }

        linkLast(task);
    }

    @Override
    public void remove(int id) {
        removeNode(nodes.get(id));
        nodes.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}
