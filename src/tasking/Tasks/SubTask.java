package tasking.Tasks;

public class SubTask extends Task
{
    private EpicTask owner;

    public SubTask(String name, String description, State state)
    {
        super(name, description, state);
    }

    public EpicTask getOwner() {
        return owner;
    }

    public void setOwner(EpicTask owner) {
        this.owner = owner;
    }
}
