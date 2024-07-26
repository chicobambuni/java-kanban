import manager.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    InMemoryTaskManager tm = new InMemoryTaskManager();

    @Test
    public void shouldThrowErrorIfTaskAlreadyExist() {
        Task task = new Task(1, "Задача", "Описание");
        Epic epic = new Epic(1, "Эпик", "Описание");
        Subtask subtask = new Subtask(1, "Подзадача", "Описание", epic);

        assertThrows(IllegalArgumentException.class, () -> {
            tm.addTask(task);
            tm.addTask(task);
            tm.addSubtask(subtask);
            tm.addSubtask(subtask);
            tm.addEpic(epic);
            tm.addEpic(epic);
        });
    }

    @Test
    public void taskShouldBeSameAfterAdd() {
        Task task = new Task("Задача", "Описание");
        Epic epic = new Epic("Эпик", "Описание");
        Subtask subtask = new Subtask("Подзадача", "Описание", epic);

        int taskId = tm.addTask(task);
        int subTaskId = tm.addSubtask(subtask);
        int epicId = tm.addEpic(epic);

        assertEquals(task, tm.getTaskById(taskId), "Задачи отличаются");
        assertEquals(subtask, tm.getSubtaskById(subTaskId), "Подзадачи отличаются");
        assertEquals(epic, tm.getEpicById(epicId), "Эпики отличаются");
    }

    @Test
    public void mustReturnSameTaskById() {
        Task task = new Task("Задача", "Описание");
        Epic epic = new Epic("Эпик", "Описание");
        Subtask subtask = new Subtask("Подзадача", "Описание", epic);

        int taskId = tm.addTask(task);
        int subTaskId = tm.addSubtask(subtask);
        int epicId = tm.addEpic(epic);

        assertNotNull(task, "Задача не найденна");
        assertEquals(task, tm.getTaskById(taskId), "Задачи не совпадают");
        assertNotNull(subtask, "Подзадача не найденна");
        assertEquals(subtask, tm.getSubtaskById(subTaskId), "Подзадачи не совпадают");
        assertNotNull(epic, "Эпик не найденн");
        assertEquals(epic, tm.getEpicById(epicId), "Эпики не совпадают");
    }

    @Test
    public void mustContainCorrectNumberOfTasks() {
        int tasksCurrentSize = tm.getAllTasks().size();
        int subtasksCurrentSize = tm.getAllSubtasks().size();
        int epicsCurrentSize = tm.getAllEpic().size();

        Task task = new Task("Задача", "Описание");
        Epic epic = new Epic("Эпик", "Описание");
        Subtask subtask = new Subtask("Подзадача", "Описание", epic);

        tm.addTask(task);
        tm.addSubtask(subtask);
        tm.addEpic(epic);

        assertEquals(tasksCurrentSize + 1, tm.getAllTasks().size(), "Неверное количество задач");
        assertEquals(subtasksCurrentSize + 1, tm.getAllSubtasks().size(), "Неверное количество подзадач");
        assertEquals(epicsCurrentSize + 1, tm.getAllEpic().size(), "Неверное количество эпиков");
    }

    @Test
    public void mustRemoveAll() {
        tm.removeAllTasks();
        tm.removeAllSubtasks();
        tm.removeAllEpics();

        assertEquals(tm.getAllTasks().size(), 0, "После удаления остались задачи");
        assertEquals(tm.getAllSubtasks().size(), 0, "После удаления остались подзадачи");
        assertEquals(tm.getAllEpic().size(), 0, "После удаления остались эпики");
    }

    @Test
    public void mustAddSubtaskToEpic() {
        Epic epic = new Epic("Эпик", "Описание");
        Subtask subtask = new Subtask("Подзадача", "Описание", epic);

        tm.addSubtask(subtask);
        assertTrue(epic.getSubtasks().contains(subtask), "Эпик не содержит подзадачу");
    }

    @Test
    public void mustUpdateEpicStatusAfterSubtaskAdded() {
        Epic epic = new Epic("Эпик", "Описание");
        Subtask subtask = new Subtask("Подзадача", "Описание", TaskStatus.IN_PROGRESS, epic);
        tm.addSubtask(subtask);

        assertNotEquals(epic.getStatus(), TaskStatus.NEW, "Эпик имеет некоректный статус");
    }

    @Test
    public void epicShouldNotContainSubtask() {
        Epic epic = new Epic("Эпик", "Описание");
        Subtask subtask = new Subtask("Подзадача", "Описание", TaskStatus.IN_PROGRESS, epic);

        tm.addEpic(epic);
        tm.addSubtask(subtask);
        tm.removeSubtaskById(subtask.getIndex());

        assertFalse(epic.getSubtasks().contains(subtask), "Эпик хранит в себе неактуальную подзадачу");
    }
}