package genetic_algorithm;

import genetic_algorithm.domain.Discipline;
import genetic_algorithm.domain.Group;
import genetic_algorithm.domain.InitialData;
import genetic_algorithm.domain.Class;

import java.util.ArrayList;

public class ScheduleIndividual implements Comparable<ScheduleIndividual> {

   private ArrayList<Class> classes;
   private boolean isHealthSame = false;
   private double health = -1;
   private Integer classId = 0;
   private Integer conflicts = 0;
   private InitialData data;

   public ScheduleIndividual(InitialData data) {
      this.data = data;
      classes = new ArrayList<Class>(data.getNumberOfClasses());
   }

   public InitialData getData() {
      return data;
   }

   public Integer getConflicts() {
      return conflicts;
   }

   public boolean isHealthSame() {
      return isHealthSame;
   }

   public double getHealth() {
      if (!isHealthSame) {
         health = estimateHealth();
         isHealthSame = true;
      }
      return health;
   }

   public Integer getClassId() {
      return classId;
   }

   public ArrayList<Class> getClasses() {
      isHealthSame = false;
      return classes;
   }

   public ScheduleIndividual initialize() {
      ArrayList<Discipline> disciplines = data.getDisciplines();
      ArrayList<Group> groups = data.getGroups();

      int timeAmount = data.getClassTimes().size();
      int roomsAmount = data.getClassrooms().size();

      for (Discipline discipline : disciplines) {
         for (Group group : discipline.getGroups()) {
            Class cl = new Class(classId++, discipline, data.getClassTimes().get((int) (Math.random() * timeAmount)),
                    group, data.getClassrooms().get((int) (Math.random() * roomsAmount)), group.getLecturer());
            classes.add(cl);

         }
      }
      return this;
   }

   private double estimateHealth() {
      conflicts = 0;

      for (Class cl : classes) {
         for (Class cl2 : classes) {
            if (cl.getId() != cl2.getId() && cl.getClassTime() == cl2.getClassTime()) {
               if (cl.getLecturer().getId() == cl2.getLecturer().getId()) { // викладачі не можуть в один час різні класи
                  conflicts++;
               }
               if (cl.getClassroom().getId() == cl2.getClassroom().getId()) { // кімнати однакові не можуть в один час різні класи мати
                  conflicts++;
               }
               if ( cl.getGroup().getId() == cl2.getGroup().getId() ||
                       (cl.getGroup().getName() == cl2.getGroup().getName() && cl.getGroup().getIsLecture() != cl2.getGroup().getIsLecture()) ) {
                  // одна спеціальність не може мати практику і лекцію одночасно з одного предмету
                  conflicts++;
               }
            }

         }
         if (cl.getGroup().getAmount() > cl.getClassroom().getCapacity()) {
            conflicts++;
         }
      }
      return 1 / (double) (conflicts + 1);
   }

   @Override
   public int compareTo(ScheduleIndividual o) {
        if (this.getHealth() > o.getHealth()){
            return -1;
        }
      return 1;
   }

   @Override
   public String toString() {

      StringBuilder stringBuilder = new StringBuilder();

      for (Class cl : classes) {
         stringBuilder.append(cl.toString() + ", ");
      }

      return stringBuilder.toString();
   }
}
