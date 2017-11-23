package dijkstra_path_finding;

/**
 * Used as a unique identifier for a {@link Data}
 * and as a key in a dictionary to point to associated {@link Data}
 * Allows for use in Hashtable for more efficient search
 * @author j
 *
 */
public interface DataKey {
	/**
	 * Compare two {@link DataKey}s
	 * @param obj
	 * @return
	 */
	public abstract boolean equals(Object obj);
	
	/**
	 * Because a subclass (or an attribute of it) of this interface will be 
	 * a unique identifier associated with {@link Data}
	 * we need a unique identifier for support for use in a Hashtable
	 * 
	 * @return This should call Objects.hash(Object objs ...) to get a hash int and
	 * return that hash
	 */
	public abstract int hashCode();
}
