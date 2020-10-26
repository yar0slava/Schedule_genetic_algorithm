package csp_problem;

import domain.ClassTime;
import domain.InitialData;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class CSPSolver {

   public static void main(String[] args) {
      InitialData data = new InitialData();
      Schedule schedule = new Schedule(data);
      schedule.initialize(data);

      forwardCheckingLCV_MRV_Power(schedule);
   }

   public static void forwardCheckingLCV_MRV_Power(Schedule schedule){
      System.out.println("---Forward Checking with LCV, MRV and Power Heuristics---");

      Runtime time = Runtime.getRuntime();
      time.gc();
      long startTime = System.nanoTime();

      Class curr = schedule.powerHeuristic();
//      if(forwardCheckingSolveLCV_MRV_Power(schedule, curr) == null){
      if(forwardCheckingSolveMRV_Power(schedule, curr) == null){
         System.err.println("DOMAIN does not have enough values for all classes!");
      }else {
         schedule.printOutSchedule();
      }

      long stopTime = System.nanoTime();
      long duration = stopTime - startTime;
      double timeInMs = (double)duration/1000000;
      long totalUsedMemory = time.totalMemory() - time.freeMemory();
      long totalUsedMemoryInBytes = totalUsedMemory/1024;

      System.out.println("Time in milliseconds: " + timeInMs);
      System.out.println("Memory in bytes: " + totalUsedMemoryInBytes);


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
      for (Class cl: schedule.findNeighbours(nextClass)) {
         nextNeighbours.put(cl, schedule.remainingClasses.get(cl));
      }

      while (valueNotFound) {

         for (Value val : nextValues) {
            if (isConsistent(nextNeighbours, val)) {
               valueNotFound = false;
               break;
            }
         }
         if(!valueNotFound){
            break;
         }else if(currValues.isEmpty()){
            return null;
         }
         else {
            for (Class cl: removedValues.keySet()){
               schedule.remainingClasses.get(cl).addAll(removedValues.get(cl));
            }
            currValue = Schedule.lcv(Schedule.findNeighboursWithDomains(schedule.remainingClasses, currClass), currValues);
            currValues.remove(currValue);
            removedValues = schedule.assignValue(currClass, currValue);
         }
      }

      if(schedule.remainingClasses.size()==1){
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
      for (Class cl: schedule.findNeighbours(nextClass)) {
         nextNeighbours.put(cl, schedule.remainingClasses.get(cl));
      }

      while (valueNotFound) {

         for (Value val : nextValues) {
            if (isConsistent(nextNeighbours, val)) {
               valueNotFound = false;
               break;
            }
         }
         if(!valueNotFound){
            break;
         }else if(currValues.isEmpty()){
            return null;
         }
         else {
            for (Class cl: removedValues.keySet()){
               schedule.remainingClasses.get(cl).addAll(removedValues.get(cl));
            }
            currValue = currValues.get(0);
            currValues.remove(currValue);
            removedValues = schedule.assignValue(currClass, currValue);
         }
      }

      if(schedule.remainingClasses.size()==1){
         schedule.assignValueToClass(nextClass, nextValues.get(0));
         return schedule.schedule;
      }
      return forwardCheckingSolveLCV_MRV_Power(schedule, nextClass);
   }

   private static boolean isConsistent(HashMap<Class, ArrayList<Value>> nextNeighbours, Value val) {
      for (Class cl: nextNeighbours.keySet()) {
         if(Schedule.howManyValuesRemaining(nextNeighbours.get(cl), val) == 0){
            return false;
         }
      }
      return true;
   }
}
