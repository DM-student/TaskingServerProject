import Tasking.Epic;
import Tasking.SubTask;
import Tasking.Task;
import Tasking.TaskManager;

public class Main {

    public static void main(String[] args)
    {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task();
        task1.name = "Сделать что-то очень глупое.";
        task1.description = "Опять отложить всё на потом.";
        task1.setState("DONE");

        Task task2 = new Task();
        task2.name = "Исправить косяк.";
        task2.description = "Доделать то что нужно.";
        task2.setState("IN_PROGRESS");

        Task task3 = new Task();
        task3.name = "Глупое задание.";
        task3.description = "Удалить себя.";
        task3.setState("IN_PROGRESS");

        Epic epic = new Epic();
        epic.name = "Выполнить задание";
        SubTask subTask1 = new SubTask();
        subTask1.name = "Приготовиться к проекту.";
        subTask1.description = "Собрать материал.";
        SubTask subTask2 = new SubTask();
        subTask2.name = "Приступить к разработке проекта.";
        subTask2.description = "Написать код.";
        subTask2.setState("IN_PROGRESS");
        SubTask subTask3 = new SubTask();
        subTask3.name = "Закончить проект.";
        subTask3.description = "Провести отладку приложения.";
        subTask1.setState("DONE");
        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);
        epic.addSubTask(subTask3);


        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.removeTaskById(3);
        taskManager.addTask(epic);

        Object[] t = taskManager.getTasks();

        for(int i = 0; i < t.length; i++) System.out.println(t[i]);


    }
}
