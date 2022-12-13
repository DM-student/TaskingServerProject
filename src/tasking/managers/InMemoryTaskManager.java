package tasking.managers;

import tasking.Tasks.EpicTask;
import tasking.Tasks.SubTask;
import tasking.Tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private HistoryManager history = Managers.getDefaultHistory();


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
        history.clear();
        tasks.clear();
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

    public HistoryManager getDeveloperHistoryManager()
    {
        return history;
    }

    private int getNewId()
    {
        int id = 1;
        while (tasks.containsKey(id)) {
            id++;
        }
        return id;
    }

    public void importTasks(Map<Integer, Task> tasks)
    {
        this.tasks = tasks;
    }

    @Override
    public List<Task> getPrioritizedTasks()
    {
        List<Task> tasksList = getTasks();
        Collections.sort(tasksList, (task1, task2) -> {
            if(task1.getStartTime() == null && task2.getStartTime() == null)
            {
                return 0;
            }
            else if(task1.getStartTime() == null)
            {
                return -1;
            }
            else if (task2.getStartTime() == null)
            {
                return 1;
            }
            return task1.getStartTime().compareTo(task2.getStartTime());
        });
        return tasksList;
    }
}
