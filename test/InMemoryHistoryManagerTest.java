import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    public void mustAddToHistory() {
        Task task = new Task("Задача", "Описание");
        Epic epic = new Epic("Эпик", "Описание");
        Subtask subtask = new Subtask("Подзадача", "Описание", epic);

        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);

        assertTrue(historyManager.getHistory().contains(task), "Отсутствует задача");
        assertTrue(historyManager.getHistory().contains(epic), "Отсутствует подзадача");
        assertTrue(historyManager.getHistory().contains(subtask), "Отсутствует эпик");
    }

    @Test
    public void shouldSavePreviousVersionOfTask() {
        Task task = new Task("Задача", "Описание", TaskStatus.NEW);
        int startSize = historyManager.getHistory().size();
        historyManager.add(task);
        historyManager.add(task);

        assertEquals(2, historyManager.getHistory().size() - startSize, "Размер истории не соответствует");
        assertEquals(historyManager.getHistory().getLast(), task, "Не сохранена последняя версия задачи");
        assertEquals(historyManager.getHistory().get(startSize + 1), task, "Не сохранена предыдущая версия задачи");
    }

    @Test
    public void sizeShouldBeLessThanMaxSize() {
        for (int i = 0; i < historyManager.MAX_HISTORY_SIZE + 1; i++) {
            historyManager.add(new Task("Задача", "Описание"));
        }

        assertEquals(historyManager.MAX_HISTORY_SIZE, historyManager.getHistory().size(), "Неккоректный размер истории");
    }
}