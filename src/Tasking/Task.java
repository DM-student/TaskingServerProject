package Tasking;

public class Task {

    public String name;
    public String description;
    protected Integer id;
    protected String state;

    public int getId()
    {
        return id;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        if(state == "NEW" || state == "IN_PROGRESS" || state == "DONE")
        {
            this.state = state;
        }
    }

    public Task()
    {
        state = "NEW";
    }

    @Override
    public String toString()
    {
        return "task={id=" + id + ", name=\"" + name + "\", description=\""
                + description + "\", state=\"" + state + "\"}";
    }
}
