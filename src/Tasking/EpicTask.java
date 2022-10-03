package Tasking;

import java.util.*;

public class EpicTask extends Task
{
    private int lastSubTaskID;
    private Map<Integer, SubTask> subTasks = new HashMap<>();
    public SubTask getSubTask(int id)
    {
        return subTasks.get(id);
    }
    public List<SubTask> getSubTasks()
    {
        return new ArrayList(subTasks.values());
    }
    public void addSubTask(SubTask subTask)
    {
        lastSubTaskID++;
        subTasks.put(lastSubTaskID, subTask);
        subTask.setOwner(this);
        subTask.setId(lastSubTaskID);
        updateState();
    }
    public void updateSubTask(int id, SubTask subTask)
    {
        if(subTasks.get(id) == null) {return;}
        subTasks.put(id, subTask);
        subTask.setOwner(this);
        subTask.setId(id);
        updateState();
    }

    public void removeSubTask(int id)
    {
        subTasks.remove(id);
        updateState();
    }
    public void updateState()
    {
        boolean checkNewStatus = true;
        for(SubTask subTask : subTasks.values())
        {
            if(!subTask.getState().equals("NEW")) { checkNewStatus=false; }
        }
        if (checkNewStatus)
        {
            setState("NEW");
            return;
        }
        boolean checkDoneStatus = true;
        for(SubTask subTask : subTasks.values())
        {
            if(!subTask.getState().equals("DONE")) { checkDoneStatus=false; }
        }
        if (checkDoneStatus)
        {
            setState("DONE");
            return;
        }
        setState("IN_PROGRESS");
    }

    public EpicTask(String name, Collection<SubTask> subTasks)
    {
        this.setName(name);
        for(SubTask subTask : subTasks)
        {
            addSubTask(subTask);
        }
        updateState();
    }

    @Override
    public String toString()
    {
        return "EpicTask{id=" + getId() + ", state=\"" + getState() + "\", name=\""
                + getName() + "\", subTasks=" + subTasks.values() +"}";
    }
}
