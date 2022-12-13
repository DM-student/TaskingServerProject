package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasking.Tasks.EpicTask;
import tasking.Tasks.State;
import tasking.Tasks.SubTask;
import tasking.Tasks.Task;
import tasking.managers.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    InMemoryTaskManager manager;

    @BeforeEach
    void beforeEach()
    {
        // Логично, что мне нужно тестировать историю в полноценных рабочих условиях.
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
    void testGetHistory()
    {
        List<Task> history = manager.getHistory();

        assertEquals(5, history.size());
        assertEquals(1, history.get(0).getId());
        assertEquals(5, history.get(4).getId());
    }

    @Test
    void testAddToHistory()
    {
        manager.getDeveloperHistoryManager().addToHistory(manager.getTask(1));
        assertEquals(1, manager.getHistory().get(4).getId()); // Проверка добавления в историю и замену
        // в случае если этот элемент есть в истории.
    }
    @Test
    void testRemoveFromHistory()
    {
        manager.getDeveloperHistoryManager().removeFromHistory(1);
        assertNotEquals(1, manager.getHistory().get(0).getId());
    }
    @Test
    void testClear()
    {
        manager.getDeveloperHistoryManager().clear();
        assertEquals(0, manager.getHistory().size());
    }

    public HistoryManagerTest()
    {
        manager = new InMemoryTaskManager();
    }
}