package HTTPStuff;

import tasking.Tasks.EpicTask;
import tasking.Tasks.State;
import tasking.Tasks.SubTask;
import tasking.Tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public class JsonTaskElement {
    public String type;
    public int id;
    public String name;
    public String description;
    public String state;
    public String startTime;
    public String duration;
    public int[] subtasks;
    public Integer owner;

    public static JsonTaskElement taskToElement(Task task)
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
        return element;
    }

    public static Task elementToTask(JsonTaskElement element)
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

        return task;
    }
}
