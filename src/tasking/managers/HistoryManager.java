package tasking.managers;

import tasking.Tasks.*;

import java.util.List;

public interface HistoryManager {
    List<Task> getHistory();
    void addToHistory(Task task);
    void removeFromHistory(int id);

    void clear();
}
