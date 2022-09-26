package Tasking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task
{
    protected List<SubTask> subTasks = new ArrayList<>();

    public SubTask[] getSubTasks() {return (SubTask[]) subTasks.toArray();}
    public SubTask getSubTask(int index) {return subTasks.get(index);}
    public void removeSubTask(int index) {subTasks.remove(index);}
    public void clearSubTasks(int index) {subTasks.clear();}
    public void addSubTask(SubTask subTask)
    {
        subTasks.add(subTask);
        subTask.owner = this;
        calculateState();
    }
    @Override
    public void setState(String state)
    {
        throw new UncheckedIOException(new IOException("Unable to setState of an epic."));
    }
    @Override
    public String getState()
    {
        return state;
    }
    public void calculateState()
    {
        boolean ifTrue = true;
        for (SubTask subTask : subTasks) if(!subTask.state.equals("NEW")) ifTrue = false;
        if(ifTrue)
        {
            state = "NEW";
            return;
        }
        ifTrue = true;
        for (SubTask subTask : subTasks) if(!subTask.state.equals("DONE")) ifTrue = false;
        if(ifTrue)
        {
            state = "DONE";
            return;
        }
        state = "IN_PROGRESS";
    }
    public Epic() {
        super();
    }
    @Override
    public String toString()
    {
        return "epic={id=" + id + ", name=\"" + name + "\", subTasks="
                + subTasks + ", state=\"" + state + "\"}";
    }
}
