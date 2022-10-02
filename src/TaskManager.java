import Tasking.*;

import java.util.*;

public class TaskManager
{
    private Map<Integer, Task> tasks = new HashMap<>();
    private int lastId = 0;

    public void add(Task task)
    {
        lastId++;
        task.setId(lastId);
        tasks.put(lastId, task);
    }
    public void update(int id, Task task)
    {
        if(tasks.get(id) != null) { tasks.put(id, task); }
        task.setId(id);
    }
    public void remove(int id)
    {
        tasks.remove(id);
    }
    public void removeAll()
    {
        tasks.clear();
    }
    public Task get(int id)
    {
        return tasks.get(id);
    }
    public List<Task> getAll()
    {
        return new ArrayList(tasks.values());
    }
    public List<SubTask> getSubTasks(int id)
    {
        if(tasks.get(id) == null) { return null; }
        if(tasks.get(id).getClass() == EpicTask.class)
        {
            return ((EpicTask) tasks.get(id)).getSubTasks();
        }
        else { return null; }
    }
    public Task getTask(int id)
    {
        if(tasks.get(id) == null) { return null; }
        if(tasks.get(id).getClass() == Task.class)
        {
            return tasks.get(id);
        }
        else { return null; }
    }
    public EpicTask getEpicTask(int id)
    {
        if(tasks.get(id) == null) { return null; }
        if(tasks.get(id).getClass() == EpicTask.class)
        {
            return (EpicTask) tasks.get(id);
        }
        else { return null; }
    }
}
