public class Subtask extends Task {
    private final Epic epic;

    public Subtask(String name, String description, Epic epic) {
        super(name, description, TaskStatus.NEW);
        this.epic = epic;
        updateEpicStatus();
    }

    public Subtask(int uuid, String name, String description, TaskStatus status, Epic epic) {
        super(uuid, name, description, status);
        this.epic = epic;
        updateEpicStatus();
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public void setStatus(TaskStatus status) {
        super.setStatus(status);
        updateEpicStatus();
    }

    public void updateEpicStatus() {
        epic.updateStatus();
    }
}
