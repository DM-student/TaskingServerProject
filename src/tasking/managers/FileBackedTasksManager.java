package tasking.managers;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;

import tasking.Tasks.*;
import tasking.*;

public class FileBackedTasksManager extends InMemoryTaskManager {


    public void load() {
        Map<Integer, Task> tasks = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader("save.txt")) {
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                stringBuilder.append(br.readLine());
                stringBuilder.append("\n");
            }
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        String inputString = stringBuilder.toString();

        if(inputString.isEmpty())
        {
            return;
        }

        String tasksString = inputString.split("\n\n", - 1)[0];

        String[] tasksStrings = tasksString.split("\n");

        Map<Integer, Integer> subtasksAndEpics = new HashMap<>();

        if(!tasksString.isEmpty()) {

            for (int i = 1; i < tasksStrings.length; i++) {
                String[] taskStrings = tasksStrings[i].split(",", -1);
                int id = Integer.parseInt(taskStrings[0]);
                String type = taskStrings[1];
                String name = taskStrings[2];
                State state = State.valueOf(taskStrings[3]);
                String description = taskStrings[4];

                if (type.equals("Task")) {
                    Task newTask = new Task(name, description, state);
                    newTask.setId(id);
                    tasks.put(id, newTask);
                } else if (type.equals("EpicTask")) {
                    EpicTask newTask = new EpicTask(name);
                    newTask.setId(id);
                    tasks.put(id, newTask);
                } else if (type.equals("SubTask")) {
                    subtasksAndEpics.put(id, Integer.parseInt(taskStrings[5]));
                    Task newTask = new SubTask(name, description, state);
                    newTask.setId(id);
                    tasks.put(id, newTask);
                }
            }
        }

        for(HashMap.Entry<Integer, Integer> entry : subtasksAndEpics.entrySet())
        {
            ((EpicTask) tasks.get(entry.getValue())).listSubTasks().add((SubTask) tasks.get(entry.getKey()));
            ((EpicTask) tasks.get(entry.getValue())).connectAllSubTasks();
        }
        super.importTasks(tasks);

        try {  //
            String historyString = inputString.split("\n\n", -1)[1];

            String[] historyStrings = historyString.split(",");
            getDeveloperHistoryManager().clear();
            for (int i = 0; i < historyStrings.length; i++) {
                getDeveloperHistoryManager().addToHistory(tasks.get(Integer.parseInt(historyStrings[i])));
            }
        } catch (Throwable e) { }
    }

    public void save()
    {
        try(FileWriter writer = new FileWriter("save.txt", false))
        {
            writer.write("id,type,name,status,description,owner");

            for(Task task : super.getTasks())
            {
                writer.write("\n");
                writer.write(UsefulStuff.antiNullPointerString(task.getId().toString()));
                writer.write(",");
                String[] classStrings = task.getClass().getName().split("\\.");
                writer.write(UsefulStuff.antiNullPointerString(classStrings[classStrings.length - 1]));
                writer.write(",");
                writer.write(UsefulStuff.antiNullPointerString(task.getName()));
                writer.write(",");
                writer.write(UsefulStuff.antiNullPointerString(task.getState().name()));
                writer.write(",");
                writer.write(UsefulStuff.antiNullPointerString(task.getDescription()));
                writer.write(",");
                if(task.getClass() == SubTask.class)
                {
                    if(((SubTask) task).getOwner().getId() != null) {
                    writer.write(((SubTask) task).getOwner().getId().toString());
                    }
                }
            }

            writer.write("\n");
            writer.write("\n");
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
    }

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
    public List<Task> getTasks() {
        List<Task> buffer = super.getTasks();
        save();
        return buffer;
    }

    @Override
    public void importTasks(Map<Integer, Task> tasks)
    {
        super.importTasks(tasks);
        save();
    }
}