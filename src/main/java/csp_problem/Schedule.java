package csp_problem;

import domain.*;
import java.util.*;

public class Schedule {

    InitialData data;
    HashMap<Class, Value> schedule = new HashMap<>();
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

    public HashMap<Class, ArrayList<Value>> assignValue(Class cl, Value value){
        schedule.put(cl, value);
        remainingClasses.remove(cl);
        return shrinkDomains(cl, value);
    }

    //not checked
    // method for shrinking domains for all Classes after cl was assigned with Value value
    private void shrinkDomainsFor(Class cl, Value value){
        // Time from Value value
        ClassTime time = value.getClassTime();

        for (Class cls: remainingClasses.keySet()) {

                //remove Values with Time time for Classes with the same Lecturer
            if(cl.getLecturer().equals(cls.getLecturer())){
                remainingClasses.get(cls).removeIf(v -> v.getClassTime().equals(time));

                //remove Values with Time time for Classes with the same Group name only
                // in case if their isLecture values are not both false(this means that both have practice)
                // two Groups with same name can't both have lectures at the same time of lecture and practice at the same time
            }else if(cl.getGroupName().equals(cls.getGroupName()) &&
            ( cl.getGroupIsLecture() || cls.getGroupIsLecture() ) ){
                remainingClasses.get(cls).removeIf(v -> v.getClassTime().equals(time));

                //otherwise remove only Value value from the Class domain
            }else{
                remainingClasses.get(cls).remove(value);
            }
        }
    }

    private HashMap<Class, ArrayList<Value>> shrinkDomains(Class cl, Value value){

        HashMap<Class, ArrayList<Value>> removedValues = new HashMap<Class, ArrayList<Value>>();

        // Time from Value value
        ClassTime time = value.getClassTime();

        for (Class cls: remainingClasses.keySet()) {

            //remove Values with Time time for Classes with the same Lecturer
            if(cl.getLecturer().equals(cls.getLecturer())){
                removedValues.put(cls, new ArrayList<>());
//                remainingClasses.get(cls).removeIf(v -> v.getClassTime().equals(time));
                for (Value val: remainingClasses.get(cls)) {
                    if(val.getClassTime().equals(time)){
                        remainingClasses.get(cls).remove(val);
                        removedValues.get(cls).add(val);
                    }
                }

                //remove Values with Time time for Classes with the same Group name only
                // in case if their isLecture values are not both false(this means that both have practice)
                // two Groups with same name can't both have lectures at the same time of lecture and practice at the same time
            }else if(cl.getGroupName().equals(cls.getGroupName()) &&
                    ( cl.getGroupIsLecture() || cls.getGroupIsLecture() ) ){

                removedValues.put(cls, new ArrayList<>());
//                remainingClasses.get(cls).removeIf(v -> v.getClassTime().equals(time));
                for (Value val: remainingClasses.get(cls)) {
                    if(val.getClassTime().equals(time)){
                        remainingClasses.get(cls).remove(val);
                        removedValues.get(cls).add(val);
                    }
                }

                //othervise remove only Value value from the Class domain
            }else{
                remainingClasses.get(cls).remove(value);
                removedValues.put(cls, new ArrayList<>());
                removedValues.get(cls).add(value);
            }
        }
        return removedValues;
    }

    // find Classes which domain is most influenced by assigning Value to Class cls
    public ArrayList<Class> findNeighbours(Class cls){
        ArrayList<Class> classes = new ArrayList<>();

        //сусіди - це ті пари, між якими можуть виникати конфлікти
        for (Class cl: remainingClasses.keySet()) {
            if( cl.getLecturer().equals(cls.getLecturer()) || //якщо одинакові лектори, то мають бути пари у різний час
                    ( cl.getGroupName().equals(cls.getGroupName()) && ( cl.getGroupIsLecture() || cls.getGroupIsLecture() ) )){
                    // якщо cls - це лекція, то всі лекції цієї спеціальності - сусіди
                    //якщо одинакова спеціальність, то лекції і практики мають бути в різний час
                    // якщо cls - це лекція, то всі практики цієї спеціальності - сусіди, і навпаки

                classes.add(cl);
            }
        }
        return  classes;
    }

    // find Classes which domain is most influenced by assigning Value to Class cls
    public static HashMap<Class, ArrayList<Value>> findNeighboursWithDomains(HashMap<Class, ArrayList<Value>> remClasses, Class cls){
        HashMap<Class, ArrayList<Value>> classes = new HashMap<Class, ArrayList<Value>>();

        //сусіди - це ті пари, між якими можуть виникати конфлікти
        for (Class cl: remClasses.keySet()) {
            if( cl.getLecturer().equals(cls.getLecturer()) || //якщо одинакові лектори, то мають бути пари у різний час
                    ( cl.getGroupName().equals(cls.getGroupName()) && ( cl.getGroupIsLecture() || cls.getGroupIsLecture() ) )){
                classes.put(cl, remClasses.get(cl));
            }
        }
        return  classes;
    }

    // find Class which assigning will influence the biggest amount of other Classes domains
    // it is the Class with the biggest amount of neighbours
    public Class powerHeuristic(){
        // Classes with their neighbours amount
        HashMap<Class, Integer> classNeighboursAmont = new HashMap<>();

        // fill in the HashMap
        for (Class cls: remainingClasses.keySet()) {
            classNeighboursAmont.put(cls, findNeighbours(cls).size());
        }

        // find maximum neighbours amount
        int maxNeighbours = Collections.max(classNeighboursAmont.values());

        // find Class with maximum neighbours amount
        for (Class cls: classNeighboursAmont.keySet()) {
            if(classNeighboursAmont.get(cls) == maxNeighbours){
                return  cls;
            }
        }
        return null;
    }

    //not checked
    // Minimum Remaining Value Heuristic
    // return Class with the minimum amount of remaining values in domain(ArrayList of Values)
    public Class mrvHeuristic(){
        // list of all remaining Classes without Values assigned
        List<Class> remClasses = new ArrayList<Class>(remainingClasses.keySet());

        // sort classes by the amount of Values from their domains
        remClasses.sort(new Comparator<Class>() {
            @Override
            public int compare(Class o1, Class o2) {
                return remainingClasses.get(o1).size() - remainingClasses.get(o2).size();
            }
        });

        //get the first one with the minimum domain
        return remClasses.get(0);
    }

    public Value leastConstrainingValue(Class cl){
        ArrayList<Class> classNeighbours = findNeighbours(cl); //усі сусіди класу
        ArrayList<Value> possibleValues = remainingClasses.get(cl); //усі можливі значення, які може приймати клас
        HashMap<Value,Integer> valuesWithRemainingForNeighbours = new HashMap<>(); // значення та його евристична вартість
        Integer remainingForAllNeighbours = 0;

        for(Value v : possibleValues){
            //для кожного значення рахуємо його евристичну цінність
            for(Class c : classNeighbours){
                remainingForAllNeighbours += findHowManyValuesRemaining(v, c);
            }
            valuesWithRemainingForNeighbours.put(v, remainingForAllNeighbours);
        }

        // чим менше скорочень в доменах сусідів, тим краще значення
        int maxValueInMap = (Collections.max(valuesWithRemainingForNeighbours.values()));
        for (Map.Entry<Value, Integer> entry : valuesWithRemainingForNeighbours.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                return entry.getKey(); //returns the most flexible value with least constraining
            }
        }
        return null;
    }

    public static Value lcv(HashMap<Class, ArrayList<Value>> clsNeighbours, ArrayList<Value> possibleVals){
        ArrayList<Class> classNeighbours = new ArrayList<>(clsNeighbours.keySet()); //усі сусіди класу
        ArrayList<Value> possibleValues = new ArrayList<>(possibleVals); //усі можливі значення, які може приймати клас
        HashMap<Value,Integer> valuesWithRemainingForNeighbours = new HashMap<>(); // значення та його евристична вартість
        Integer remainingForAllNeighbours = 0;

        for(Value v : possibleValues){
            //для кожного значення рахуємо його евристичну цінність
            for(Class c : classNeighbours){
                remainingForAllNeighbours += howManyValuesRemaining(clsNeighbours.get(c), v);
            }
            valuesWithRemainingForNeighbours.put(v, remainingForAllNeighbours);
        }

        // чим менше скорочень в доменах сусідів, тим краще значення
        int maxValueInMap = (Collections.max(valuesWithRemainingForNeighbours.values()));
        for (Map.Entry<Value, Integer> entry : valuesWithRemainingForNeighbours.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                return entry.getKey(); //returns the most flexible value with least constraining
            }
        }
        return null;
    }

    private Integer findHowManyValuesRemaining (Value v, Class cl2){

        ArrayList<Value> allValues = new ArrayList<Value>(remainingClasses.get(cl2));
        //з цього сусіда потрібно прибрати
        //всі ті вел'ю, де ЧАС стає конфліктом
        allValues.removeIf(val -> val.getClassTime() == v.getClassTime());
        return allValues.size();
    }

    public static Integer howManyValuesRemaining (ArrayList<Value> allValues, Value v){
        ArrayList<Value> allValuesCopy = new ArrayList<Value>(allValues);
        //з цього сусіда потрібно прибрати
        //всі ті вел'ю, де ЧАС стає конфліктом
        allValuesCopy.removeIf(val -> val.getClassTime() == v.getClassTime());
        return allValuesCopy.size();
    }

    public void initialize(InitialData data){
        remainingClasses = new HashMap<Class, ArrayList<Value>>();
        ArrayList<Value> values = new ArrayList<>();

        for (ClassTime classTime: data.getClassTimes()) {
            for (Classroom classroom: data.getClassrooms()) {
                Value v = new Value(classTime, classroom);
//                System.out.println(v);
                values.add(new Value(classTime, classroom));
            }
        }

        System.out.println("VALUES AMOUNT: " + values.size());

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
}
