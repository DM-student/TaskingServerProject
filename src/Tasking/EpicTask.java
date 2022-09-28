package Tasking;

import java.util.ArrayList;
import java.util.List;

// Тут конечно много методов, которые не используются, но я их добавил для галочки,
// иначе класс будет не очень функциональным.
public class EpicTask extends Task
{
    public List<SubTask> subTasks = new ArrayList<>();

    public void fixSubTasks()
    {
        for(SubTask subTask : subTasks)
        {
            subTask.Owner = this;
            subTask.id = null;
        }
        updateState();
    }
    public void updateState()
    {
        boolean checkSuccess = true;
        for(SubTask subTask : subTasks)
        {
            if(!subTask.state.equals("NEW")) checkSuccess=false;
        }
        if (checkSuccess)
        {
            state = "NEW";
            return;
        }
        checkSuccess = true;
        for(SubTask subTask : subTasks)
        {
            if(!subTask.state.equals("DONE")) checkSuccess=false;
        }
        if (checkSuccess)
        {
            state = "DONE";
            return;
        }
        state = "IN_PROGRESS";
    }

    public EpicTask(String name, List<SubTask> subTasks)
    {
        this.name = name;
        this.subTasks = subTasks;
        fixSubTasks();
    }
    public EpicTask(){}

    @Override
    public String toString()
    {
        return "EpicTask{id=" + id + ", state=\"" +  state + "\", name=\""
                + name + "\", subTasks=" + subTasks +"}";
    }
}
