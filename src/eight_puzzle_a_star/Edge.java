package eight_puzzle_a_star;

public abstract class Edge {
	protected Vertex source;
	protected Vertex destination;

	public Edge(Vertex src, Vertex dest) {
		source = src;
		destination = dest;
	}

	public abstract float getTransitionCost();
}
