package domain;

import java.util.ArrayList;

public class Discipline {

    private String id;
    private String name;
    private ArrayList<Group> groups;

    public Discipline(String id, String name, ArrayList<Group> groups) {
        this.id = id;
        this.name = name;
        this.groups = groups;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return name;
    }
}
