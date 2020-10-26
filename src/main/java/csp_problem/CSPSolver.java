package csp_problem;

import domain.ClassTime;
import domain.InitialData;

import java.util.ArrayList;
import java.util.HashMap;

public class CSPSolver {

   public static void main(String[] args) {
      InitialData data = new InitialData();
      Schedule schedule = new Schedule(data);
      schedule.initialize(data);
      System.out.println("-----------------");
//        System.out.println(schedule.leastConstrainingValue(testClass));


//        for (Class cls: schedule.remainingClasses.keySet()) {
//            System.out.println(cls + "\n ==== values =========================================================  SIZE: "
//                    + schedule.remainingClasses.get(cls).size());
////            for (Value val: schedule.remainingClasses.get(cls)) {
////                System.out.println("     " + val);
////            }
//        }

//        for (Class cls: schedule.remainingClasses.keySet()) {
//            System.out.println(cls);
//        }
//        System.out.println("POWER------------------" + '\n' + schedule.powerHeuristic());

//        for (Class cls: schedule.remainingClasses.keySet()) {
//            System.out.println(cls + "\n ==== neighbours =========================================================  SIZE: "
//                    + schedule.findNeighbours(cls).size());
//            for (Class cls1: schedule.findNeighbours(cls)) {
//                System.out.println("     " + cls1);
//            }
//        }

      Class curr = schedule.powerHeuristic();
      HashMap<Class, Value> forwCeckSchedule = forwardCheckingSolve(schedule, curr);



      ClassTime classTimeTest1 = new ClassTime("СTW1", "Ср 08:30 - 09:50");
      ClassTime classTimeTest2 = new ClassTime("СTW2", "Ср 10:00 - 11:20");
      ClassTime classTimeTest3 = new ClassTime("СTW3", "Ср 11:40 - 13:00");
      ClassTime classTimeTest4 = new ClassTime("СTW4", "Ср 13:30 - 14:40");
      ClassTime classTimeTest5 = new ClassTime("СTW5", "Ср 15:00 - 16:20");
      for (Class cls : forwCeckSchedule.keySet()) {
         Value value = forwCeckSchedule.get(cls);
         if (value.getClassTime().equals(classTimeTest1)
                 || value.getClassTime().equals(classTimeTest2)
                 || value.getClassTime().equals(classTimeTest3)
                 || value.getClassTime().equals(classTimeTest4)
                 || value.getClassTime().equals(classTimeTest5)) {
            System.out.printf("\n%50s %80s", cls, forwCeckSchedule.get(cls));
         }
//            System.out.println(cls + "   " + backtrackingSchedule.get(cls));
      }
   }

   public static HashMap<Class, Value> forwardCheckingSolve(Schedule schedule, Class currClass) {

      //запамятали усі велю які можна присвоїти
      ArrayList<Value> currValues = schedule.remainingClasses.get(currClass);
      //вибрали велю, яке присвоюємо
      Value currValue = schedule.leastConstrainingValue(currClass);
      //велю, які можна присвоїти, які залишились
      currValues.remove(currValue);
      //заесайнили
      HashMap<Class, ArrayList<Value>> removedValues = schedule.assignValue(currClass, currValue);

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

      if(schedule.remainingClasses.isEmpty()){
         return schedule.schedule;
      }
      return forwardCheckingSolve(schedule, nextClass);
   }

   private static boolean isConsistent(HashMap<Class, ArrayList<Value>> nextNeighbours, Value val) {
      for (Class cl: nextNeighbours.keySet()) {
         if(Schedule.howManyValuesRemaining(nextNeighbours.get(cl), val) == 0){
            return false;
         }
      }
      return true;
   }

   private static void backtrack() {
   }


   private static boolean consistent(){

      return false;
   }

}
