package genetic_algorithm;

import genetic_algorithm.domain.*;

public class Driver {
    private InitialData data;

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.data = new InitialData();
        driver.printAvailableData();

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
