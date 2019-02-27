package eight_puzzle_a_star;

public abstract class Edge {
	protected Vertex source;
	protected Vertex destination;

	/* the cost to transition from src to dest */
	protected TransitionCost transitionCost;
}
