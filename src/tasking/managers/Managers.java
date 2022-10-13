package tasking.managers;

import tasking.HistoryManager;
import tasking.InMemoryHistoryManager;
import tasking.InMemoryTaskManager;
import tasking.TaskManager;

public class Managers {
    public static TaskManager getDefault()
    {
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory()
    {
        return new InMemoryHistoryManager();
    }
}
