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

//        System.out.println(" -- Generation Number: "+ generationNumber);
//        System.out.printf("%2s %4s %9s %12s"," #", "Health", "Conflicts", "Classes [Class -- ClassTime -- Group -- ClassTime] " + "\n");
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(driver.data);
        Population population = new Population(Driver.POPULATION_SIZE, driver.data).sortByHealth();
//        for (ScheduleIndividual schedule: population.getScheduleIndividuals()) {
//            System.out.println(" "+driver.scheduleNumb++ + " | " +
//                    String.format("%.5f",schedule.getHealth())+ " | " + schedule.getConflicts() + "         |"
//                + schedule);
//        }
        driver.printScheduleAsTable(population.getScheduleIndividuals().get(0), generationNumber);
        driver.classNumb = 1;
        while (population.getScheduleIndividuals().get(0).getHealth() != 1.0) {
            System.out.println(" -- Generation Number: "+ generationNumber++);
            System.out.printf("%2s %4s %9s %12s"," #", "Health", "Conflicts", "Classes [Class -- ClassTime -- Group -- ClassTime] " + "\n");
            population = geneticAlgorithm.generateEvolution(population).sortByHealth();
            driver.scheduleNumb = 0;
            for (ScheduleIndividual schedule: population.getScheduleIndividuals()) {
                System.out.println(" "+driver.scheduleNumb++ + " | " + String.format("%.5f",schedule.getHealth())+ "  | " + schedule.getConflicts() + "         |"
                        + schedule);
            }

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

        System.out.println(" ....... Group ....... ");
       System.out.println("+---------+---------+---------+---------------+");
        System.out.printf("%9s %9s %9s %12s", "Group ID", "Name", "Amount", "Lecturer" + "\n");
        System.out.println("+---------+---------+---------+---------------+");
        for (Group group : data.getGroups()) {
            System.out.format("%9s %8s %8s %18s",group.getId(), group.getName(), group.getAmount(),group.getLecturer() + "\n");
        }


        System.out.println("\n ....... Discipline ....... ");
        System.out.println("+------+---------+----------------------------------");
        System.out.printf("%3s %9s %18s", "ID", "Name", "Groups" + "\n");
        System.out.println("+------+---------+----------------------------------");
        for (Discipline discipline : data.getDisciplines()) {

            System.out.format("%3s %9s %38s",discipline.getId(), discipline.getName(), discipline.getGroups() + "\n");
        }

        System.out.println("\n .. Classroom .. ");
        System.out.println("+---------+---------+");
        System.out.printf("%9s %11s", "Room_ID", "Capacity" + "\n");
        System.out.println("+---------+---------+");
        for (Classroom classrooms : data.getClassrooms()) {
            System.out.format("%6s %11s",classrooms.getId(),classrooms.getCapacity() + "\n");
        }


        System.out.println("\n .... Lecturer ....");
        System.out.println("+-------+-------------------+");
        System.out.printf("%6s %15s", "ID", "Name" + "\n");
        System.out.println("+-------+-------------------+");
        for (Lecturer lecturer : data.getLecturers()) {
            System.out.format("%6s %20s",lecturer.getId(), lecturer.getName() + "\n");
        }

        System.out.println("\n ..... ClassTime .....");
        System.out.println("+-------+-------------------+");
        System.out.printf("%6s %15s", "ID", "Name" + "\n");
        System.out.println("+-------+-------------------+");
        for (ClassTime time : data.getClassTimes()) {
            System.out.format("%6s %20s",time.getId(),time.getTime() + "\n");
        }
        System.out.println();

    }

}
