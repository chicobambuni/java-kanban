import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int taskCounter = 0;

    private final int MAX_HISTORY_SIZE = 10;
    private ArrayList<Task> history = new ArrayList<>();

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    public void updateEpicStatus(Epic epic) {
        epic.setStatus(TaskStatus.NEW);

        for (Subtask subtask : epic.getSubtasks()) {
            switch (subtask.getStatus()) {
                case IN_PROGRESS:
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                    return;
                case DONE:
                    epic.setStatus(TaskStatus.DONE);
                    break;
            }
        }
    }

    public void addTaskToHistory(Task task) {
        if (history.size() < MAX_HISTORY_SIZE) {
            history.add(task);
            return;
        }

        history = new ArrayList<>(history.subList(1, MAX_HISTORY_SIZE));
        history.add(task);
    }

    @Override
    public Collection<Task> getHistory() {
        return history;
    }

    @Override
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    @Override
    public Collection<Epic> getAllEpic() {
        return epics.values();
    }


    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
    }

    @Override
    public Task getTaskById(int index) {
        Task task = tasks.get(index);
        addTaskToHistory(task);
        return task;
    }

    @Override
    public Subtask getSubtaskById(int index) {
        Subtask subtask = subtasks.get(index);
        addTaskToHistory(subtask);
        return subtask;
    }

    @Override
    public Epic getEpicById(int index) {
        Epic epic = epics.get(index);
        addTaskToHistory(epic);
        return epic;
    }


    @Override
    public void addTask(Task task) {
        if (task.getIndex() == null) {
            task.setIndex(taskCounter++);
        }

        if (tasks.containsKey(task.getIndex())) {
            throw new IllegalArgumentException("Задача с таким ID уже существует!");
        }

        tasks.put(task.getIndex(), task);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (subtask.getIndex() == null) {
            subtask.setIndex(taskCounter++);
        }

        if (tasks.containsKey(subtask.getIndex())) {
            throw new IllegalArgumentException("Задача с таким ID уже существует!");
        }

        if (subtask.getEpic() != null) {
            subtask.getEpic().getSubtasks().add(subtask);
            updateEpicStatus(subtask.getEpic());
        }

        subtasks.put(subtask.getIndex(), subtask);
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic.getIndex() == null) {
            epic.setIndex(taskCounter++);
        }

        if (tasks.containsKey(epic.getIndex())) {
            throw new IllegalArgumentException("Задача с таким ID уже существует!");
        }

        updateEpicStatus(epic);

        epics.put(epic.getIndex(), epic);
    }


    @Override
    public void updateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не может быть null");
        }

        if (tasks.containsKey(task.getIndex())) {
            tasks.put(task.getIndex(), task);
        } else {
            throw new IllegalArgumentException("Задача не найденна!");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null) {
            throw new IllegalArgumentException("Задача не может быть null");
        }

        if (subtasks.containsKey(subtask.getIndex())) {
            if (subtask.getEpic() != null) {
                updateEpicStatus(subtask.getEpic());
            }

            subtasks.put(subtask.getIndex(), subtask);
        } else {
            throw new IllegalArgumentException("Задача не найденна!");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Задача не может быть null");
        }

        if (epics.containsKey(epic.getIndex())) {
            updateEpicStatus(epic);
            epics.put(epic.getIndex(), epic);
        } else {
            throw new IllegalArgumentException("Задача не найденна!");
        }
    }


    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = subtask.getEpic();

        if (epic != null) {
            epic.getSubtasks().remove(subtask);
            updateEpicStatus(epic);
        }

        subtasks.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        ArrayList<Subtask> epicSubtasks = epic.getSubtasks();

        for (Subtask epicSubtask : epicSubtasks) {
            subtasks.remove(epicSubtask.getIndex());
        }

        epics.remove(id);
    }

    @Override
    public Collection<Subtask> getEpicSubtasks(int id) {
        Epic epic = epics.get(id);

        if (epic == null) {
            throw new IllegalArgumentException("Не найден эпик с заданным идентификатором: " + id);
        }

        return epic.getSubtasks();
    }
}
