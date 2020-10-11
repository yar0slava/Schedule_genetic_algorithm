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

    @Override
    public String toString() {
        return name;
    }
}
