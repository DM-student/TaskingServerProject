package tasking;

import java.util.*;
import tasking.Tasks.*;

public class InMemoryHistoryManager implements HistoryManager{
    private CustomLinkedList<Task> historyList = new CustomLinkedList<>();
    private Map<Integer, CustomLinkedList.Node> nodeMap = new HashMap<>();

    public InMemoryHistoryManager() {
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        CustomLinkedList.Node toAdd = historyList.getLastNode();
        for(int i = 0; i < historyList.getSize(); i++)
        {
            history.add((Task) toAdd.item);
            toAdd = toAdd.getLastNode();
        }
        return history;
    }

    @Override
    public void addToHistory(Task task)
    {
        if(nodeMap.containsKey(task.getId()))
        {
            removeFromHistory(task.getId());
        }
        nodeMap.put(task.getId(), historyList.add(task));
    }

    @Override
    public void removeFromHistory(int id)
    {
        historyList.remove(nodeMap.remove(id));
    }
}
