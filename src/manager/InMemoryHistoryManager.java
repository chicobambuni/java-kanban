package manager;

import common.HistoryManager;
import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    public final int MAX_HISTORY_SIZE = 10;
    private ArrayList<Task> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (history.size() >= MAX_HISTORY_SIZE) {
            for (int i = 0; i < MAX_HISTORY_SIZE - 1; i++) {
                history.set(i, history.get(i + 1));
            }

            history.set(MAX_HISTORY_SIZE - 1, task);
        } else {
            history.add(task);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }
}
