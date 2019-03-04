package eight_puzzle_a_star;

public class DistanceHeuristic implements Heuristic {
	public float getCost(Vertex gameState) {
		return ((GameState)gameState).getDistanceCost();
	}

	public String getName() {
		return "Distance Heuristic";
	}
}
