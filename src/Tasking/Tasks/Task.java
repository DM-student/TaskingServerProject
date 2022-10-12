package Tasking.Tasks;

import Tasking.*;

public class Task {
    private String name;
    private String description;
    private State state;
    private Integer id;


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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Task(String name, String description, State state)
    {
        this.name = name;
        this.description = description;
        this.state = state;
    }
}
