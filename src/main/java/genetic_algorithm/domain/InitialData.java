package genetic_algorithm.domain;

import java.util.ArrayList;
import java.util.Arrays;

public class InitialData {

    private ArrayList<Classroom> classrooms;
    private ArrayList<Lecturer> lecturers;
    private ArrayList<Discipline> disciplines;
    private ArrayList<ClassTime> classTimes;
    private ArrayList<Group> groups;
    private int numberOfClasses = 0;
    public InitialData() { initialize(); }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public ArrayList<Discipline> getDisciplines() {
        return disciplines;
    }

    public ArrayList<ClassTime> getClassTimes() {
        return classTimes;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    private InitialData initialize() {
        Classroom room1 = new Classroom("R1",25);

        classrooms = new ArrayList<Classroom>(Arrays.asList(room1));

        ClassTime classTime1 = new ClassTime("СT1", "Пн 08:30 - 09:50");
        ClassTime classTime2 = new ClassTime("СT2", "Пн 10:00 - 11:20");
        ClassTime classTime3 = new ClassTime("СT3", "Пн 11:40 - 13:00");
        ClassTime classTime4 = new ClassTime("СT4", "Пн 13:30 - 14:40");
        ClassTime classTime5 = new ClassTime("СT5", "Пн 15:00 - 16:20");

        classTimes= new ArrayList<ClassTime>(Arrays.asList(classTime1, classTime2, classTime3, classTime4, classTime5));

        Lecturer lecturer1 = new Lecturer(101, "Глибовець М.М.");
        Lecturer lecturer2 = new Lecturer(102, "Синіцина Р.Б.");
        Lecturer lecturer3 = new Lecturer(103, "Глибовець А.М.");
        Lecturer lecturer4 = new Lecturer(104, "Козеренко С.М.");

        lecturers = new ArrayList<Lecturer>(Arrays.asList(lecturer1, lecturer2, lecturer3, lecturer4));

        Group group1 = new Group("G1", "КН1", 50);
        Group group2 = new Group("G2", "ІПЗ1", 50);
        Group group3 = new Group("G3", "П1", 50);

        groups = new ArrayList<Group>(Arrays.asList(group1, group2, group3));

        Discipline discipline1 = new Discipline("D1", "МП", new ArrayList<Group>(Arrays.asList(group1, group3)), new ArrayList<Lecturer>(Arrays.asList(lecturer1)));
        Discipline discipline2 = new Discipline("D2", "ООЕІ", new ArrayList<Group>(Arrays.asList(group1)), new ArrayList<Lecturer>(Arrays.asList(lecturer2)));
        Discipline discipline3 = new Discipline("D3", "ОКА", new ArrayList<Group>(Arrays.asList(group3)), new ArrayList<Lecturer>(Arrays.asList(lecturer3, lecturer4)));
        Discipline discipline4 = new Discipline("D4", "ДМ", new ArrayList<Group>(Arrays.asList(group1, group2)), new ArrayList<Lecturer>(Arrays.asList(lecturer4)));
        Discipline discipline5 = new Discipline("D5", "БМТ", new ArrayList<Group>(Arrays.asList(group2)), new ArrayList<Lecturer>(Arrays.asList(lecturer2, lecturer1)));
        Discipline discipline6 = new Discipline("D6", "ЛААГ", new ArrayList<Group>(Arrays.asList(group1, group3)), new ArrayList<Lecturer>(Arrays.asList(lecturer4, lecturer2)));

        disciplines = new ArrayList<Discipline>(Arrays.asList(discipline1, discipline2, discipline3, discipline4));

        disciplines.forEach(x -> numberOfClasses += x.getGroups().size());
        return this;
    }
}
