import java.util.ArrayList;
import java.util.Collection;

public class InMemoryHistoryManager implements HistoryManager {
    private ArrayList<Task> history = new ArrayList<>();

    private void adaptHistory() {
        int MAX_HISTORY_SIZE = 10;

        if (history.size() > MAX_HISTORY_SIZE) {
            int startIndex = history.size() - MAX_HISTORY_SIZE;
            history = new ArrayList<>(history.subList(startIndex, history.size()));
        }
    }

    @Override
    public void add(Task task) {
        history.add(task);
        adaptHistory();
    }

    public <T extends Task> void add(Collection<T> tasks) {
        history.addAll(tasks);
        adaptHistory();
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }
}
