package tasking.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{
    @BeforeEach
    @Override
    void beforeEach() {
        if (manager == null) {manager = FileBackedTasksManager.load(new File("test.txt")); }
        super.beforeEach();
    }
    @Test
    @Override
    void testPriorityList() {
        super.testPriorityList();
    }
    @Test
    @Override
    void testEpic()
    {
        super.testEpic();
    }

    @Test
    @Override
    void testAddTask()
    {
        super.testAddTask();
    }
    @Test
    @Override
    void testGetMethods()
    {
        super.testGetMethods();
    }
    @Test
    @Override
    void testRemove()
    {
        super.testRemove();
    }
    @Test
    @Override
    void testGetNewId()
    {
        super.testGetNewId();
    }
    @Test
    @Override
    void testReplace()
    {
        super.testReplace();
    }

    @Test
    void testLoad()
    {
       FileBackedTasksManager testManager = FileBackedTasksManager.load(new File("test.txt"));
       assertEquals(5, testManager.getTasks().size());
    }
    @Test
    void testEmptyLoad()
    {
        manager.removeAllTasks();
        FileBackedTasksManager testManager = FileBackedTasksManager.load(new File("test.txt"));
        assertEquals(0, testManager.getTasks().size());
    }
}