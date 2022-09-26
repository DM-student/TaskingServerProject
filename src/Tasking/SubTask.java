package Tasking;
import java.io.IOException;
import java.io.UncheckedIOException;

public class SubTask extends Task
{
    protected Epic owner;

    public  Epic getOwner()
    {
        return owner;
    }

    @Override
    public void setState(String state)
    {
        super.setState(state);
        if(owner != null) owner.calculateState();
    }
    @Override
    public int getId()
    {
        throw new UncheckedIOException(new IOException("Unable to getId of SubTask."));
    }

    public SubTask() {
        super();
    }

    @Override
    public String toString()
    {
        return "subTask={name=\"" + name + "\", description=\""
                + description + "\", state=\"" + state + "\"}";
    }
}
