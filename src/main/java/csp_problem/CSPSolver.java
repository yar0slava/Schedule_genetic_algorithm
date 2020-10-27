package csp_problem;

import domain.InitialData;

import java.util.ArrayList;
import java.util.HashMap;

public class CSPSolver {

    public static void main(String[] args) {
        InitialData data = new InitialData();
        Schedule schedule = new Schedule(data);
        schedule.initialize(data);

        Runtime time = Runtime.getRuntime();
        time.gc();
        long startTime = System.nanoTime();

        Class curr = schedule.powerHeuristic();
        System.out.println("---Constraint Propagation with LCV, MRV and Power Heuristics---");
        if (constraintPropagationSolveLCV_MRV_Power(schedule, curr) == null) {
//        System.out.println("---Forward Checking with LCV, MRV and Power Heuristics---");
//        if (forwardCheckingSolveLCV_MRV_Power(schedule, curr) == null) {
//        System.out.println("---Forward Checking with MRV and Power Heuristics---");
//        if (forwardCheckingSolveMRV_Power(schedule, curr) == null) {
//        System.out.println("---Constraint Propagation with MRV and Power Heuristics---");
//        if (constraintPropagationSolveMRV_Power(schedule, curr) == null) {
            System.err.println("DOMAIN does not have enough values for all classes!");
        } else {
            schedule.printOutSchedule();
        }

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
        return forwardCheckingSolveMRV_Power(schedule, nextClass);
    }

    public static HashMap<Class, Value> constraintPropagationSolveLCV_MRV_Power(Schedule schedule, Class currClass) {

        while (true) {
            ArrayList<Value> currValues = schedule.remainingClasses.get(currClass);//запамятали усі велю які можна присвоїти
            Value currValue = schedule.leastConstrainingValue(currClass);//вибрали велю, яке присвоюємо

            if (isConsistentConstraintLCV(schedule, currClass, currValue)) {
                schedule.assignValue(currClass, currValue);
                currValues.remove(currValue);
                break;
            }
             else if (currValues.size()==1) {
                return null;
            }
        }

        if (schedule.remainingClasses.isEmpty()) {
            return schedule.schedule;
        }

        return constraintPropagationSolveLCV_MRV_Power(schedule, schedule.mrvHeuristic());
    }

    public static HashMap<Class, Value> constraintPropagationSolveMRV_Power(Schedule schedule, Class currClass) {

        while (true) {
            ArrayList<Value> currValues = schedule.remainingClasses.get(currClass);//запамятали усі велю які можна присвоїти
            Value currValue = schedule.remainingClasses.get(currClass).get(0);//вибрали велю, яке присвоюємо

            if (isConsistentConstraint(schedule, currClass, currValue)) {
                schedule.assignValue(currClass, currValue);
                currValues.remove(currValue);
                break;
            }
            else if (currValues.size()==1) {
                return null;
            }
        }

        if (schedule.remainingClasses.isEmpty()) {
            return schedule.schedule;
        }

        return constraintPropagationSolveMRV_Power(schedule, schedule.mrvHeuristic());
    }

    private static boolean isConsistent(HashMap<Class, ArrayList<Value>> nextNeighbours, Value val) {
        for (Class cl : nextNeighbours.keySet()) {
            if (Schedule.howManyValuesRemaining(nextNeighbours.get(cl), val) == 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isConsistentConstraintLCV(Schedule schedule, Class c, Value val) {

        Schedule scheduleCheck = new Schedule(schedule.data);
        scheduleCheck.initialize(schedule.data);

        Class currClass = c;

        scheduleCheck.assignValueToClass(currClass, val);

        while(!scheduleCheck.remainingClasses.isEmpty()){
            if(isAnyEmpty(scheduleCheck.remainingClasses)){
                return false;
            }
            currClass = scheduleCheck.mrvHeuristic();
            scheduleCheck.assignValueToClass(currClass, scheduleCheck.leastConstrainingValue(currClass));
        }

        return true;
    }

    private static boolean isConsistentConstraint(Schedule schedule, Class c, Value val) {

        Schedule scheduleCheck = new Schedule(schedule.data);
        scheduleCheck.initialize(schedule.data);

        Class currClass = c;

        scheduleCheck.assignValueToClass(currClass, val);

        while(!scheduleCheck.remainingClasses.isEmpty()){
            if(isAnyEmpty(scheduleCheck.remainingClasses)){
                return false;
            }
            currClass = scheduleCheck.mrvHeuristic();
            scheduleCheck.assignValueToClass(currClass, scheduleCheck.remainingClasses.get(currClass).get(0));
        }

        return true;
    }

    private static boolean isAnyEmpty(HashMap<Class, ArrayList<Value>> sch){
        for(Class c : sch.keySet()){
            if(sch.get(c).size() == 0){
                return true;
            }
        }
        return false;
    }

}
