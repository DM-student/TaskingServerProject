package tasking.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{
    @BeforeEach
    @Override
    void beforeEach() {
        if (manager == null) {manager = new InMemoryTaskManager(); }
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
}