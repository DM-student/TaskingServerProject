package tasking.managers;

public class ManagerSaveException extends Error {
    @Override
    public String toString()
    {
        return "saving error";
    }
}
