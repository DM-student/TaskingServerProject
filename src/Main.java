import Tasking.*;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("IN_PROGRESS", "Допилить проект.", "Делай-делай ты проект, дурной.");
        Task task2 = new Task("DONE", "Тратить время на глупые вещи.", "Как всегда в прочем.");
        Collection<SubTask> subTasks1 = List.of(
                new SubTask("DONE", "Подготовить лист", "Сделать первую часть."),
                new SubTask("IN_PROGRESS", "Сделать часть кода", "Сделать вторую часть."),
                new SubTask("NEW", "Сделать часть кода", "Сделать третью часть.")
                );
        Collection<SubTask> subTasks2 = List.of(
                new SubTask("NEW", "Неполное задание", "Сделать NEW подзадачу."),
                new SubTask("NEW", "Пустое задание", "Сделать NEW подзадачу."),
                new SubTask("NEW", "Никакое задание", "Сделать NEW подзадачу.")
        );
        EpicTask epic1 = new EpicTask("Создать первый эпик.", subTasks1);
        EpicTask epic2 = new EpicTask("Создать эпик под  удаление.", subTasks2);

        taskManager.add(task1);
        taskManager.add(task2);
        taskManager.add(epic1);
        taskManager.add(epic2);

        printTasks(taskManager.getAll());

        taskManager.remove(4);

        taskManager.get(1).setState("DONE");
        taskManager.getEpicTask(3).getSubTask(2).setState("DONE");
        taskManager.getEpicTask(3).getSubTask(3).setState("DONE");

        printTasks(taskManager.getAll());


        taskManager.removeAll();

        printTasks(taskManager.getAll());
    }

    static void printTasks(List tasks)
    {
        System.out.println("Все задачи в менеджере задач:");
        for (Object task : tasks) System.out.println(task);
    }
}
