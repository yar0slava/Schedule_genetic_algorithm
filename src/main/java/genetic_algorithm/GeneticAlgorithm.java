package genetic_algorithm;

import domain.InitialData;

import java.util.ArrayList;

public class GeneticAlgorithm {

    //    public static final int POPULATION_SIZE = 9;
    public static final double MUTATION_PROBABILITY = 0.1;
    public static final double CROSSOVER_POPULATION_PROBABILITY = 0.9;
    public static final double CROSSOVER_SCHEDULE_PROBABILITY = 0.5;
    public static final int PARENTS_FOR_CROSSOVER_NUM = 3;
    public static final int HEALTHIEST_INDIVIDUALS_NUM = 1;

    InitialData data;

    public GeneticAlgorithm(InitialData data) {
        this.data = data;
    }

    ScheduleIndividual crossoverSchedule(ScheduleIndividual individual1, ScheduleIndividual individual2) {
        ScheduleIndividual crossoverRes = new ScheduleIndividual(data).initialize();
//        ScheduleIndividual crossoverRes = new ScheduleIndividual(data);

        for (int i = 0; i < crossoverRes.getClasses().size(); i++) {
            if (Math.random() > CROSSOVER_SCHEDULE_PROBABILITY) {
                crossoverRes.getClasses().set(i, individual1.getClasses().get(i));
            } else {
                crossoverRes.getClasses().set(i, individual2.getClasses().get(i));
            }
        }

        return crossoverRes;
    }

    ScheduleIndividual mutateSchedule(ScheduleIndividual individual) {

        ScheduleIndividual schedule = new ScheduleIndividual(data).initialize();

        for (int i = 0; i < individual.getClasses().size(); i++) {
            if (MUTATION_PROBABILITY > Math.random()) {
                individual.getClasses().set(i, schedule.getClasses().get(i));
            }
        }

        return individual;
    }

    Population selectParentsForCrossover(Population population) {
        Population parentPopulation = new Population(PARENTS_FOR_CROSSOVER_NUM, data);

        int popSize = population.getScheduleIndividuals().size();

        for (int i = 0; i < PARENTS_FOR_CROSSOVER_NUM; i++) {
            parentPopulation.getScheduleIndividuals().set(i, population.getScheduleIndividuals().get((int) (Math.random() * popSize)));
        }

        return parentPopulation;
    }

    Population mutatePopulation(Population population) {
        Population resPopulation = new Population(population.getScheduleIndividuals().size(), data);
        ArrayList<ScheduleIndividual> schedules = resPopulation.getScheduleIndividuals();

        for (int i = 0; i < HEALTHIEST_INDIVIDUALS_NUM; i++) {
            schedules.set(i, population.getScheduleIndividuals().get(i));
        }
        for (int i = HEALTHIEST_INDIVIDUALS_NUM; i < population.getScheduleIndividuals().size(); i++) {
            schedules.set(i, mutateSchedule(population.getScheduleIndividuals().get(i)));
        }

        return resPopulation;
    }

    Population crossoverPopulation(Population population) {
        Population resPopulation = new Population(population.getScheduleIndividuals().size(), data);
        population.sortByHealth();

        for (int i = 0; i < HEALTHIEST_INDIVIDUALS_NUM; i++) {
            resPopulation.getScheduleIndividuals().set(i, population.getScheduleIndividuals().get(i));
        }

        for (int i = HEALTHIEST_INDIVIDUALS_NUM; i < population.getScheduleIndividuals().size(); i++) {
            if (CROSSOVER_POPULATION_PROBABILITY > Math.random()) {
                ScheduleIndividual individual1 = selectParentsForCrossover(population).sortByHealth().getScheduleIndividuals().get(0);
                ScheduleIndividual individual2 = selectParentsForCrossover(population).sortByHealth().getScheduleIndividuals().get(0);
                resPopulation.getScheduleIndividuals().set(i, crossoverSchedule(individual1, individual2));
            } else resPopulation.getScheduleIndividuals().set(i, population.getScheduleIndividuals().get(i));
        }

//        resPopulation.sortByHealth();
        return resPopulation;
    }

    public Population generateEvolution(Population population) {
        return mutatePopulation(crossoverPopulation(population));
    }
}

