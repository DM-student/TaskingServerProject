package Tasking;

public class Task
{
    private Integer id;
    private String state;
    private String name;
    private String description;



    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Task(String state, String name, String description)
    {
        this.state = state;
        this.name = name;
        this.description = description;
    }

    public Task() {}

    @Override
    public String toString()
    {
        return "Task{id=" + id + ", state=\"" +  state + "\", name=\""
                + name + "\", description=\"" + description +"\"}";
    }
}
