package genetic_algorithm.domain;

import java.util.ArrayList;

public class Discipline {
    private String id;
    private String name;
    private ArrayList<Group> groups;
    private ArrayList<Lecturer> lecturers;

    public Discipline(String id, String name, ArrayList<Group> groups, ArrayList<Lecturer> lecturers) {
        this.id = id;
        this.name = name;
        this.groups = groups;
        this.lecturers = lecturers;
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

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(ArrayList<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    @Override
    public String toString() {
        return name;
    }
}
