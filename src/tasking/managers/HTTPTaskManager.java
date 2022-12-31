package tasking.managers;

import HTTPStuff.KVClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tasking.Tasks.EpicTask;
import tasking.Tasks.State;
import tasking.Tasks.SubTask;
import tasking.Tasks.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class HTTPTaskManager extends FileBackedTasksManager {


    public static HTTPTaskManager load(String URI, String key) throws IOException, InterruptedException {
        Gson gson = new Gson();
        HTTPTaskManager manager = new HTTPTaskManager(URI, key);

        String JsonTasks = manager.client.loadFromServer(key);
        String JsonHistory = manager.client.loadFromServer(key + "/history");
        if(JsonTasks.isEmpty())  {return manager; }
        JsonTaskElement[] elements = gson.fromJson(JsonTasks, JsonTaskElement[].class);

        Map<Integer, Task> tasks = new HashMap<>();
        Map<Integer, Integer> subTaskAndItsOwnerIds = new HashMap<>();

        for(JsonTaskElement element : elements)
        {
            Task task;

            if(element.type == SubTask.class.getSimpleName()) { task = new SubTask(null, null, null); }
            else if(element.type == EpicTask.class.getSimpleName()) { task = new EpicTask(null); }
            else { task = new Task(null, null, null); }

            task.setId(element.id);
            task.setName(element.name);
            task.setDescription(element.description);
            task.setState(element.state == null ? null : State.valueOf(element.state));
            task.setStartTime(element.startTime == null ? null : LocalDateTime.parse(element.startTime));
            task.setDuration(element.duration == null ? null : Duration.parse(element.duration));

            tasks.put(task.getId(), task);
            if(element.type == SubTask.class.getSimpleName())
            {
                subTaskAndItsOwnerIds.put(element.id, element.owner);
            }
        }

        for(HashMap.Entry<Integer, Integer> entry : subTaskAndItsOwnerIds.entrySet())
        {
            EpicTask epic = (EpicTask) tasks.get(entry.getValue());
            SubTask sub = (SubTask) tasks.get(entry.getKey());

            epic.listSubTasks().add(sub);
            sub.setOwner(epic);
        }
        manager.importTasksWithoutSave(tasks);

        for(int i : gson.fromJson(JsonHistory, int[].class))
        {
            manager.getDeveloperHistoryManager().addToHistory(tasks.get(i));
        }

        return manager;
    }

    private String key;
    private Gson gson;
    private KVClient client;

    public HTTPTaskManager(String URI, String key) throws IOException, InterruptedException {
        super(null);
        client = new KVClient(URI);
        this.key = key;
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void save()
    {
        List<JsonTaskElement> elements = new ArrayList<>();
        for(Task task : getTasks())
        {
            JsonTaskElement element = new JsonTaskElement();
            element.type = task.getClass().getSimpleName();
            element.id = task.getId();
            if(task.getName() != null) element.name = task.getName();
            if(task.getDescription() != null) element.description = task.getDescription();
            if(task.getState() != null) element.state = task.getState().toString();
            if(task.getStartTime() != null) element.startTime = task.getStartTime().toString();
            if(task.getDuration() != null) element.duration = task.getDuration().toString();
            if(task instanceof EpicTask)
            {
                element.subtasks = ((EpicTask) task).listSubTasks().stream().mapToInt(t -> t.getId()).toArray();
            }
            if(task instanceof SubTask)
            {
                EpicTask owner = ((SubTask) task).getOwner();
                if(owner !=null && owner.getId() != null) element.owner = ((SubTask) task).getOwner().getId();
            }
            elements.add(element);
        }
        try {
            client.uploadToServer(key, gson.toJson(elements));
            int[] history = getHistory().stream().mapToInt(t -> t.getId()).toArray();
            client.uploadToServer(key + "/history", gson.toJson(history));
        } catch (Throwable e) {
            System.out.println("Ошибка сохранения на сервер.");
            throw new RuntimeException(e);
        }
    }
}

class JsonTaskElement
{
    String type;
    int id;
    String name;
    String description;
    String state;
    String startTime;
    String duration;
    int[] subtasks;
    Integer owner;
}
