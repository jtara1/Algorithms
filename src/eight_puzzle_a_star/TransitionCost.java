package eight_puzzle_a_star;

/* the weight or scalar intended for describing the cost to transition from one vertex to another */
public interface TransitionCost {
	public abstract Float getCost();
}
