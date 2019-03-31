package n_queens_local_search;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm implements Runnable {
    // attrs
    protected int boardSize;
    private int populationSize;
    private float chanceToMutateChild = 0.05f;

    private int iterationLimit = 1500000; // 1m
    private State bestState;
    private int iterations = 0;

    private Random random = new Random();
    private Thread thread;
    private Float runtime;

    // get and set
    public State getBestState() {
        return bestState;
    }
    public Thread getThread() {
        return thread;
    }
    public Float getRuntime() {
        return runtime;
    }

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
        return geneticAlgorithm(population);
    }

    protected State geneticAlgorithm(ArrayList<State> population) {
        ArrayList<State> newPopulation;
        Instant runtimeStart = Instant.now();

        bestState = population.get(0);
        System.out.println("starting GE for n queens k = " + this.populationSize + " mutationChance = " + chanceToMutateChild);

        for (iterations = 0; iterations < iterationLimit; ++iterations) {
            newPopulation = new ArrayList<>();

            for (int i = 0; i < population.size(); ++i) {
                State state1 = randomSelection(population);
                State state2 = randomSelection(population);

                State child = state1.reproduce(state2);
                if (random.nextFloat() <= chanceToMutateChild) child = child.mutate();

                newPopulation.add(child);

                if (child.fitness() > bestState.fitness()) bestState = child;
                if (child.fitness() == child.getMaxFitness()) {
                    Duration duration = Duration.between(runtimeStart, Instant.now());
                    runtime = duration.getSeconds() + (duration.getNano() / 100000000f);
                    bestState = child;
                    return child;
                }
            }

            population = newPopulation;
        }

        Duration duration = Duration.between(runtimeStart, Instant.now());
        runtime = duration.getSeconds() + (duration.getNano() / 100000000f);
        return bestState;
    }

    private ArrayList<State> createPopulation(int size) {
        ArrayList<State> population = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            population.add(GameState.trulyRandomState(boardSize));
        }

        return population;
    }

    /* selects a state_pointer from the list at random with the weighted biases being based on fitness */
    private State randomSelection(ArrayList<State> population) {
        float randomFloat = random.nextFloat();
//        Collections.shuffle(population);
        Collections.sort(population);

        int fitnessSum = 0;
        float probability = 0f;

        for (State state_pointer : population) {
            fitnessSum += state_pointer.fitness(); // caches algo output to calculate fitness
        }

        for (State state_pointer : population) {
            float probabilityToPick = (float)state_pointer.fitness() / fitnessSum;
            probability += probabilityToPick;

            if (randomFloat <= probability) return state_pointer;
        }

//        System.out.println("warning: randomSelection failed, picked last element in population " + probability);
        return population.get(population.size() - 1); // shouldn't ever occur
    }

    public double getSearchCost() {
        return iterations;
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
