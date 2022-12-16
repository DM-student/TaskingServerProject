package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasking.Tasks.State;
import tasking.managers.FileBackedTasksManager;
import tasking.managers.InMemoryTaskManager;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{
    @Test
    void testLoad()
    {
       FileBackedTasksManager testManager = FileBackedTasksManager.load(new File("test.txt"));
       assertEquals(5, testManager.getTasks().size());
        assertEquals(1, testManager.getTasks().get(0).getId());
        assertEquals(5, testManager.getTasks().get(4).getId());
       assertEquals(5, testManager.getHistory().size());
       assertEquals(1, testManager.getHistory().get(0).getId());
        assertEquals(5, testManager.getHistory().get(4).getId());
    }
    @Test
    void testEmptyLoad()
    {
        manager.removeAllTasks();
        FileBackedTasksManager testManager = FileBackedTasksManager.load(new File("test.txt"));
        assertEquals(0, testManager.getTasks().size());

    }
    @Test
    void testEmptyHistory()
    {
        manager.getDeveloperHistoryManager().clear();
        manager.save();
        FileBackedTasksManager testManager = FileBackedTasksManager.load(new File("test.txt"));

        assertEquals(5, testManager.getTasks().size()); // Убедимся, что задачи на месте.
        assertEquals(0, testManager.getHistory().size()); // Убедимся, что история пустая.
    }
    @Test
    void testEmptyEpic()
    {
        manager.getEpicTask(3).listSubTasks().clear();
        manager.removeTask(4);
        manager.removeTask(5);
        FileBackedTasksManager testManager = FileBackedTasksManager.load(new File("test.txt"));

        assertEquals(0, testManager.getEpicTask(3).listSubTasks().size()); // Убедимся, что после загрузки Эпик пуст
        assertEquals(State.NEW, testManager.getEpicTask(3).getState()); // Убедимся, что история пустая.
    }

    public FileBackedTasksManagerTest()
    {
        manager = FileBackedTasksManager.load(new File("test.txt"));
    }
}