package tasking;

public class UsefulStuff {
    public static String returnEmptyIfNull(String target)
    {
        if(target == null)
        {
            return "";
        }
        return target;
    }
}
