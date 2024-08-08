package model;

public class Subtask extends Task {
    private Epic epic;

    public Subtask(int index, String name, String description, TaskStatus status, Epic epic) {
        super(name, description, status);
        this.index = index;
        this.epic = epic;
    }

    public Subtask(int index, String name, String description, Epic epic) {
        this(index, name, description, TaskStatus.NEW, epic);
    }

    public Subtask(String name, String description, TaskStatus status, Epic epic) {
        super(name, description, status);
        this.epic = epic;
    }

    public Subtask(String name, String description, Epic epic) {
        this(name, description, TaskStatus.NEW, epic);
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }
}
