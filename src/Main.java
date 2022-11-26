import tasking.Tasks.EpicTask;
import tasking.Tasks.State;
import tasking.Tasks.SubTask;
import tasking.Tasks.Task;
import tasking.managers.FileBackedTasksManager;
import tasking.managers.Managers;
import tasking.managers.TaskManager;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        TaskManager taskManager = Managers.loadFromFile(new File("save.txt"));

        for(int i = 0; i < 8; i++)
        {
            taskManager.addTask(new Task("Простая задача " + i, "Описание простой задачи" + i, State.NEW));
        }

        EpicTask epic1 = new EpicTask("Эпик 1");

        EpicTask epic2 = new EpicTask("Эпик 2");

        SubTask subTask1 = new SubTask("Типо подзадача 1", "Что-то в подзадаче", State.NEW);
        SubTask subTask2 = new SubTask("Типо подзадача 2", "Что-то в подзадаче", State.NEW);
        SubTask subTask3 = new SubTask("Типо подзадача 3", "Что-то в подзадаче", State.NEW);

        SubTask subTask4 = new SubTask("Типо подзадача 4", "Что-то в подзадаче", State.NEW);
        SubTask subTask5 = new SubTask("Типо подзадача 1", "Что-то в подзадаче", State.DONE);

        epic1.listSubTasks().add(subTask1);
        epic1.listSubTasks().add(subTask2);
        epic1.listSubTasks().add(subTask3);
        epic1.connectAllSubTasks();

        epic2.listSubTasks().add(subTask4);
        epic2.listSubTasks().add(subTask5);
        epic2.connectAllSubTasks();

        taskManager.addTask(subTask1);
        taskManager.addTask(subTask2);
        taskManager.addTask(subTask3);
        taskManager.addTask(subTask4);
        taskManager.addTask(subTask5);

        taskManager.addTask(epic1);
        taskManager.addTask(epic2);

        for(int i = 1; i < 21; i++)
        {
            taskManager.getSubTask(i);
        }
        for(int i = 1; i < 21; i++)
        {
            taskManager.getEpicTask(i);
        }
        for(int i = 1; i < 21; i++)
        {
            taskManager.getTask(i);
        }

        taskManager.removeTask(2);

        List<Task> history = taskManager.getHistory();

        State checkState1 = epic1.getState();
        State checkState2 = epic2.getState();
        State checkState3 = (new EpicTask("Тестовый ЕпикТаск.")).getState();
    }
}
