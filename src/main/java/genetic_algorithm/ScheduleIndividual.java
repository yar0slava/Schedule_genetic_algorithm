package genetic_algorithm;

import genetic_algorithm.domain.Group;
import genetic_algorithm.domain.InitialData;
import genetic_algorithm.domain.Class;

import java.util.ArrayList;

public class ScheduleIndividual {

    private ArrayList<Class> classes;
    private boolean isHealthSame = false;
    private double health = -1;
    private Integer classId = 0;
    private Integer conflicts = 0;
    private InitialData data;

    public InitialData getData() {
        return data;
    }
    public ScheduleIndividual(InitialData data) {
        this.data = data;
        classes = new ArrayList<Class>(data.getNumberOfClasses());
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

//    public ScheduleIndividual initialize(){
//       ArrayList<Group> groups = data.getGroups();
//
//
//        for (:
//             ) {
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

    public Integer getClassId() {
        return classId;
    }

//    @Override
//    public String toString(){
//        String printClass = new String();
//        for (Class pCl: classes) {
//            printClass
//
//        }
//
//    }

    public ArrayList<Class> getClasses() {
        isHealthSame = false;
        return classes;
    }


}
