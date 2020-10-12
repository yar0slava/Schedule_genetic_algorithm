package genetic_algorithm;

import genetic_algorithm.domain.InitialData;
import genetic_algorithm.domain.Class;

import java.util.ArrayList;

public class ScheduleIndividual implements Comparable<ScheduleIndividual>{

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
        if(!isHealthSame){
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

//    public ScheduleIndividual initialize(){
//       ArrayList<Group> groups = data.getGroups();
//
//
//        for (:) {
//
//        }
//        for (Group group: groups ) {
//
//
//        }
//    }


    private double estimateHealth() {
        conflicts = 0;

        for (Class cl: classes) {
               if(cl.getGroup().getAmount() > cl.getClassroom().getCapacity()) {
                   conflicts++;
               }
            for (Class cl2: classes) {
                if(cl.getId() != cl2.getId() && cl.getClassTime() == cl2.getClassTime()){
                    if(cl.getLecturer().getId() == cl2.getLecturer().getId()) {
                        conflicts++;
                    }
                    if(cl.getClassroom().getId() == cl2.getClassroom().getId()){
                        conflicts++;
                    }
                    if(cl.getGroup().getId() == cl2.getGroup().getId()){
                        conflicts++;
                    }

                }

            }
        }
        return 1/(double)(conflicts+1);
    }

    @Override
    public int compareTo(ScheduleIndividual o) {
//        if (this.getHealth() > o.getHealth()){
//            return -1;
//        }
//        else if (this.getHealth() < o.getHealth()) {
//            return 1;
//        }
        return  (int)(o.getHealth() - this.getHealth());
    }

    @Override
    public String toString(){

        StringBuilder stringBuilder = new StringBuilder();

        for (Class cl: classes) {
            stringBuilder.append(cl.toString() + ", ");
        }

        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length()-1);
        return stringBuilder.toString();
    }
}
