package Tasking;

import java.util.*;

public class TaskManager
{
    // Хранятся просто объекты, а не объекты класса Task потому, что в любом случае придётся
    // приведение совершать для получения доступа к полям и методам.
    private Map<Integer, Object> tasks = new HashMap<>();
    private int lastId = 0;

    public void addTask(Task task)
    {
        lastId++;
        task.id = lastId;
        tasks.put(lastId, task);
    }
    // Этот метод хоть и не используется, но он нужен по заданию.
    public void updateTask(int id, Object task)
    {
        if(tasks.get(id) != null) tasks.put(id, task);
    }
    public void removeTask(int id)
    {
        tasks.remove(id);
    }
    public void removeAllTasks()
    {
        tasks.clear();
    }
    public Object getTask(int id)
    {
        return tasks.get(id);
    }
    public Map getTasks()
    {
        return Map.copyOf(tasks);
    }
    // Аналогичная ситуация как и с updateTask.
    public List<SubTask> getSubTasks(int id)
    {
        return ((EpicTask) tasks.get(id)).subTasks;
    }
}
