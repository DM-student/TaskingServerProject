package tasking.managers;

import tasking.Tasks.EpicTask;
import tasking.Tasks.SubTask;
import tasking.Tasks.Task;

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
        history.removeFromHistory(id);
    }

    @Override
    public void removeAllTasks() {
        // Тут нужна подобная реализация, чтобы при удалении всего, оно было удалено и из истории.
        List<Integer> toDelete = new ArrayList<>();
        for(Task task : tasks.values())
        {
            toDelete.add(task.getId());
        }
        for(int id : toDelete)
        {
            removeTask(id);
        }
    }

    @Override
    public void replaceTask(int id, Task task) {
        tasks.replace(id, task);
        task.setId(id);
    }

    @Override
    public Task getTask(int id) {
        if(tasks.get(id) != null && tasks.get(id).getClass() == Task.class)
        {
            history.addToHistory(tasks.get(id));
            return tasks.get(id);
        }
        return null;
    }

    @Override
    public SubTask getSubTask(int id) {
        if(tasks.get(id) != null && tasks.get(id).getClass() == SubTask.class)
        {
            history.addToHistory(tasks.get(id));
            return (SubTask) tasks.get(id);
        }
        return null;
    }

    @Override
    public EpicTask getEpicTask(int id) {
        if(tasks.get(id) != null && tasks.get(id).getClass() == EpicTask.class)
        {
            history.addToHistory(tasks.get(id));
            return (EpicTask) tasks.get(id);
        }
        return null;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<Task>(tasks.values());
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
