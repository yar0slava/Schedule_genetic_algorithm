package csp_problem;

import com.sun.deploy.cache.BaseLocalApplicationProperties;
import domain.*;

import java.util.*;

public class Schedule {

    InitialData data;
    HashMap<Class, Value> schedule;
    HashMap<Class, ArrayList<Value>> remainingClasses;

    static Class testClass;

    public Schedule(InitialData data) {
        this.data = data;
    }

    public void assignValueToClass(Class cl, Value value){
        schedule.put(cl, value);
        remainingClasses.remove(cl);
        shrinkDomainsFor(cl, value);
    }

    public static void main(String[] args) {
        InitialData data = new InitialData();
        Schedule schedule = new Schedule(data);
        schedule.initialize(data);
        System.out.println("-----------------");
        System.out.println(schedule.leastConstrainingValue(testClass));
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

        //сусіди - це ті пари, між якими можуть виникати конфлікти
        for (Class cl: remainingClasses.keySet()) {
            if(cl.getLecturer().equals(cls.getLecturer()) || //якщо одинакові лектори, то мають бути пари у різний час
                    // якщо cls - це лекція, то всі лекції цієї спеціальності - сусіди
                    (cl.getGroupName() == cls.getGroupName() && cl.getGroupIsLecture() && cls.getGroupIsLecture()) ||
                    //якщо одинакова спеціальність, то лекції і практики мають бути в різний час
                    // якщо cls - це лекція, то всі практики цієї спеціальності - сусіди, і навпаки
                    (cl.getGroupName() == cls.getGroupName() && cl.getGroupIsLecture() && !(cls.getGroupIsLecture())) ||
                    (cl.getGroupName() == cls.getGroupName() && !(cl.getGroupIsLecture()) && cls.getGroupIsLecture())){
                classes.add(cl);
            }
        }
        return  classes;
    }

    public Value leastConstrainingValue(Class cl){
        ArrayList<Class> classNeighbs = findNeighbours(cl); //усі сусіди класу
        ArrayList<Value> possibleValues = remainingClasses.get(cl); //усі можливі значення, які може приймати клас
        HashMap<Value,Integer> valuesWithRemainingForNeighbs = new HashMap<>(); // значення та його евриїстична вартість
        Integer remainingForAllNeighbs = 0;

        for(Value v : possibleValues){
            //для кожного значення рахуємо його евриїстичну цінність
            for(Class c : classNeighbs){
                remainingForAllNeighbs += findHowManyValuesRemaining(v, c);
            }
            valuesWithRemainingForNeighbs.put(v, remainingForAllNeighbs);
        }

        // чим менше скорочень в доменах сусідів, тим краще значення
        int maxValueInMap = (Collections.max(valuesWithRemainingForNeighbs.values()));
        for (Map.Entry<Value, Integer> entry : valuesWithRemainingForNeighbs.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                return entry.getKey(); //returns the most flexible value with least constraining
            }
        }

        return null;
    }

    public Integer findHowManyValuesRemaining (Value v, Class cl2){
        ArrayList<Value> allValues = remainingClasses.get(cl2);
        //з цього сусіда потрібно прибрати
        //всі ті велю, де ЧАС стає конфліктом
        for (Value val : allValues){
            if(val.getClassTime() == v.getClassTime()){
                allValues.remove(val);
            }
        }
        return allValues.size();
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

                testClass = cl;
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
