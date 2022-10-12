package Tasking.Tasks;

import Tasking.State;

import java.util.*;

public class EpicTask extends Task
{
    private List<SubTask> subTasks = new ArrayList<>();

    public List<SubTask> listSubTasks()
    {
        return subTasks;
    }

    public EpicTask(String name) {
        super(name, null, null);
    }

    public void connectAllSubTasks()
    {
        for(SubTask subTask : subTasks)
        {
            subTask.setOwner(this);
        }
    }

    @Override
    public State getState()
    {
        int NEWstates = 0;
        int DONEstates = 0;
        for (SubTask subTask : subTasks)
        {
            if (subTask.getState() == State.NEW || subTask.getState() == null) { NEWstates++; }
            else if (subTask.getState() == State.DONE) { DONEstates++; }
        }
        if(NEWstates == subTasks.size()) { return State.NEW; }
        if(DONEstates == subTasks.size()) { return State.DONE; }
        return State.IN_PROGRESS;
    }
    @Override
    public void setState(State state)
    {
        return;
    }
}
