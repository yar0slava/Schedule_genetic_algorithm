package csp_problem;

import domain.Discipline;
import domain.Group;
import domain.Lecturer;


import java.util.Objects;

public class Class {

    private Integer id;
    private Discipline discipline;
    private Group group;
    private Lecturer lecturer;

    public Class(Integer id, Discipline discipline, Group group, Lecturer lecturer) {
        this.id = id;
        this.discipline = discipline;
        this.group = group;
        this.lecturer = lecturer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public String getGroupName() {
        return group.getName();
    }

    public String getGroupId() {
        return group.getId();
    }

    public boolean getGroupIsLecture() {
        return group.getIsLecture();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class aClass = (Class) o;
        return Objects.equals(id, aClass.id) &&
                Objects.equals(group, aClass.group) &&
                Objects.equals(lecturer, aClass.lecturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, lecturer);
    }

//    @Override
//    public String toString() {
//        return " [" + discipline +
//                "|" + group +
//                "|" + lecturer +
//                ']';
//    }
}
