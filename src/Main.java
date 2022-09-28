import Tasking.*;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("IN_PROGRESS", "Допилить проект.", "Делай-делай ты проект, дурной.");
        Task task2 = new Task("DONE", "Тратить время на глупые вещи.", "Как всегда в прочем.");
        List<SubTask> subTasks1 = List.of(
                new SubTask("DONE", "Подготовить лист", "Сделать первую часть."),
                new SubTask("IN_PROGRESS", "Сделать часть кода", "Сделать вторую часть."),
                new SubTask("NEW", "Сделать часть кода", "Сделать третью часть.")
                );
        List<SubTask> subTasks2 = List.of(
                new SubTask("NEW", "Неполное задание", "Сделать NEW подзадачу."),
                new SubTask("NEW", "Пустое задание", "Сделать NEW подзадачу."),
                new SubTask("NEW", "Никакое задание", "Сделать NEW подзадачу.")
        );
        EpicTask epic1 = new EpicTask("Создать первый эпик.", subTasks1);
        EpicTask epic2 = new EpicTask("Создать эпик под  удаление.", subTasks2);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(epic1);
        taskManager.addTask(epic2);

        printTasks(taskManager.getTasks());

        taskManager.removeTask(4);
        // Вместо использования отдельного метода для обновления, легче просто
        // получить нужный ссылочный объект и изменить его.
        ((Task) taskManager.getTask(1)).state = "DONE";

        printTasks(taskManager.getTasks());

        taskManager.removeAllTasks();

        printTasks(taskManager.getTasks());
    }

    static void printTasks(Map tasks)
    {
        System.out.println("Все задачи в менеджере задач:");
        for (Object t : tasks.values()) System.out.println(t);
    }
}
