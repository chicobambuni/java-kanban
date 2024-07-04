import java.util.Collection;

public interface TaskManager {
    public Collection<Task> getAllTasks();
    public Collection<Subtask> getAllSubtasks();
    public Collection<Epic> getAllEpic();
    public void removeAllTasks();
    public void removeAllSubtasks();
    public void removeAllEpics();
    public Task getTaskById(int index);
    public Subtask getSubtaskById(int index);
    public Epic getEpicById(int index);
    public void addTask(Task task);
    public void addSubtask(Subtask subtask);
    public void addEpic(Epic epic);
    public void updateTask(Task task);
    public void updateSubtask(Subtask subtask);
    public void updateEpic(Epic epic);
    public void removeTaskById(int id);
    public void removeSubtaskById(int id);
    public void removeEpicById(int id);
    public Collection<Subtask> getEpicSubtasks(int id);
}
