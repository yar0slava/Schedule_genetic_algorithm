package genetic_algorithm;

import genetic_algorithm.domain.*;
import genetic_algorithm.domain.Class;

import java.util.ArrayList;

public class Driver {
    private InitialData data;
    private int classNumb = 1;
    public static final int POPULATION_SIZE = 9;
    private int scheduleNumb = 0;


    public static void main(String[] args) {
        int generationNumber = 0;
        Driver driver = new Driver();
        driver.data = new InitialData();
        driver.printAvailableData();

        System.out.println("> Generation # "+ generationNumber);
        System.out.print("  Schedule # |                                           ");
        System.out.print("Classes [dept,class,room,instructor,meeting-time]       ");
        System.out.println( "                                  | Fitness | Conflicts");
        System.out.print("-----------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(driver.data);
        Population population = new Population(Driver.POPULATION_SIZE, driver.data).sortByHealth();
        population.getScheduleIndividuals().forEach(schedule -> System.out.println("       "+driver.scheduleNumb++ +
                "     | "+ schedule + " | " +
                String.format("%.5f",schedule.getHealth()) +
                " | "+schedule.getConflicts()));
        driver.printScheduleAsTable(population.getScheduleIndividuals().get(0), generationNumber);
        driver.classNumb = 1;
        while (population.getScheduleIndividuals().get(0).getHealth() != 1.0) {
            System.out.println("> Generation # "+ ++generationNumber);
            System.out.print("  Schedule # |                                           ");
            System.out.print("Classes [dept,class,room,instructor,meeting-time]       ");
            System.out.println("                                  | Fitness | Conflicts");
            System.out.print("-----------------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------------------");
            population = geneticAlgorithm.generateEvolution(population).sortByHealth();
            driver.scheduleNumb = 0;
            population.getScheduleIndividuals().forEach(schedule -> System.out.println("       "+driver.scheduleNumb++ +
                    "     | "+ schedule + " | " +
                    String.format("%.5f",schedule.getHealth()) +
                    " | "+schedule.getConflicts()));
            driver.printScheduleAsTable(population.getScheduleIndividuals().get(0), generationNumber);
            driver.classNumb = 1;
        }

    }

    private void printScheduleAsTable(ScheduleIndividual schedule, int generation){
        ArrayList<Class> classes = schedule.getClasses();

        System.out.print("\n                       ");
        System.out.println("Class # | Dept | Course (number, max # of students) | Room (Capacity) |   Instructor (Id)   |  Meeting Time (Id)");
        System.out.print("                       ");
        System.out.print("------------------------------------------------------");
        System.out.println("---------------------------------------------------------------");

        for (Class classees: schedule.getClasses()) {
            System.out.print("                       ");
            System.out.print(String.format("  %1$02d  ", classNumb) + "  | ");
            System.out.printf("%1$21s", classees.getDiscipline() + ")|");
            System.out.printf("%1$21s", classees.getClassroom() + " " + classees.getClassroom().getCapacity().toString() + ")|");
            System.out.printf("%1$21s", classees.getClassTime() + ")|");
            System.out.printf("%1$21s", classees.getGroup() + " " + classees.getGroup().getAmount().toString() +
                    " " + classees.getGroup().getLecturer() + ")|");
            System.out.println("\n");

            classNumb++;
        }
        if (schedule.getHealth() == 1) System.out.println("> Solution Found in "+ (generation + 1) +" generations");
        System.out.print("-----------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
    }

    private void printAvailableData() {

        System.out.println(" <--- Group ---> ");
        for (Group group : data.getGroups()) {
            System.out.println("Group_ID: " + group.getId() + ", Name: " + group.getName() + ", Amount: " + group.getAmount()
                    + ", isLecture: " + group.getIsLecture() + ", Lecturer: " + group.getLecturer());
        }

        System.out.println("\n <--- Discipline ---> ");
        for (Discipline discipline : data.getDisciplines()) {
            System.out.println("Discipline_ID: " + discipline.getId() + ", Name: "
                    + discipline.getName() + ", Groups: " + discipline.getGroups());

        }

        System.out.println("\n  <--- Classroom ---> ");
        for (Classroom classrooms : data.getClassrooms()) {
            System.out.println("Room_ID: " + classrooms.getId() + ", Capacity: " + classrooms.getCapacity());
        }

        System.out.println("\n <--- Lecturer --->");
        for (Lecturer lecturer : data.getLecturers()) {
            System.out.println("Lecturer_ID: " + lecturer.getId() + ", Name: " + lecturer.getName());
        }

        System.out.println("\n <--- ClassTime --->");
        for (ClassTime time : data.getClassTimes()) {
            System.out.println("Time_ID: " + time.getId() + ", Name: " + time.getTime());
        }

    }

}
