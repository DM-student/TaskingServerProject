package Tasking;

public class SubTask extends Task
{
    private EpicTask owner;

    public SubTask(String state, String name, String description)
    {
        super(state, name, description);
    }
    @Override
    public String toString()
    {
        return "SubTask{id=" + getId() + ", state=\"" + getState() + "\", name=\""
                + getName() + "\", description=\"" + getDescription() +"\"}";
    }
    @Override
    public void setState(String state)
    {
        super.setState(state);
        if(owner != null) { owner.updateState(); }
    }
    public EpicTask getOwner() {
        return owner;
    }
    public void setOwner(EpicTask owner) {
        this.owner = owner;
        if(owner != null) { owner.updateState(); }
    }

}
