import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        this.subtasks = new ArrayList<>();
        updateStatus();
    }

    public Epic(String name, String description, ArrayList<Subtask> subtasks) {
        super(name, description, TaskStatus.NEW);
        this.subtasks = subtasks;
        updateStatus();
    }

    public Epic(int uuid, String name, String description, ArrayList<Subtask> subtasks) {
        super(uuid, name, description);
        this.subtasks = subtasks;
        updateStatus();
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public void setStatus(TaskStatus status) {
        //Пытался делать в рамках задачи,
        //не совсем понял как именно подразумевалось
        //запретить изменение статуса эпика.
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
        updateStatus();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateStatus();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        updateStatus();
    }

    public void updateStatus() {
        setStatus(TaskStatus.NEW);

        for (Subtask subtask : getSubtasks()) {
            switch (subtask.getStatus()) {
                case IN_PROGRESS: status = TaskStatus.IN_PROGRESS; return;
                case DONE: status = TaskStatus.DONE; break;
            }
        }
    }
}
