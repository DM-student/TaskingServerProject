package test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasking.Tasks.*;
import tasking.managers.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    T manager;

    // Просто обнуление менеджера и подготовка его к новому тесту.

    @BeforeEach
    void beforeEach()
    {
        manager.removeAllTasks();

        Task task1 = new Task("A", "AA", State.IN_PROGRESS);
        task1.setStartTime(LocalDateTime.of(2022, 10, 3, 13, 20));
        task1.setDuration(Duration.ofHours(5));
        Task task2 = new Task("B", "BB", State.DONE);
        task2.setStartTime(LocalDateTime.of(2022, 10, 3, 14, 20));
        task2.setDuration(Duration.ofHours(6));
        SubTask subTask1 = new SubTask("C", "CC", State.NEW);
        subTask1.setStartTime(LocalDateTime.of(2022, 10, 1, 14, 30));
        subTask1.setDuration(Duration.ofHours(6));
        SubTask subTask2 = new SubTask("D", "DD", State.NEW);
        subTask2.setStartTime(LocalDateTime.of(2022, 11, 3, 14, 20));
        subTask2.setDuration(Duration.ofHours(7));
        EpicTask epicTask = new EpicTask("E");
        epicTask.listSubTasks().add(subTask1);
        epicTask.listSubTasks().add(subTask2);
        epicTask.connectAllSubTasks();

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(epicTask);
        manager.addTask(subTask1);
        manager.addTask(subTask2);
        manager.getTask(1);
        manager.getTask(2);
        manager.getEpicTask(3);
        manager.getSubTask(4);
        manager.getSubTask(5);
    }

    @Test
    void testPriorityList()
    {
        List<Task> testList = manager.getPrioritizedTasks();
        assertEquals(3, manager.getPrioritizedTasks().get(0).getId());
        assertEquals(4, manager.getPrioritizedTasks().get(1).getId());
        assertEquals(1, manager.getPrioritizedTasks().get(2).getId());
        assertEquals(2, manager.getPrioritizedTasks().get(3).getId());
        assertEquals(5, manager.getPrioritizedTasks().get(4).getId());
    }

    @Test
    void testEpic()
    {
        assertEquals(3, manager.getSubTask(4).getOwner().getId());
        assertEquals(3, manager.getSubTask(5).getOwner().getId());

        assertEquals(State.NEW, manager.getEpicTask(3).getState());
    }

    @Test
    void testAddTask() // С пустым менеджером ничего не изменится.
    {
        Task newTask = new Task("test", "test-test", State.NEW);
        manager.addTask(newTask);
        assertEquals(newTask, manager.getTask(6));
    }
    @Test
    void testGetMethods()
    {
        assertEquals("A", manager.getTask(1).getName());
        assertEquals("E", manager.getEpicTask(3).getName());
        assertEquals("C", manager.getSubTask(4).getName());
        manager.removeAllTasks();
        assertNull(manager.getTask(1));
        assertNull(manager.getEpicTask(3));
        assertNull(manager.getSubTask(4));
    }
    @Test
    void testRemove()
    {
        manager.removeTask(1);
        assertEquals(4, manager.getTasks().size());
        manager.removeTask(1); // Тут идёт попытка удалить таск, который уже удалён.
        assertEquals(4, manager.getTasks().size()); // По итогу ничего не должно изменится.
    }
    @Test
    void testReplace()
    {
        manager.replaceTask(1, new Task("Test", "Test", State.NEW));
        assertEquals("Test", manager.getTask(1).getName());
        manager.replaceTask(10, new Task("Test", "Test", State.NEW));
        assertNull(manager.getTask(10));
    }
    @Test
    void testRemoveAll()
    {
        manager.removeAllTasks();
        assertEquals(0, manager.getTasks().size());
    }
    // Менеджер истории будет проверен отдельно.
}