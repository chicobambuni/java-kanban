package manager;

import common.HistoryManager;
import common.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int taskCounter = 0;

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();

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

    @Override
    public Collection<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public Collection<Task> getAllTasks() {
        return this.tasks.values();
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return this.subtasks.values();
    }

    @Override
    public Collection<Epic> getAllEpic() {
        return this.epics.values();
    }


    @Override
    public void removeAllTasks() {
        for (Task task : tasks.values()) {
            removeTaskById(task.getIndex());
        }
    }

    @Override
    public void removeAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            removeSubtaskById(subtask.getIndex());
        }
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            removeEpicById(epic.getIndex());
        }
    }

    @Override
    public Task getTaskById(int index) {
        Task task = tasks.get(index);
        historyManager.add(task);
        return task;
    }

    @Override
    public Subtask getSubtaskById(int index) {
        Subtask subtask = subtasks.get(index);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public Epic getEpicById(int index) {
        Epic epic = epics.get(index);
        historyManager.add(epic);
        return epic;
    }


    @Override
    public int addTask(Task task) {
        if (task.getIndex() == null) {
            task.setIndex(taskCounter++);
        }

        if (tasks.containsKey(task.getIndex())) {
            throw new IllegalArgumentException("Задача с таким ID уже существует!");
        }

        tasks.put(task.getIndex(), task);
        return task.getIndex();
    }

    @Override
    public int addSubtask(Subtask subtask) {
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
        return subtask.getIndex();
    }

    @Override
    public int addEpic(Epic epic) {
        if (epic.getIndex() == null) {
            epic.setIndex(taskCounter++);
        }

        if (tasks.containsKey(epic.getIndex())) {
            throw new IllegalArgumentException("Задача с таким ID уже существует!");
        }

        updateEpicStatus(epic);

        epics.put(epic.getIndex(), epic);
        return epic.getIndex();
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
        historyManager.remove(id);
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
        historyManager.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        List<Subtask> epicSubtasks = epic.getSubtasks();

        for (Subtask epicSubtask : epicSubtasks) {
            subtasks.remove(epicSubtask.getIndex());
            historyManager.remove(epicSubtask.getIndex());
        }

        epics.remove(id);
        historyManager.remove(id);
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
