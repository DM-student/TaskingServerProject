package tasking.managers;

import tasking.Tasks.*;

import java.util.List;

public interface TaskManager
{
    void addTask(Task task);
    void removeTask(int id);
    void removeAllTasks();
    void replaceTask(int id, Task task);
    Task getTask(int id);
    SubTask getSubTask(int id);
    EpicTask getEpicTask(int id);
    List<Task> getTasks();
    List<Task> getHistory();
    List<Task> getPrioritizedTasks();
}
