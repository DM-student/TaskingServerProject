package tasking.managers;

import HTTPStuff.JsonTaskElement;
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
            Task task = JsonTaskElement.elementToTask(element);

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
            elements.add(JsonTaskElement.taskToElement(task));
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

