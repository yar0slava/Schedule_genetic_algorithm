package csp_problem;

import domain.InitialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CSPSolver {

    public static void main(String[] args) {
        InitialData data = new InitialData();

        Schedule schedule = new Schedule(data);
        schedule.initialize(data);
        forwardCheckingMRV_Power(schedule);



        Schedule schedule1 = new Schedule(data);
        schedule1.initialize(data);
        constraintCheckingLCV_MRV_Power(schedule1);
//        forwardCheckingLCV_MRV_Power(schedule1);
    }

    public static void constraintCheckingLCV_MRV_Power(Schedule schedule) {
        System.out.println("---Constraint Checking with LCV, MRV and Power Heuristics---");

        Runtime time = Runtime.getRuntime();
        time.gc();
        long startTime = System.nanoTime();

        Class curr = schedule.powerHeuristic();
        if (constraintPropagationSolveLCV_MRV_Power(schedule, curr) == null) {
            System.err.println("DOMAIN does not have enough values for all classes!");
        } else {
            schedule.printOutSchedule();
        }

        showStatistics(time, startTime);
    }

    public static void forwardCheckingMRV_Power(Schedule schedule) {
        System.out.println("---Forward Checking with MRV and Power Heuristics---");

        Runtime time = Runtime.getRuntime();
        time.gc();
        long startTime = System.nanoTime();

        Class curr = schedule.powerHeuristic();
        if (forwardCheckingSolveMRV_Power(schedule, curr) == null) {
            System.err.println("DOMAIN does not have enough values for all classes!");
        } else {
            schedule.printOutSchedule();
        }

        showStatistics(time, startTime);
    }

    private static void showStatistics(Runtime time, long startTime) {
        long stopTime = System.nanoTime();
        long duration = stopTime - startTime;
        double timeInMs = (double) duration / 1000000;
        long totalUsedMemory = time.totalMemory() - time.freeMemory();
        long totalUsedMemoryInBytes = totalUsedMemory / 1024;

        System.out.println("Time in milliseconds: " + timeInMs);
        System.out.println("Memory in bytes: " + totalUsedMemoryInBytes + "\n\n\n");
    }

    public static HashMap<Class, Value> forwardCheckingSolveLCV_MRV_Power(Schedule schedule, Class currClass) {

        ArrayList<Value> currValues = schedule.remainingClasses.get(currClass);//запамятали усі велю які можна присвоїти
        Value currValue = schedule.leastConstrainingValue(currClass);//вибрали велю, яке присвоюємо
        currValues.remove(currValue);//велю, які можна присвоїти, які залишились
        HashMap<Class, ArrayList<Value>> removedValues = schedule.assignValue(currClass, currValue);//заесайнили

        Class nextClass = schedule.mrvHeuristic();
        ArrayList<Value> nextValues = schedule.remainingClasses.get(nextClass);

        boolean valueNotFound = true;

        HashMap<Class, ArrayList<Value>> nextNeighbours = new HashMap<Class, ArrayList<Value>>();
        for (Class cl : schedule.findNeighbours(nextClass)) {
            nextNeighbours.put(cl, schedule.remainingClasses.get(cl));
        }

        while (valueNotFound) {

            for (Value val : nextValues) {
                if (isConsistent(nextNeighbours, val)) {
                    valueNotFound = false;
                    break;
                }
            }
            if (!valueNotFound) {
                break;
            } else if (currValues.isEmpty()) {
                return null;
            } else {
                for (Class cl : removedValues.keySet()) {
                    schedule.remainingClasses.get(cl).addAll(removedValues.get(cl));
                }
                currValue = Schedule.lcv(Schedule.findNeighboursWithDomains(schedule.remainingClasses, currClass), currValues);
                currValues.remove(currValue);
                removedValues = schedule.assignValue(currClass, currValue);
            }
        }

        if (schedule.remainingClasses.size() == 1) {
            schedule.assignValueToClass(nextClass, nextValues.get(0));
            return schedule.schedule;
        }
        return forwardCheckingSolveLCV_MRV_Power(schedule, nextClass);
    }

    public static HashMap<Class, Value> forwardCheckingSolveMRV_Power(Schedule schedule, Class currClass) {

        ArrayList<Value> currValues = schedule.remainingClasses.get(currClass);//запамятали усі велю які можна присвоїти
        Value currValue = currValues.get(0);//вибрали велю, яке присвоюємо
        currValues.remove(currValue);//велю, які можна присвоїти, які залишились
        HashMap<Class, ArrayList<Value>> removedValues = schedule.assignValue(currClass, currValue);//заесайнили

        Class nextClass = schedule.mrvHeuristic();
        ArrayList<Value> nextValues = schedule.remainingClasses.get(nextClass);

        boolean valueNotFound = true;

        HashMap<Class, ArrayList<Value>> nextNeighbours = new HashMap<Class, ArrayList<Value>>();
        for (Class cl : schedule.findNeighbours(nextClass)) {
            nextNeighbours.put(cl, schedule.remainingClasses.get(cl));
        }

        while (valueNotFound) {

            for (Value val : nextValues) {
                if (isConsistent(nextNeighbours, val)) {
                    valueNotFound = false;
                    break;
                }
            }
            if (!valueNotFound) {
                break;
            } else if (currValues.isEmpty()) {
                return null;
            } else {
                for (Class cl : removedValues.keySet()) {
                    schedule.remainingClasses.get(cl).addAll(removedValues.get(cl));
                }
                currValue = currValues.get(0);
                currValues.remove(currValue);
                removedValues = schedule.assignValue(currClass, currValue);
            }
        }

        if (schedule.remainingClasses.size() == 1) {
            schedule.assignValueToClass(nextClass, nextValues.get(0));
            return schedule.schedule;
        }
        return forwardCheckingSolveLCV_MRV_Power(schedule, nextClass);
    }

    private static boolean isConsistent(HashMap<Class, ArrayList<Value>> nextNeighbours, Value val) {
        for (Class cl : nextNeighbours.keySet()) {
            if (Schedule.howManyValuesRemaining(nextNeighbours.get(cl), val) == 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isConsistentConstraint(Schedule schedule, HashMap<Class, ArrayList<Value>> nextNeighbours, Value val) {
        // всі класи в яких співпадає час
        final Set<Class> classes = getAllTimeRemainingClasses(schedule, val);
        for (Class cl : classes) {
            if (Schedule.howManyValuesRemaining(nextNeighbours.get(cl), val) == 0) {
                return false;
            }
        }


//        for (Class cl : nextNeighbours.keySet()) {
//            if (Schedule.howManyValuesRemaining(schedule.remainingClasses.get(cl), val) == 0) {
//                return false;
//            }
//        }

        return true;
    }
    // всі NSW (всі класи де є конфлікту часу)
    private static Set<Class> getAllTimeRemainingClasses(Schedule schedule, Value val) {
        final Set<Class> classes = new HashSet<>();
        for (Class cl : schedule.remainingClasses.keySet()) {
            if (Schedule.howManyValuesRemaining(schedule.remainingClasses.get(cl), val) == 0) {
                classes.add(cl);
            }
        }
        return classes;
    }


    public static HashMap<Class, Value> constraintPropagationSolveLCV_MRV_Power(Schedule schedule, Class currClass) {

        ArrayList<Value> currValues = schedule.remainingClasses.get(currClass);//запамятали усі велю які можна присвоїти
        Value currValue = schedule.leastConstrainingValue(currClass);//вибрали велю, яке присвоюємо
        currValues.remove(currValue);//велю, які можна присвоїти, які залишились
        HashMap<Class, ArrayList<Value>> removedValues = schedule.assignValue(currClass, currValue);//заесайнили

        Class nextClass = schedule.mrvHeuristic();
        ArrayList<Value> nextValues = schedule.remainingClasses.get(nextClass);

        boolean valueNotFound = true;

        HashMap<Class, ArrayList<Value>> nextNeighbours = new HashMap<Class, ArrayList<Value>>();
        for (Class cl : schedule.findNeighbours(nextClass)) {
            nextNeighbours.put(cl, schedule.remainingClasses.get(cl));
        }

        //isConsistent перевірятиме усіх сусідів усіх сусідів і тд і рекурсивно перевіряти до чого призводить есайнення конкретного велю
        //якщо все ок, то есайнимо

        while (valueNotFound) {

            for (Value val : nextValues) {
                if (isConsistentConstraint(schedule, nextNeighbours, val)) {
                    valueNotFound = false;
                    break;
                }
            }
            if (!valueNotFound) {
                break;
            } else if (currValues.isEmpty()) {
                return null;
            } else {
                for (Class cl : removedValues.keySet()) {
                    schedule.remainingClasses.get(cl).addAll(removedValues.get(cl));
                }
                currValue = Schedule.lcv(Schedule.findNeighboursWithDomains(schedule.remainingClasses, currClass), currValues);
                currValues.remove(currValue);
                removedValues = schedule.assignValue(currClass, currValue);
            }
        }

        if (schedule.remainingClasses.size() == 1) {
            schedule.assignValueToClass(nextClass, nextValues.get(0));
            return schedule.schedule;
        }
        return forwardCheckingSolveLCV_MRV_Power(schedule, nextClass);
    }
}
