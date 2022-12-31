package tasking.managers;

import java.io.File;

public class Managers {
    public static TaskManager getDefault()
    {
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory()
    {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager loadFromFile(File file)
    {
        return FileBackedTasksManager.load(file);
    }

    public static HTTPTaskManager loadFromKVServer(String URI, String key)
    {
        try
        {
            return new HTTPTaskManager(URI, key);
        }
        catch (Throwable e)
        {
            return null;
        }
    }
}
