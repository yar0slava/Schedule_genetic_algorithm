package domain;

import java.util.Objects;

public class Group {

    String id; //КН_МП1 КН_МП2, ід кожної групи предмету для практики, для лекції ід=L
    Boolean isLecture;
    Integer amount;
    String name; //КН, спеціальність
    Lecturer lecturer;

    public Group(String id, String name, Integer amount, Boolean isLecture, Lecturer lecturer) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.isLecture = isLecture;
        this.lecturer = lecturer;
    }

    public Lecturer getLecturer()  {
        return lecturer;
    }

    public Boolean getIsLecture() {
        return isLecture;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id.equals(group.id) &&
                isLecture.equals(group.isLecture) &&
                name.equals(group.name) &&
                lecturer.equals(group.lecturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isLecture, name, lecturer);
    }

    @Override
    public String toString() {
        return "id: " + id;
    }
}
