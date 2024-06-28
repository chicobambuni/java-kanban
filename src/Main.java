public class Main {
    public static void printAllTasks(TaskManager taskManager) {
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }
        for (Epic epic : taskManager.getAllEpic()) {
            System.out.println(epic);
        }
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("task1", "Описание");
        Task task2 = new Task("task2", "Описание");

        Epic epic1 = new Epic("epic1", "Описание");
        Subtask subtask1 = new Subtask("subtask1", "Описание", epic1);
        Subtask subtask2 = new Subtask("subtask2", "Описание", epic1);
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);

        Epic epic2 = new Epic("epic2", "Описание");
        Subtask subtask3 = new Subtask("subtask3", "Описание", epic2);
        epic2.addSubtask(subtask3);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        printAllTasks(taskManager);

        task1.setStatus(TaskStatus.IN_PROGRESS);
        task1.setStatus(TaskStatus.DONE);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask1.setStatus(TaskStatus.DONE);
        subtask3.setStatus(TaskStatus.DONE);
        printAllTasks(taskManager);

        taskManager.removeTaskById(task1.getIndex());
        taskManager.removeEpicById(epic1.getIndex());
        printAllTasks(taskManager);
    }
}
