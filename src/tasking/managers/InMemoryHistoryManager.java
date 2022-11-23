package tasking.managers;

import java.util.*;

import tasking.CustomLinkedList;
import tasking.Tasks.*;

public class InMemoryHistoryManager implements HistoryManager{
    private CustomLinkedList<Task> historyList = new CustomLinkedList<>();
    private Map<Integer, CustomLinkedList.Node<Task>> nodeMap = new HashMap<>();

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        CustomLinkedList.Node<Task> toAdd = historyList.getFirstNode();
        for(int i = 0; i < historyList.getSize(); i++)
        {
            history.add(toAdd.item);
            toAdd = toAdd.getNextNode();
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
        if(nodeMap.containsKey(id)) {
            historyList.remove(nodeMap.remove(id));
        }
    }

    @Override
    public void clear()
    {
        for(Task task : getHistory())
        {
            removeFromHistory(task.getId());
        }
    }
}
