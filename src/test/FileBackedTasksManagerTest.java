package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    public FileBackedTasksManagerTest()
    {
        manager = FileBackedTasksManager.load(new File("test.txt"));
    }
}