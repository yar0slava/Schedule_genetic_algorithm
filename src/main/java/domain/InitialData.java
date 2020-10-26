package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InitialData {

    private ArrayList<Classroom> classrooms;
    private ArrayList<Lecturer> lecturers;
    private ArrayList<Discipline> disciplines;
    private ArrayList<ClassTime> classTimes;
    private ArrayList<Group> groups;
    private int numberOfClasses = 0;
    HashMap<Lecturer, ArrayList<Group>> lecturerGroups;

    public InitialData() {
        initialize();
    }

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

    public HashMap<Lecturer, ArrayList<Group>> getLecturerGroups() {
        return lecturerGroups;
    }

    private InitialData initialize() {
        Classroom room1 = new Classroom("101", 60);
        Classroom room2 = new Classroom("103", 70);
        Classroom room3 = new Classroom("104", 45);
        Classroom room4 = new Classroom("105", 35);
        Classroom room5 = new Classroom("106", 40);
        Classroom room6 = new Classroom("107", 60);
        Classroom room7 = new Classroom("108", 50);
        Classroom room8 = new Classroom("109", 60);
        Classroom room9 = new Classroom("110", 70);
        Classroom room10 = new Classroom("111", 45);
        Classroom room11 = new Classroom("112", 35);
        Classroom room12 = new Classroom("113", 40);
        Classroom room13 = new Classroom("114", 60);
        Classroom room14 = new Classroom("115", 50);

//        classrooms = new ArrayList<Classroom>(Arrays.asList(room1, room2, room3, room4,
//                room5, room6, room7, room8, room9, room10, room11, room12, room13, room14));

//        classrooms = new ArrayList<Classroom>(Arrays.asList(room1, room2, room3, room4, room5, room6));
        classrooms = new ArrayList<Classroom>(Arrays.asList(room1, room2, room3));

        ClassTime classTimeM1 = new ClassTime("1", "Пн 08:30 - 09:50");
        ClassTime classTimeM2 = new ClassTime("2", "Пн 10:00 - 11:20");
        ClassTime classTimeM3 = new ClassTime("3", "Пн 11:40 - 13:00");
        ClassTime classTimeM4 = new ClassTime("4", "Пн 13:30 - 14:40");
        ClassTime classTimeM5 = new ClassTime("5", "Пн 15:00 - 16:20");

        ClassTime classTimeT1 = new ClassTime("6", "Вт 08:30 - 09:50");
        ClassTime classTimeT2 = new ClassTime("7", "Вт 10:00 - 11:20");
        ClassTime classTimeT3 = new ClassTime("8", "Вт 11:40 - 13:00");
        ClassTime classTimeT4 = new ClassTime("9", "Вт 13:30 - 14:40");
        ClassTime classTimeT5 = new ClassTime("10", "Вт 15:00 - 16:20");

        ClassTime classTimeW1 = new ClassTime("11", "Ср 08:30 - 09:50");
        ClassTime classTimeW2 = new ClassTime("12", "Ср 10:00 - 11:20");
        ClassTime classTimeW3 = new ClassTime("13", "Ср 11:40 - 13:00");
        ClassTime classTimeW4 = new ClassTime("14", "Ср 13:30 - 14:40");
        ClassTime classTimeW5 = new ClassTime("15", "Ср 15:00 - 16:20");

        ClassTime classTimeTH1 = new ClassTime("16", "Чт 08:30 - 09:50");
        ClassTime classTimeTH2 = new ClassTime("17", "Чт 10:00 - 11:20");
        ClassTime classTimeTH3 = new ClassTime("18", "Чт 11:40 - 13:00");
        ClassTime classTimeTH4 = new ClassTime("19", "Чт 13:30 - 14:40");
        ClassTime classTimeTH5 = new ClassTime("20", "Чт 15:00 - 16:20");

        ClassTime classTimeF1 = new ClassTime("21", "Пт 08:30 - 09:50");
        ClassTime classTimeF2 = new ClassTime("22", "Пт 10:00 - 11:20");
        ClassTime classTimeF3 = new ClassTime("23", "Пт 11:40 - 13:00");
        ClassTime classTimeF4 = new ClassTime("24", "Пт 13:30 - 14:40");
        ClassTime classTimeF5 = new ClassTime("25", "Пт 15:00 - 16:20");

        classTimes = new ArrayList<ClassTime>(Arrays.asList(
//                classTimeM1, classTimeM2, classTimeM3, classTimeM4, classTimeM5,
                classTimeT1, classTimeT2, classTimeT3, classTimeT4, classTimeT5,
                classTimeW1, classTimeW2, classTimeW3, classTimeW4, classTimeW5,
                classTimeTH1, classTimeTH2, classTimeTH3, classTimeTH4, classTimeTH5,
                classTimeF1, classTimeF2, classTimeF3, classTimeF4, classTimeF5));

        Lecturer lecturer1 = new Lecturer(101, "Проценко Д.В.");
        Lecturer lecturer2 = new Lecturer(105, "Брюховецький В.С.");
        Lecturer lecturer3 = new Lecturer(102, "Чорна Т.В.");
        Lecturer lecturer4 = new Lecturer(106, "Корольова О.О.");

        Lecturer lecturer9 = new Lecturer(109, "Тимошкевич Л.М.");
        Lecturer lecturer10 = new Lecturer(110, "Козеренко С.О.");
        Lecturer lecturer11 = new Lecturer(111, "Ольшевська В.А.");
        Lecturer lecturer12 = new Lecturer(112, "Олійник М.А.");
        Lecturer lecturer13 = new Lecturer(113, "Лебідь В.О.");

        Lecturer lecturer14 = new Lecturer(114, "Глибовець М.М.");
        Lecturer lecturer15 = new Lecturer(115, "Салата К.В.");
        Lecturer lecturer16 = new Lecturer(116, "Тригуб О.С.");

        Lecturer lecturer17 = new Lecturer(117, "Сініцина Р.Б.");

        lecturers = new ArrayList<Lecturer>(Arrays.asList(lecturer1, lecturer2, lecturer3,
                lecturer4, lecturer9, lecturer10, lecturer11, lecturer12, lecturer13,
                lecturer14, lecturer15, lecturer16, lecturer17));

        lecturerGroups = new HashMap<>();

        Group group1 = new Group("КН1_ВДМС_L", "КН1", 50, true, lecturer1);
        Group group2 = new Group("КН1_ВДМС_L", "КН1", 50, true, lecturer2);
        Group group3 = new Group("КН1_ВДМС_3", "КН1", 25, false, lecturer3);
        Group group4 = new Group("КН1_ВДМС_4", "КН1", 25, false, lecturer4);

        Group group5 = new Group("ІПЗ1_ВДМС_L", "ІПЗ1", 60, true, lecturer1);
        Group group6 = new Group("ІПЗ1_ВДМС_L", "ІПЗ1", 60, true, lecturer2);
        Group group7 = new Group("ІПЗ1_ВДМС_1", "ІПЗ1", 30, false, lecturer3);
        Group group8 = new Group("ІПЗ1_ВДМС_2", "ІПЗ1", 30, false, lecturer4);

        lecturerGroups.put(lecturer1, new ArrayList<>(Arrays.asList(group1, group5)));
        lecturerGroups.put(lecturer2, new ArrayList<>(Arrays.asList(group2, group6)));
        lecturerGroups.put(lecturer3, new ArrayList<>(Arrays.asList(group3, group7)));
        lecturerGroups.put(lecturer4, new ArrayList<>(Arrays.asList(group4, group8)));

        Group group10 = new Group("КН1_ДМ_L", "КН1", 50, true, lecturer10);
        Group group11 = new Group("КН1_ДМ_1", "КН1", 25, false, lecturer10);
        Group group12 = new Group("КН1_ДМ_2", "КН1", 25, false, lecturer10);

        Group group13 = new Group("КН1_МА_L", "КН1", 50, true, lecturer13);
        Group group14 = new Group("КН1_МА_1", "КН1", 25, false, lecturer13);
        Group group15 = new Group("КН1_МА_2", "КН1", 25, false, lecturer11);

        Group group16 = new Group("КН1_АГ_L", "КН1", 50, true, lecturer9);
        Group group17 = new Group("КН1_АГ_1", "КН1", 25, false, lecturer9);
        Group group18 = new Group("КН1_АГ_2", "КН1", 25, false, lecturer9);

        Group group19 = new Group("ІПЗ1_ЛААГ_L", "ІПЗ1", 60, true, lecturer9);
        Group group20 = new Group("ІПЗ1_ЛААГ_1", "ІПЗ1", 20, false, lecturer9);
        Group group21 = new Group("ІПЗ1_ЛААГ_2", "ІПЗ1", 20, false, lecturer11);
        Group group22 = new Group("ІПЗ1_ЛААГ_3", "ІПЗ1", 20, false, lecturer11);

        lecturerGroups.put(lecturer9, new ArrayList<>(Arrays.asList(group16, group17, group18, group19, group20)));
        lecturerGroups.put(lecturer10, new ArrayList<>(Arrays.asList(group10, group11, group12)));
        lecturerGroups.put(lecturer11, new ArrayList<>(Arrays.asList(group15, group21, group22)));


        Group group23 = new Group("ІПЗ1_ОДМ_L", "ІПЗ1", 60, true, lecturer13);
        Group group24 = new Group("ІПЗ1_ОДМ_1", "ІПЗ1", 20, false, lecturer13);
        Group group25 = new Group("ІПЗ1_ОДМ_2", "ІПЗ1", 20, false, lecturer12);
        Group group26 = new Group("ІПЗ1_ОДМ_3", "ІПЗ1", 20, false, lecturer12);

        lecturerGroups.put(lecturer12, new ArrayList<>(Arrays.asList(group25, group26)));
        lecturerGroups.put(lecturer13, new ArrayList<>(Arrays.asList(group13, group14, group23, group24)));

        Group group27 = new Group("КН1_МП_L", "КН1", 50, true, lecturer14);
        Group group28 = new Group("КН1_МП_1", "КН1", 10, false, lecturer16);
        Group group29 = new Group("КН1_МП_2", "КН1", 10, false, lecturer15);
        Group group30 = new Group("КН1_МП_3", "КН1", 10, false, lecturer16);
        Group group31 = new Group("КН1_МП_4", "КН1", 10, false, lecturer15);
        Group group32 = new Group("КН1_МП_5", "КН1", 10, false, lecturer16);

        lecturerGroups.put(lecturer14, new ArrayList<>(Arrays.asList(group27)));
        lecturerGroups.put(lecturer15, new ArrayList<>(Arrays.asList(group29, group31)));
        lecturerGroups.put(lecturer16, new ArrayList<>(Arrays.asList(group28, group30, group32)));

        Group group33 = new Group("КН1_ООЕІ_L", "КН1", 50, true, lecturer17);
        Group group34 = new Group("КН1_ООЕІ_1", "КН1", 10, false, lecturer17);
        Group group35 = new Group("КН1_ООЕІ_2", "КН1", 10, false, lecturer17);
        Group group36 = new Group("КН1_ООЕІ_3", "КН1", 10, false, lecturer17);
        Group group37 = new Group("КН1_ООЕІ_4", "КН1", 10, false, lecturer17);
        Group group38 = new Group("КН1_ООЕІ_5", "КН1", 10, false, lecturer17);

        lecturerGroups.put(lecturer17, new ArrayList<>(Arrays.asList(group33, group34, group35, group36, group37, group38)));

        groups = new ArrayList<Group>(Arrays.asList(group1, group2, group3, group4, group5, group6, group7, group8,
                group10, group11, group12, group13, group14, group15, group16, group17, group18, group19,
                group20, group21, group22, group23, group24, group25, group26, group27, group28, group29,
                group30, group31, group32, group33, group34, group35, group36, group37, group38));


        Discipline discipline1 = new Discipline("D1", "ВДМС",
                new ArrayList<Group>(Arrays.asList(group1, group2, group3, group4, group5, group6, group7, group8)));
        Discipline discipline2 = new Discipline("D2", "ДМ",
                new ArrayList<Group>(Arrays.asList(group10, group11, group12)));
        Discipline discipline3 = new Discipline("D3", "МА",
                new ArrayList<Group>(Arrays.asList(group13, group14, group15)));
        Discipline discipline4 = new Discipline("D4", "АГ",
                new ArrayList<Group>(Arrays.asList(group16, group17, group18)));
        Discipline discipline5 = new Discipline("D5", "ЛААГ",
                new ArrayList<Group>(Arrays.asList(group19, group20, group21, group22)));
        Discipline discipline6 = new Discipline("D6", "ОДМ",
                new ArrayList<Group>(Arrays.asList(group23, group24, group25, group26)));
        Discipline discipline7 = new Discipline("D7", "МП",
                new ArrayList<Group>(Arrays.asList(group27, group28, group29, group30, group31, group32)));
        Discipline discipline8 = new Discipline("D8", "ООЕІ",
                new ArrayList<Group>(Arrays.asList(group33, group34, group35, group36, group37, group38)));

        disciplines = new ArrayList<Discipline>(Arrays.asList(discipline1,
                discipline2, discipline3, discipline4,discipline5,discipline6, discipline7, discipline8));
//        disciplines = new ArrayList<Discipline>(Arrays.asList(discipline1, discipline2, discipline3));

        for (Discipline discipline: disciplines) {
            numberOfClasses += discipline.getGroups().size();
        }

        return this;
    }
}
