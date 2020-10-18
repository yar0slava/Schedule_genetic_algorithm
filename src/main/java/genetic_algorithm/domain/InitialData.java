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
        Classroom room1 = new Classroom("101",50);
        Classroom room2 = new Classroom("121",40);
        Classroom room3 = new Classroom("223",55);
        Classroom room4 = new Classroom("224",35);
        Classroom room5 = new Classroom("313",50);
        Classroom room6 = new Classroom("225",50);
        Classroom room7 = new Classroom("112",50);

        classrooms = new ArrayList<Classroom>(Arrays.asList(room1,room2,room3,room4,room5,room6,room7));

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

        Group group1 = new Group("КН_L", "КН", 50,true,lecturer1);
        Group group2 = new Group("КН_1", "КН", 30,false,lecturer1);
        Group group3 = new Group("КН_2", "КН", 20,false,lecturer2);
        Group group4 = new Group("ІПЗ_L", "ІПЗ", 50,true,lecturer3);
        Group group5 = new Group("ІПЗ_1", "ІПЗ", 25,false, lecturer3);
        Group group6 = new Group("ІПЗ_2", "ІПЗ", 25,false,lecturer4);
        Group group7 = new Group("ПМ_3", "ПМ", 50,true,lecturer4);
        Group group8 = new Group("ПМ_3", "ПМ", 15,false,lecturer1);
        Group group9 = new Group("ПМ_3", "ПМ", 35,false,lecturer3);

        groups = new ArrayList<Group>(Arrays.asList(group1, group2, group3,group4, group5, group6, group7, group8, group9));


        Discipline discipline1 = new Discipline("D1", "МП", new ArrayList<Group>(Arrays.asList(group4, group3, group1)));
        Discipline discipline2 = new Discipline("D2", "ООЕІ", new ArrayList<Group>(Arrays.asList(group4, group2)));
        Discipline discipline3 = new Discipline("D3", "ОКА", new ArrayList<Group>(Arrays.asList(group7,group8,group9)));
        Discipline discipline4 = new Discipline("D4", "ДМ", new ArrayList<Group>(Arrays.asList(group1, group3)));
        Discipline discipline5 = new Discipline("D5", "БМТ", new ArrayList<Group>(Arrays.asList(group4,group5)));
        Discipline discipline6 = new Discipline("D6", "ЛААГ", new ArrayList<Group>(Arrays.asList(group7,group8)));

        //disciplines = new ArrayList<Discipline>(Arrays.asList(discipline1, discipline2, discipline3, discipline4,discipline5,discipline6));
       disciplines = new ArrayList<Discipline>(Arrays.asList(discipline1, discipline2, discipline3));
        disciplines.forEach(x -> numberOfClasses += x.getGroups().size());
        return this;
    }
}
