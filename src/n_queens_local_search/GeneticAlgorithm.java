package n_queens_local_search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm implements Runnable {
    // attrs
    private int boardSize;
    private int populationSize;
    private float chanceToMutateChild = 0.05f;

    private int iterationLimit = 1000000; // 1m
    private State bestState;

    private Random random = new Random();
    private Thread thread;

    // get and set

    // static

    // constructors
    public GeneticAlgorithm(int boardSize, int populationSize) {
        this.boardSize = boardSize;
        this.populationSize = populationSize;
    }

    /**
     * starts with k randomly generated states (population)
     * Loop:
     * newPopulation = empty set
     * iterate over population:
     *  x = randomSelect(pop, fitnessFunc)
     *  y = randomSelect(pop, fitnessFunc)
     *  child = reproduce(x, y)
     *
     *  if (small rand probability):
     *   child = mutate(child)
     *
     *  add child to newPopulation
     * util some individual is fit enough or enough time has passed
     *
     * @return
     */
    public State geneticAlgorithm() {
        ArrayList<State> population = createPopulation(populationSize);
        ArrayList<State> newPopulation;

        bestState = population.get(0);

        for (int generation = 0; generation < iterationLimit; ++generation) {
            newPopulation = new ArrayList<>();

            for (int i = 0; i < population.size(); ++i) {
                State state1 = randomSelection(population);
                State state2 = randomSelection(population);

                State child = state1.reproduce(state2);
                if (random.nextFloat() <= chanceToMutateChild) child = child.mutate();

                newPopulation.add(child);

                if (child.fitness() > bestState.fitness()) bestState = child;
                if (child.fitness() == child.getMaxFitness()) {
                    return child;
                }
            }

            population = newPopulation;
        }

        return bestState;
    }

    private ArrayList<State> createPopulation(int size) {
        ArrayList<State> population = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            population.add(GameState.trulyRandomState(boardSize));
        }

        return population;
    }

    /* selects a state from the list at random with the weighted biases being based on fitness */
    private State randomSelection(ArrayList<State> population) {
        float randomFloat = random.nextFloat();
        Collections.shuffle(population);

        int fitnessSum = 0;
        float probability = 0f;

        for (State state : population) {
            fitnessSum += state.fitness(); // caches algo output to calculate fitness
        }

        for (State state : population) {
            float probabilityToPick = (float)state.fitness() / fitnessSum;
            probability += probabilityToPick;

            if (randomFloat <= probability) return state;
        }

//        System.out.println("warning: randomSelection failed, picked last element in population " + probability);
        return population.get(population.size() - 1); // shouldnn't ever occur
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, toString());
            thread.start();
        }
    }

    public void run() {
        geneticAlgorithm();
    }
}
