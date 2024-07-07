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

    private void adaptHistory() {
        if (history.size() > MAX_HISTORY_SIZE) {
            int startIndex = history.size() - MAX_HISTORY_SIZE;
            ArrayList<Task> mewHistory = new ArrayList<>(history.subList(startIndex, history.size()));

            history.clear();
            history.addAll(mewHistory);
        }
    }

    @Override
    public void add(Task task) {
        history.add(task);
        adaptHistory();
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }
}
