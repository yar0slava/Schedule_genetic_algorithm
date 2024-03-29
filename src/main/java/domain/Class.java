package domain;

public class Class {

    private Integer id;
    private Discipline discipline;
    private ClassTime classTime;
    private Group group;
    private Classroom classroom;
    private Lecturer lecturer;

    public Class(Integer id, Discipline discipline, ClassTime classTime,
                 Group group, Classroom classroom, Lecturer lecturer) {
        this.id = id;
        this.discipline = discipline;
        this.classTime = classTime;
        this.group = group;
        this.classroom = classroom;
        this.lecturer = lecturer;
    }

    public Integer getId() {
        return id;
    }

    public Lecturer getLecturer() {
        return lecturer;
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

    public ClassTime getClassTime() {
        return classTime;
    }

    public void setClassTime(ClassTime classTime) {
        this.classTime = classTime;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Classroom getClassroom() {
        return classroom;
    }

//    public void setClassroom(Classroom classroom) {
//        this.classroom = classroom;
//    }

    @Override
    public String toString() {
        return " [" + discipline +
                "|" + classTime +
                "|" + group +
                "|" + classroom +
                "|" + lecturer +
                ']';
    }
}
