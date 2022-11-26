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
    // Я не вижу смысла создавать аргумент для этого метода,
    // так как всё равно предрешено название и путь файла для сохранения и загрузки.
    {
        FileBackedTasksManager newManager = new FileBackedTasksManager();
        newManager.load(file);
        return newManager;
    }
}
