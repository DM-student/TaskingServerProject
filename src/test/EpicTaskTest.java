package test;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tasking.Tasks.EpicTask;
import tasking.Tasks.State;
import tasking.Tasks.SubTask;

import java.time.Duration;
import java.time.LocalDateTime;

class EpicTaskTest {
    static EpicTask epicTask;

    @BeforeAll
    public static void createTask()
    {
        epicTask = new EpicTask("Test");
        SubTask subTask1 = new SubTask("subtask1", "Something1", State.DONE);
        SubTask subTask2 = new SubTask("subtask2", "Something2", State.IN_PROGRESS);
        SubTask subTask3 = new SubTask("subtask3", "Something3", State.NEW);
        subTask1.setStartTime(LocalDateTime.of(2022, 10, 1, 12, 20));
        subTask3.setStartTime(LocalDateTime.of(2022, 10, 3, 13, 20));
        subTask1.setDuration(Duration.ofDays(1));
        subTask2.setDuration(Duration.ofDays(1));
        subTask3.setDuration(Duration.ofDays(1));
        epicTask.listSubTasks().add(subTask1);
        epicTask.listSubTasks().add(subTask2);
        epicTask.listSubTasks().add(subTask3);
        epicTask.connectAllSubTasks();
    }

    @Test
    public void testEpicState()
    {
        epicTask.listSubTasks().get(0).setState(State.NEW);
        epicTask.listSubTasks().get(1).setState(State.NEW);
        epicTask.listSubTasks().get(2).setState(State.NEW);
        assertEquals(State.NEW,epicTask.getState());

        epicTask.listSubTasks().get(0).setState(State.DONE);
        epicTask.listSubTasks().get(1).setState(State.IN_PROGRESS);
        epicTask.listSubTasks().get(2).setState(State.NEW);
        assertEquals(State.IN_PROGRESS,epicTask.getState());

        epicTask.listSubTasks().get(0).setState(State.DONE);
        epicTask.listSubTasks().get(1).setState(State.DONE);
        epicTask.listSubTasks().get(2).setState(State.DONE);
        assertEquals(State.DONE,epicTask.getState());

        createTask();
    }

    @Test
    public void testEpicTime()
    {
        assertEquals("2022-10-01T12:20", epicTask.getStartTime().toString());
        assertEquals("2022-10-04T13:20", epicTask.getEndTime().toString());
        assertEquals("PT97H", epicTask.getDuration().toString());
    }
}