package Tasking;

import Tasking.Tasks.*;

import java.util.List;

public interface HistoryManager {
    List<Task> getHistory();
    void addToHistory(Task task);
}
