import manager.InMemoryHistoryManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    int taskIndex = 0;
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    private int nextUniqueIndex() {
        return taskIndex++;
    }

    @Test
    public void mustAddToHistory() {
        Task task = new Task(nextUniqueIndex(), "Задача", "Описание");
        Epic epic = new Epic(nextUniqueIndex(), "Эпик", "Описание");
        Subtask subtask = new Subtask(nextUniqueIndex(), "Подзадача", "Описание", epic);

        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);

        assertTrue(historyManager.getHistory().contains(task), "Отсутствует задача");
        assertTrue(historyManager.getHistory().contains(epic), "Отсутствует эпик");
        assertTrue(historyManager.getHistory().contains(subtask), "Отсутствует подзадача");
    }

    @Test
    public void mustRemoveFromHistory() {
        Task task = new Task(nextUniqueIndex(), "Задача", "Описание");

        historyManager.add(task);
        historyManager.remove(task.getIndex());

        assertFalse(historyManager.getHistory().contains(task), "Присутствует задача");
    }

    @Test
    public void mustRemoveAllFromHistory() {
        Task task = new Task(nextUniqueIndex(), "Задача", "Описание");

        historyManager.add(task);

        ArrayList<Task> history = historyManager.getHistory();
        for (Task value : history) {
            historyManager.remove(value.getIndex());
        }

        assertFalse(historyManager.getHistory().contains(task), "История не пуста!");
    }

    @Test
    public void mustContainOnlyUnique() {
        int index = nextUniqueIndex();
        Task task = new Task(index, "Задача", "Описание", TaskStatus.NEW);
        historyManager.add(task);
        historyManager.add(task);

        int numberOfSamples = 0;
        ArrayList<Task> history = historyManager.getHistory();

        for (Task value : history) {
            if (value.getIndex() == index) {
                numberOfSamples++;
            }
        }

        assertFalse(numberOfSamples != 1, "Задача имеет дубликат в истории");
    }
}