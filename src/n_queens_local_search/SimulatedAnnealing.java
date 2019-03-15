package n_queens_local_search;

import java.util.Random;

public class SimulatedAnnealing {
	// attrs
	private int iterationLimit = Integer.MAX_VALUE;
	private State initialState;

	// get and set
	public State getInitialState() { return initialState; }

	// static
	private static Random random = new Random();

	// constructors
	public SimulatedAnnealing(State initialState) {
		this.initialState = initialState;
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
	public State start() {
		State current = initialState;
		System.out.println("start simulated annealing\n" + initialState.toString());

		for (int i = 0; i < iterationLimit; ++i) {
			float temperature = initialState.temperatureScheduling(i);

			int currentEnergy = current.energy();
			if (temperature == 0f || currentEnergy == 0) return current;

			State next = current.getRandomNeighbor();
			int deltaEnergy = next.energy() - currentEnergy;

			float probability = (float)Math.exp(-deltaEnergy / temperature);

			if (deltaEnergy < 0) current = next;
			else if (probability >= random.nextFloat()) {
				current = next;
			}

			if (i % 100000 == 0) {
				System.out.println("---------------------------------");
				System.out.println("deltaE: " + deltaEnergy);
				System.out.println("energy: " + currentEnergy);
				System.out.println("currnt: " + current);
				System.out.println("probab: " + probability);
			}
		}

		return current;
	}
}
