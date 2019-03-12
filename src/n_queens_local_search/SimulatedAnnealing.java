package n_queens_local_search;

import java.util.Random;

public class SimulatedAnnealing {
	// attrs
	private int iterationLimit = Integer.MAX_VALUE;
	private State initialState;

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

		for (int i = 0; i < iterationLimit; ++i) {
			float temperature = initialState.temperatureScheduling(i);
			System.out.println(current.energy());
			if (temperature == 0f || current.isSolution()) return current;

			State next = current.getRandomNeighbor();
			int deltaEnergy = next.energy() - current.energy();

			if (deltaEnergy > 0) current = next;
			else if (Math.exp(deltaEnergy / temperature) >= random.nextFloat()) {
				current = next;
			}
		}

		return null;
	}
}
