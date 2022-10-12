package Tasking;

import java.util.*;
import Tasking.Tasks.*;

public class InMemoryHistoryManager implements HistoryManager{
    private ArrayList<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return (List) history.clone();
    }

    @Override
    public void addToHistory(Task task)
    {
        history.add(0, task);
        if(history.size() > 10) { history.remove(10); }
    }
}
