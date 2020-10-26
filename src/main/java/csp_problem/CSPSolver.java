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

      HashMap<Class, Value> backtrackingSchedule = forwardCheckingSolve(schedule);


      ClassTime classTimeTest1 = new ClassTime("СTW1", "Ср 08:30 - 09:50");
      ClassTime classTimeTest2 = new ClassTime("СTW2", "Ср 10:00 - 11:20");
      ClassTime classTimeTest3 = new ClassTime("СTW3", "Ср 11:40 - 13:00");
      ClassTime classTimeTest4 = new ClassTime("СTW4", "Ср 13:30 - 14:40");
      ClassTime classTimeTest5 = new ClassTime("СTW5", "Ср 15:00 - 16:20");
      for (Class cls : backtrackingSchedule.keySet()) {
         Value value = backtrackingSchedule.get(cls);
         if (value.getClassTime().equals(classTimeTest1)
                 || value.getClassTime().equals(classTimeTest2)
                 || value.getClassTime().equals(classTimeTest3)
                 || value.getClassTime().equals(classTimeTest4)
                 || value.getClassTime().equals(classTimeTest5)) {
            System.out.printf("\n%50s %80s", cls, backtrackingSchedule.get(cls));
         }
//            System.out.println(cls + "   " + backtrackingSchedule.get(cls));
      }
   }

   public static HashMap<Class, Value> forwardCheckingSolve(Schedule schedule) {
      Class prev = schedule.powerHeuristic(); //початкове
      //запамятали усі велю які можна присвоїти
      ArrayList<Value> prevValues = schedule.remainingClasses.get(prev);
      //вибрали велю, яке присвоюємо
      Value valueToAssign = schedule.leastConstrainingValue(prev);
      //велю, які можна присвоїти, які залишились
      prevValues.remove(valueToAssign);
      //заесайнили
      schedule.assignValueToClass(prev, valueToAssign);


      Class toAssign = schedule.mrvHeuristic();
//
      while (!schedule.remainingClasses.isEmpty()) {
//         toAssign = schedule.mrvHeuristic();
//         schedule.assignValueToClass(toAssign, schedule.leastConstrainingValue(toAssign));
      }


      return schedule.schedule;
   }
//   bool graphColoring(bool graph[V][V], int m, int i, int color[V]) {
//      // if current index reached end
//      if (i == V) {
//         // if coloring is safe
//         if (isSafe(graph, color)) {
//            // Print the solution
//            printSolution(color);
//            return true;
//         }
//         return false;
//      }
//      // Assign each color from 1 to m
//      for (int j = 1; j <= m; j++) {
//         color[i] = j;
//         // Recur of the rest vertices
//         if (graphColoring(
//                 graph, m, i + 1, color))
//            return true;
//         color[i] = 0;
//      }
//      return false;
//   }

   public boolean existsNeighbsValueOption (Schedule s, Class c){
      for(Class n : s.findNeighbours(c))
   }
}
