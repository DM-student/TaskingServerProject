package tasking.Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
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
    public LocalDateTime getStartTime()
    {
        LocalDateTime earliestTime = null;
        for (SubTask subTask : subTasks)
        {
            if(subTask.getStartTime() != null) if (earliestTime == null || earliestTime.isAfter(subTask.getStartTime()))
            {

                earliestTime = subTask.getStartTime();
            }
        }
        return earliestTime;
    }

    @Override
    public LocalDateTime getEndTime()
    {
        LocalDateTime latestTime = null;
        for (SubTask subTask : subTasks)
        {

            if (subTask.getEndTime() != null) if(latestTime == null || latestTime.isBefore(subTask.getEndTime()))
            {
                latestTime = subTask.getEndTime();
            }
        }
        return latestTime;
    }

    private SubTask latestSubTask()
    {
        LocalDateTime latestTime = null;
        SubTask latestSubTask = null;
        for (SubTask subTask : subTasks)
        {
           if(subTask.getEndTime() != null) if(latestTime == null || latestTime.isBefore(subTask.getEndTime()))
            {
                latestTime = subTask.getEndTime();
                latestSubTask = subTask;
            }
        }
        return latestSubTask;
    }

    @Override
    public Duration getDuration() {
        if(latestSubTask() == null) { return null; }

        SubTask latestSubTask = latestSubTask();
        LocalDateTime start = getStartTime();
        LocalDateTime end = latestSubTask.getEndTime();
        Duration lastDuration = latestSubTask.getDuration();

        if(start == null || end == null || lastDuration == null) { return null; }
        return Duration.between(start, end).plus(lastDuration);
    }
}
