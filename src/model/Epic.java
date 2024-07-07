package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    public Epic(int index, String name, String description, ArrayList<Subtask> subtasks) {
        super(index, name, description, TaskStatus.NEW);
        this.subtasks = subtasks;
    }

    public Epic(String name, String description, ArrayList<Subtask> subtasks) {
        super(name, description, TaskStatus.NEW);
        this.subtasks = subtasks;
    }

    public Epic(String name, String description) {
        this(name, description, new ArrayList<>());
    }

    public Epic(int index, String name, String description) {
        this(index, name, description, new ArrayList<>());
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }
}
