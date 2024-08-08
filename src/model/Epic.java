package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(int index, String name, String description, List<Subtask> subtasks) {
        super(index, name, description, TaskStatus.NEW);
        this.subtasks = subtasks;
    }

    public Epic(String name, String description, List<Subtask> subtasks) {
        super(name, description, TaskStatus.NEW);
        this.subtasks = subtasks;
    }

    public Epic(String name, String description) {
        this(name, description, new ArrayList<>());
    }

    public Epic(int index, String name, String description) {
        this(index, name, description, new ArrayList<>());
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }
}
