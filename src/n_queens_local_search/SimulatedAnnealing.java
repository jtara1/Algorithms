package n_queens_local_search;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Random;

public class SimulatedAnnealing implements Runnable {
	// attrs
	private int iterationLimit = 2000000000; // 2b
	private State initialState;
	private State bestState;
	private int bestEnergy = Integer.MAX_VALUE;
	private HashSet<String> solutions;
	private int iterations = 0;

	private Thread thread;
	private Float runtime;

	// get and set
	public State getInitialState() { return initialState; }
	public State getBestState() {
		return bestState;
	}
	public HashSet<String> getSolutions() {
		return solutions;
	}
	public Thread getThread() {
		return thread;
	}
	public float getRuntime() {
		return runtime;
	}

	// static
	private static Random random = new Random();

	// constructors
	public SimulatedAnnealing(State initialState) {
		this.bestState = initialState;
		this.initialState = initialState;
		this.solutions = new HashSet<>();
	}

	public SimulatedAnnealing() {
		this(GameState.randomState(8));
	}

	// methods
	/**
	 * pseudo code func:
	 * for k in [1, inf]:
	 *   T := tempSchedule[k]
	 *   if T = 0: return current
	 *   next := neighbor(current) # randomly selected
	 *   deltaEnergy := energy(next) - energy(current)
	 *
	 *   if deltaEnergy > 0: current := next
	 *   else if e^(deltaEnergy / T) >= random(0, 1):
	 *     current := next
	 *
	 * return current
	 */
	public State simulatedAnnealing() {
		return simulatedAnnealing(false);
	}

	public State simulatedAnnealing(boolean continueToCheckForOtherSolutions) {
		State current = initialState;
		bestEnergy = current.energy();
		boolean solutionFound = false;
		Instant runtimeStart = Instant.now();

//		System.out.println("starting simulated annealing\n" + initialState.toString());

		iterationLimit = 1000000000; // 50m
		for (iterations = 0; iterations < iterationLimit; ++iterations) {
			float temperature = initialState.temperatureScheduling(iterations);

			int currentEnergy = current.energy();
			solutionFound = current.isSolution();
			if (temperature == 0f || solutionFound) bestState = current;

			State next = current.getRandomNeighbor();
			int nextEnergy = next.energy();
			if (nextEnergy <= bestEnergy) {
				bestState = next;
				bestEnergy = nextEnergy;
			}

			int deltaEnergy = nextEnergy - currentEnergy;

			// sigmoid function to allow domain (-inf, inf) and range (0, 1)
			float deltaE = (deltaEnergy > 0 ? -1 : 1) * deltaEnergy;
			deltaE = deltaE == 0 ? -15 : deltaE;
			float probability = (float)(1 / (1 + Math.exp(deltaE / temperature))); // sigmoid: 1 / (1 + e^-x)

			if (nextEnergy <= currentEnergy) current = next;
			else if (probability >= random.nextFloat()) current = next;

			if (solutionFound) solutions.add(current.toString());
//			if (solutionFound || iteration == iterationLimit - 1 || iteration % 500000 == 0) {
			if (solutionFound || iterations == iterationLimit - 1) {
//				System.out.println("---------------------------------");
//				System.out.println("deltaE: " + deltaEnergy);
//				System.out.println("curntE: " + currentEnergy);
//				System.out.println("currnt: " + current);
//				System.out.println("next  : " + next);
//				System.out.println("probab: " + probability);
//				System.out.println("step  : " + iteration);

				if ((solutionFound || iterations == iterationLimit - 1) && !continueToCheckForOtherSolutions) {
//					System.out.println("solution: " + current);
					Duration duration = Duration.between(runtimeStart, Instant.now());
					runtime = duration.getSeconds() + (duration.getNano() / 100000000f);
					return bestState;
				}
			}
		}

//		System.out.println(bestEnergy);
		return bestState;
	}

	public double getSearchCost() {
		return iterations;
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, initialState.toString());
			thread.start();
		}
	}

	@Override
	public void run() {
		bestState = simulatedAnnealing();
	}
}
