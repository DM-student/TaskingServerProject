package Tasking;

public class SubTask extends Task
{
    public EpicTask Owner;
    public SubTask(String state, String name, String description)
    {
        this.state = state;
        this.name = name;
        this.description = description;
    }
    @Override
    public String toString()
    {
        return "SubTask{owner.id=" + Owner.id + ", state=\"" +  state + "\", name=\""
                + name + "\", description=\"" + description +"\"}";
    }
}
