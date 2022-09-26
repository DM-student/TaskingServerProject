package Tasking;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.UncheckedIOException;

public class TaskManager
{
    private int lastId = 0;
    private List<Object> tasks = new ArrayList<>();
    public Object[] getTasks()
    {
            return tasks.toArray();
    }

    public void addTask(Object task)
    {
        if(task.getClass() != Task.class && task.getClass() != Epic.class)
        {
            throw new UncheckedIOException(new IOException("Object's class not supported. Expected Epic or Task, provided: "
                    + task.getClass().getName()));
        }
        Task thatTask = (Task) task;
        lastId++;
        thatTask.id = lastId;
        tasks.add(task);
    }
    public void removeTaskById(int id)
    {
        for(int i = 0; i < tasks.size(); i++)
        {
            Task task = (Task) tasks.get(i);
            if(task.id == id) tasks.remove(i);
        }
    }
    public void removeTaskByIndex(int index)
    {
        tasks.remove(index);
    }
}
