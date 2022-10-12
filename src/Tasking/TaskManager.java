package Tasking;

import Tasking.Tasks.*;

import java.util.List;
import java.util.Map;

public interface TaskManager
{
    void addTask(Task task);
    void removeTask(int id);
    void removeAllTasks();
    void replaceTask(int id, Task task);
    Task getTask(int id);
    SubTask getSubTask(int id);
    EpicTask getEpicTask(int id);
    Map getTasks();
    List<Task> getHistory();
}
