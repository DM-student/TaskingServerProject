package Tasking;

import Tasking.Tasks.EpicTask;
import Tasking.Tasks.SubTask;
import Tasking.Tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HistoryManager history = Managers.getDefaultHistory();

    private int lastId;

    @Override
    public void addTask(Task task) {
        task.setId(getNewId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void replaceTask(int id, Task task) {
        tasks.replace(id, task);
        task.setId(id);
    }

    @Override
    public Task getTask(int id) {
        if(tasks.get(id) instanceof Task && tasks.get(id) != null)
        {
            history.addToHistory(tasks.get(id));
            return tasks.get(id);
        }
        return null;
    }

    @Override
    public SubTask getSubTask(int id) {
        if(tasks.get(id) instanceof SubTask && tasks.get(id) != null)
        {
            history.addToHistory(tasks.get(id));
            return (SubTask) tasks.get(id);
        }
        return null;
    }

    @Override
    public EpicTask getEpicTask(int id) {
        if(tasks.get(id) instanceof EpicTask && tasks.get(id) != null)
        {
            history.addToHistory(tasks.get(id));
            return (EpicTask) tasks.get(id);
        }
        return null;
    }

    @Override
    public Map getTasks() {
        return (Map) tasks.clone();
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    public int getNewId()
    {
        lastId++;
        return lastId;
    }
}
