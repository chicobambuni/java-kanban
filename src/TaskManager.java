import java.util.Collection;
import java.util.HashMap;

public class TaskManager {
    private int taskCounter = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    protected void updateEpicStatus(Epic epic) {
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


    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    public Collection<Epic> getAllEpic() {
        return epics.values();
    }


    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
    }


    public Task getTaskById(int index) {
        return tasks.get(index);
    }

    public Subtask getSubtaskById(int index) {
        return subtasks.get(index);
    }

    public Epic getEpicById(int index) {
        return epics.get(index);
    }


    public void addTask(Task task) {
        if (task.getIndex() == null) {
            task.setIndex(taskCounter++);
        }

        if (tasks.containsKey(task.getIndex())) {
            throw new IllegalArgumentException("Задача с таким ID уже существует!");
        }

        tasks.put(task.getIndex(), task);
    }

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


    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeSubtaskById(int id) {
        subtasks.remove(id);
    }

    public void removeEpicById(int id) {
        epics.remove(id);
    }


    public Collection<Subtask> getEpicSubtasks(int id) {
        Epic epic = epics.get(id);

        if (epic == null) {
            throw new IllegalArgumentException("Не найден эпик с заданным идентификатором: " + id);
        }

        return epic.getSubtasks();
    }
}
