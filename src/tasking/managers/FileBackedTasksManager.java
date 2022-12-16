package tasking.managers;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.io.File;

import tasking.Tasks.*;
import tasking.*;


public class FileBackedTasksManager extends InMemoryTaskManager {

    public File saveFile = new File("save.txt"); // Тут дефолтный путь сохранения, который можно изменить.

    public static FileBackedTasksManager load(File file) { // Я считал, что лучше будет оставить
        Map<Integer, Task> tasks = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                stringBuilder.append(br.readLine());
                stringBuilder.append("\n");
            }
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        if(stringBuilder.length() == 0)
        {
            return new FileBackedTasksManager(file);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1); // Нужно чтобы на конце \n убрать.
        String inputString = stringBuilder.toString();

        // Отрицательное число заставляет метод вернуть строку, даже если после разделителя пустота.
        String[] dataString = inputString.split("\n\n", -1);

        String[] tasksStrings = dataString[0].split("\n");

        Map<Integer, Integer> subtasksAndEpics = new HashMap<>();

        if(!dataString[0].isEmpty()) {

            for (int i = 1; i < tasksStrings.length; i++) {
                String[] taskStrings = tasksStrings[i].split(",", -1);
                int id = Integer.parseInt(taskStrings[0]);
                String type = taskStrings[1];
                String name = taskStrings[2];
                State state = State.valueOf(taskStrings[3]);
                String description = taskStrings[4];
                LocalDateTime startTime = (taskStrings[5].equals("") ? null : LocalDateTime.parse(taskStrings[5]));
                Duration duration = (taskStrings[6].equals("") ? null : Duration.parse(taskStrings[6]));

                if (type.equals("Task")) {
                    Task newTask = new Task(name, description, state);
                    newTask.setId(id);
                    newTask.setStartTime(startTime);
                    newTask.setDuration(duration);
                    tasks.put(id, newTask);
                } else if (type.equals("EpicTask")) {
                    EpicTask newTask = new EpicTask(name);
                    newTask.setId(id);
                    newTask.setStartTime(startTime);
                    newTask.setDuration(duration);
                    tasks.put(id, newTask);
                } else if (type.equals("SubTask")) {
                    subtasksAndEpics.put(id, Integer.parseInt(taskStrings[7]));
                    Task newTask = new SubTask(name, description, state);
                    newTask.setId(id);
                    newTask.setStartTime(startTime);
                    newTask.setDuration(duration);
                    tasks.put(id, newTask);
                }
            }
        }

        for(HashMap.Entry<Integer, Integer> entry : subtasksAndEpics.entrySet())
        {
            ((EpicTask) tasks.get(entry.getValue())).listSubTasks().add((SubTask) tasks.get(entry.getKey()));
            ((EpicTask) tasks.get(entry.getValue())).connectAllSubTasks();
        }

        FileBackedTasksManager manager = new FileBackedTasksManager(file);

        manager.importTasksWithoutSave(tasks);



        if(dataString.length < 2 || dataString[1].isEmpty())
        {
            return manager; // Исправил критический баг, который я не заметил и не пойми как допустил.
        }
        String[] historyStrings = dataString[1].split(",");
        manager.getDeveloperHistoryManager().clear();
        for (int i = 0; i < historyStrings.length; i++) {
            manager.getDeveloperHistoryManager().addToHistory(tasks.get(Integer.parseInt(historyStrings[i])));
        }
        return manager;
    }

    public void save()  // Я решил сделать поле в которое можно указать путь,
                        // а не аргумент, потому что иначе для изменения пути сохранения мне нужно будет менять путь
                        // в каждом вызове метода сохранения.
    {
        try(FileWriter writer = new FileWriter(saveFile, false))
        {
            writer.write("id,type,name,status,description,startTime,duration,owner");

            for(Task task : super.getTasks())
            {
                // Если будет null, то это вызовет ошибку, а это значение может быть например у эпиков,
                // которые по хорошему не должны иметь описание
                writer.write("\n");
                writer.write(task.getId() == null ? "" : task.getId().toString());
                writer.write(",");
                String[] classStrings = task.getClass().getName().split("\\.");
                writer.write(classStrings[classStrings.length - 1]);
                writer.write(",");
                writer.write(task.getName() == null ? "" : task.getName());
                writer.write(",");
                writer.write(task.getState().name() == null ? "" : task.getState().name());
                writer.write(",");
                writer.write(task.getDescription() == null ? "" : task.getDescription());
                writer.write(",");
                writer.write(task.getStartTime() == null ? "" : task.getStartTime().toString());
                writer.write(",");
                writer.write(task.getDuration() == null ? "" : task.getDuration().toString());
                writer.write(",");
                if(task instanceof SubTask)
                {
                    if(((SubTask) task).getOwner().getId() != null) {
                    writer.write(((SubTask) task).getOwner().getId().toString());
                    }
                }

            }

            writer.write("\n");
            writer.write("\n"); // Два переноса нужно, чтобы отделить историю от объектов.
            StringBuilder str = new StringBuilder();

            for(Task task : super.getHistory())
            {
                str.append(task.getId());
                str.append(",");
            }
            if(str.lastIndexOf(",") != -1) { str.deleteCharAt(str.lastIndexOf(",")); }

            writer.write(str.toString());
        }
        catch (Throwable e)
        {
            throw new ManagerSaveException();
        }
    } // Генератор айди не нужно сохранять, так как он переписан так, что может сам выбрать не занятый айди.

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void replaceTask(int id, Task task) {
        super.replaceTask(id, task);
        save();
    }

    @Override
    public Task getTask(int id) {
        Task buffer = super.getTask(id);
        save();
        return buffer;
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask buffer = super.getSubTask(id);
        save();
        return buffer;
    }

    @Override
    public EpicTask getEpicTask(int id) {
        EpicTask buffer = super.getEpicTask(id);
        save();
        return buffer;
    }

    @Override
    public void importTasks(Map<Integer, Task> tasks)
    {
        super.importTasks(tasks);
        save(); // Тут сохранение нужно потому, что импортировать задачи могут и не только в случае
                // загрузки из файла. Да и при загрузке используется метод суперкласса.
    }

    public void importTasksWithoutSave(Map<Integer, Task> tasks)
    {
        super.importTasks(tasks);
    }

    public FileBackedTasksManager(File file) {
        saveFile = file;
    }
}