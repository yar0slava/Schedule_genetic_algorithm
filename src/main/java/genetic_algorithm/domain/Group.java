package genetic_algorithm.domain;

public class Group {

    String id; //КН_МП1 КН_МП2, ід кожної групи предмету для практики, для лекції ід=L
    Boolean isLecture;
    Integer amount;
    String name; //КН_МП, спеціальність + предмет
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
    public String toString() {
        return name;
    }
}
