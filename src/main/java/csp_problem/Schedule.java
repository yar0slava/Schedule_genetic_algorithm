package csp_problem;

import domain.*;

import java.util.*;

public class Schedule {

//    public static void main(String[] args) {
//        InitialData data = new InitialData();
//        Schedule schedule = new Schedule(data);
//        schedule.initialize(data);
//    }

    InitialData data;
    HashMap<Class, Value> schedule;
    HashMap<Class, ArrayList<Value>> remainingClasses;

    public Schedule(InitialData data) {
        this.data = data;
    }

    public void assignValueToClass(Class cl, Value value){
        schedule.put(cl, value);
        remainingClasses.remove(cl);
        shrinkDomainsFor(cl, value);
    }

    //not checked
    private void shrinkDomainsFor(Class cl, Value value){
        ClassTime time = value.getClassTime();

        for (Class cls: remainingClasses.keySet()) {
            if(cl.getLecturer().equals(cls.getLecturer())){
                remainingClasses.get(cls).removeIf(v -> v.getClassTime().equals(time));
            }else if(cl.getGroupName().equals(cls.getGroupName()) &&
            !( !cl.getGroupIsLecture() && !cls.getGroupIsLecture() ) ){
                remainingClasses.get(cls).removeIf(v -> v.getClassTime().equals(time));
            }else{
                remainingClasses.get(cls).remove(value);
            }
        }
    }

    //not checked
    public Class mrvHeuristic(){
        List<Class> remClasses = new ArrayList<Class>(remainingClasses.keySet());

        remClasses.sort(new Comparator<Class>() {
            @Override
            public int compare(Class o1, Class o2) {
                return remainingClasses.get(o1).size() - remainingClasses.get(o2).size();
            }
        });

        return remClasses.get(0);
    }

    //not checked
    public ArrayList<Class> findNeighbours(Class cls){
        ArrayList<Class> classes = new ArrayList<>();

        for (Class cl: remainingClasses.keySet()) {
            if( cl.getGroupId() == cls.getGroupId() || cl.getLecturer().equals(cls.getLecturer()) ||
                    cl.getGroupName() == cls.getGroupName()){
                classes.add(cl);
            }
        }
        return  classes;
    }

    public void initialize(InitialData data){
        remainingClasses = new HashMap<Class, ArrayList<Value>>();
        ArrayList<Value> values = new ArrayList<>();

        for (ClassTime classTime: data.getClassTimes()) {
            for (Classroom classroom: data.getClassrooms()) {
                Value v = new Value(classTime, classroom);
                System.out.println(v);
                values.add(new Value(classTime, classroom));
            }
        }

        int classId = 0;
        ArrayList<Discipline> disciplines = data.getDisciplines();

        for (Discipline discipline : disciplines) {
            for (Group group : discipline.getGroups()) {
                Class cl = new Class(classId++, discipline, group, group.getLecturer());

                //rooms with suitable capacity
                ArrayList<Value> clValues = new ArrayList<>();
                for (Value v:values) {
                    if(v.getClassroom().getCapacity()>= group.getAmount()){
                        clValues.add(v);
                    }
                }
                remainingClasses.put(cl, clValues);
            }
        }
    }

    //not checked
    public Class powerHeuristic(){
        HashMap<Lecturer, ArrayList<Group>> lecturerGroups = data.getLecturerGroups();

        int maxGroups = 0;
        Lecturer lectWithMaxGroups = data.getLecturers().get(0);

        for (Lecturer lecturer: data.getLecturers()) {
            if(lecturerGroups.get(lecturer).size() > maxGroups){
                maxGroups = lecturerGroups.get(lecturer).size();
                lectWithMaxGroups = lecturer;
            }
        }

        int maxGroupAmount = 0;
        Group biggestGroup = lecturerGroups.get(lectWithMaxGroups).get(0);

        for (Group group: lecturerGroups.get(lectWithMaxGroups)) {
            if(group.getAmount() > maxGroupAmount){
                maxGroupAmount = group.getAmount();
                biggestGroup = group;
            }
        }

        for (Class cl: remainingClasses.keySet()) {
            if(cl.getGroup().equals(biggestGroup)){
                return cl;
            }
        }
        return null;
    }

}
