package eight_puzzle_a_star;

public class MisplacedTilesHeuristic implements Heuristic {
	public float getCost(Vertex gameState) {
		return ((GameState)gameState).getMisplacedTileCost();
	}

	public String getName() {
		return "Misplaced Tiles Heuristic";
	}
}
