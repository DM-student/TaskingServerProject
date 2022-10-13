package tasking;

import java.util.*;
import tasking.Tasks.*;

public class InMemoryHistoryManager implements HistoryManager{
    private ArrayList<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void addToHistory(Task task)
    {
        history.add(0, task);
        if(history.size() > 10) { history.remove(10); }
        // Тут было замечание по поводу того, что остётся 9 элементов после удаления, но это не так.
        // Удаляется 11-тый элемент с индексом 10, ведь первый индекс - это 0.
    }
}
