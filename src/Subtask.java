public class Subtask extends Task {
    private Epic epic;

    public Subtask(String name, String description, Epic epic) {
        super(name, description, TaskStatus.NEW);
        this.epic = epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }
}
