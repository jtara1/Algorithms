package n_queens_local_search;

public class GeneticAlgorithm {
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
    public State start() {
        return null;
    }
}
