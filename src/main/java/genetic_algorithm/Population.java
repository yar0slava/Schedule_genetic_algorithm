package genetic_algorithm;

import domain.InitialData;

import java.util.ArrayList;

public class Population {

    private ArrayList<ScheduleIndividual> scheduleIndividuals;

    public Population(int size, InitialData data){
        scheduleIndividuals = new ArrayList<>(size);

        for (int i=0; i<size; i++){
            scheduleIndividuals.add(i, new ScheduleIndividual(data).initialize());
        }
    }

    public ArrayList<ScheduleIndividual> getScheduleIndividuals() {
        return scheduleIndividuals;
    }

    public Population sortByHealth(){
        scheduleIndividuals.sort(null);

        return this;
    }
}
