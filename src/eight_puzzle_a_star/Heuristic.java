package eight_puzzle_a_star;

public interface Heuristic {
	public abstract float getCost(Vertex vertex);
}
