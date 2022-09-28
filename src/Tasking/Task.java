package Tasking;

public class Task
{
    public Integer id;
    public String state;
    public String name;
    public String description;

    public Task(String state, String name, String description)
    {
        this.state = state;
        this.name = name;
        this.description = description;
    }
    public Task(){}

    @Override
    public String toString()
    {
        return "Task{id=" + id + ", state=\"" +  state + "\", name=\""
                + name + "\", description=\"" + description +"\"}";
    }
}
