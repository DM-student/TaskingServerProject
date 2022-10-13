package tasking;

import tasking.Tasks.*;

import java.util.List;

public interface HistoryManager {
    List<Task> getHistory();
    void addToHistory(Task task);
}
