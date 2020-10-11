package genetic_algorithm.domain;

public class Class {
    private Integer id;
    private Discipline discipline;
    private ClassTime classTime;
    private Group group;
    private Lecturer lecturer;
    private Classroom classroom;

    public Class(Integer id, Discipline discipline, ClassTime classTime, Group group, Lecturer lecturer, Classroom classroom) {
        this.id = id;
        this.discipline = discipline;
        this.classTime = classTime;
        this.group = group;
        this.lecturer = lecturer;
        this.classroom = classroom;
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

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Override
    public String toString() {
        return "Class{" +
                "discipline=" + discipline +
                ", classTime=" + classTime +
                ", group=" + group +
                ", lecturer=" + lecturer +
                ", classroom=" + classroom +
                '}';
    }
}
