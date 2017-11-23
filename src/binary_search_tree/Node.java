package binary_search_tree;

/**
 * Represents a node in a tree, specifically, a binary search tree.
 * @author James T
 *
 * @param <T> Any type that implements Comparable<T> (i.e.: Integer, Double, String, etc.)
 */
public class Node<T extends Comparable<T>> {
	/**
	 * The key / value / data of the node
	 */
	public T datum = null;
	public Node left = null;
	public Node right = null;
	public Node parent = null;
	
	public Node(T datum) {
		this.datum = datum;
	}
	
	/**
	 * Allows the datum of the node to be printed when called.
	 * @return The string that is to be printed.
	 */
	@Override
	public String toString() {
		return datum == null ? "null" : datum.toString();
	}
	
	/**
	 * An alternative print that helps with debugging.
	 * @return The string that represents this node more accurately.
	 */
	public String toStringDebug() {
		return String.format(
				"Datum = %s, Left = %s, Right = %s, Parent = %s",
				datum == null ? "null" : datum.toString(), 
				left == null ? "null" : left.datum.toString(), 
				right == null ? "null" : right.datum.toString(),
				parent == null ? "null" : parent.datum.toString());
	}
}
