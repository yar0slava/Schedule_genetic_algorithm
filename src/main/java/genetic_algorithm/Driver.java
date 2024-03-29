package genetic_algorithm;

import domain.*;
import domain.Class;

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

        System.out.println(" -- Generation Number: " + generationNumber);
        System.out.printf("%1s %7s %11s %27s", " #", "Health", "Conflicts", "  Classes [Class -- ClassTime -- Group -- ClassTime -- Lecturer] " + "\n");
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(driver.data);
        Population population = new Population(Driver.POPULATION_SIZE, driver.data).sortByHealth();
        for (ScheduleIndividual schedule : population.getScheduleIndividuals()) {
            System.out.printf("%2s %4s %9s  %12s", driver.scheduleNumb++ + " |",
                    String.format("%.5f", schedule.getHealth()) + " |", schedule.getConflicts() + "  |", schedule);
            System.out.println();

        }
        driver.classNumb = 1;

        while (population.getScheduleIndividuals().get(0).getHealth() != 1.0) {
            System.out.println(" -- Generation Number: " + ++generationNumber);
            System.out.printf("%1s %7s %11s %27s", " #", "Health", "Conflicts", "  Classes [Class -- ClassTime -- Group -- ClassTime -- Lecturer] " + "\n");
            population = geneticAlgorithm.generateEvolution(population).sortByHealth();
            driver.scheduleNumb = 0;
            for (ScheduleIndividual schedule : population.getScheduleIndividuals()) {
                System.out.printf("%2s %4s %9s  %12s", driver.scheduleNumb++ + " |",
                        String.format("%.5f", schedule.getHealth()) + " |", schedule.getConflicts() + "  |", schedule + "\n");
            }
            System.out.println();

            driver.classNumb = 1;
        }
        if (population.getScheduleIndividuals().get(0).getHealth() == 1.0) {
            driver.printScheduleAsTable(population.getScheduleIndividuals().get(0), generationNumber);
        }

    }

    private void printScheduleAsTable(ScheduleIndividual schedule, int generation) {
        ArrayList<Class> classes = schedule.getClasses();


        System.out.println(" ....... Schedule ....... ");
        System.out.println("+-----+-----------+---------+----------+---------------+---------+-----------------------+-----------------------+");
        System.out.printf("%3s %13s %10s %9s %12s %12s %14s %24s", "#","Discipline", "Classroom", "Capacity", "Group", "Amount", "Time ", "Lecturer"+"\n");
        System.out.println("+-----+-----------+---------+----------+---------------+---------+-----------------------+-----------------------+");
        for (Class classees: schedule.getClasses()) {
            System.out.printf("%3s %12s %9s %10s %16s %9s %20s %23s", classNumb, classees.getDiscipline(),
                    classees.getClassroom(),classees.getClassroom().getCapacity().toString(), classees.getGroup().getId(),
                    classees.getGroup().getAmount().toString(), classees.getClassTime(),classees.getGroup().getLecturer() );
            System.out.println();
            classNumb++;
        }
        if (schedule.getHealth() == 1) System.out.println("+--------- correct schedule found on "+ (generation + 1) +" generations -------------------------------------------------------------+");
    }

    private void printAvailableData() {

        System.out.println(" ....... Group ....... ");
        System.out.println("+---------+---------+---------+---------------+");
        System.out.printf("%9s %9s %9s %12s", "Group ID", "Name", "Amount", "Lecturer" + "\n");
        System.out.println("+---------+---------+---------+---------------+");
        for (Group group : data.getGroups()) {
            System.out.format("%9s %8s %8s %18s", group.getId(), group.getName(), group.getAmount(), group.getLecturer() + "\n");
        }


        System.out.println("\n ....... Discipline ....... ");
        System.out.println("+------+---------+----------------------------------");
        System.out.printf("%3s %9s %18s", "ID", "Name", "Groups" + "\n");
        System.out.println("+------+---------+----------------------------------");
        for (Discipline discipline : data.getDisciplines()) {

            System.out.format("%3s %9s %38s", discipline.getId(), discipline.getName(), discipline.getGroups() + "\n");
        }

        System.out.println("\n .. Classroom .. ");
        System.out.println("+---------+---------+");
        System.out.printf("%9s %11s", "Room_ID", "Capacity" + "\n");
        System.out.println("+---------+---------+");
        for (Classroom classrooms : data.getClassrooms()) {
            System.out.format("%6s %11s", classrooms.getId(), classrooms.getCapacity() + "\n");
        }


        System.out.println("\n .... Lecturer ....");
        System.out.println("+-------+-------------------+");
        System.out.printf("%6s %15s", "ID", "Name" + "\n");
        System.out.println("+-------+-------------------+");
        for (Lecturer lecturer : data.getLecturers()) {
            System.out.format("%6s %20s", lecturer.getId(), lecturer.getName() + "\n");
        }

        System.out.println("\n ..... ClassTime .....");
        System.out.println("+-------+-------------------+");
        System.out.printf("%6s %15s", "ID", "Name" + "\n");
        System.out.println("+-------+-------------------+");
        for (ClassTime time : data.getClassTimes()) {
            System.out.format("%6s %20s", time.getId(), time.getTime() + "\n");
        }
        System.out.println();

    }

}
