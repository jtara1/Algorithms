package dijkstra_path_finding;

/**
 * Used as key in a dictionary to point to associated {@link #Data}
 * @author j
 *
 */
public interface DataKey {
	public abstract boolean equals(Object obj);
	public abstract int hashCode();
}
