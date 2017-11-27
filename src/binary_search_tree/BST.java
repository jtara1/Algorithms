package binary_search_tree;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Binary Search Tree that can perform all the operations of a BST.
 * @author James T
 *
 * @param <T> Any type that implements Comparable<T> (i.e.: Integer, Double, String, etc.)
 */
public class BST<T extends Comparable<T>> {
	
	/**
	 * Root node of the tree
	 */
	private Node root = null;
	
	/**
	 * Used to hold the output of one of the three types of traversals
	 */
	private String dataSequence = "";
	
	/**
	 * Holds the values of every element in the tree when a traversal method is called
	 */
	private ArrayList<T> traversedValues = new ArrayList<T>();
	
	/**
	 * Helps determine if an insertion or deletion was successful
	 */
	private boolean operationSuccessful = true;
	
	/**
	 * @return Root node of the tree
	 */
	public Node<T> getRoot() {
		return root;
	}
	
	/**
	 * Goes through the nodes such that the root node is the first node
	 * @param printAfter if true, print the final traversal
	 */
	public void traversePreOrder(boolean printAfter) {
		traversedValues.clear();
		dataSequence = "";
		traversePreOrder(root);
		if (printAfter)
			System.out.println("Pre-order: " + dataSequence);
	}
	
	/**
	 * Recursively traverses all the nodes in the tree
	 * @param node
	 */
	private void traversePreOrder(Node<T> node) {
		if (node != null) {
			// append node key to string & add node data to list
			dataSequence += node.toString() + " ";
			traversedValues.add(node.datum);
			// left
			traversePreOrder(node.left);
			// right
			traversePreOrder(node.right);
		}
	}
	
	/**
	 * Goes through the nodes such that the root node is in the middle
	 * @param printAfter if true, print the final traversal
	 */
	public void traverseInOrder(boolean printAfter) {
		traversedValues.clear();
		dataSequence = "";
		traverseInOrder(root);
		if (printAfter)
			System.out.println("In-order: " + dataSequence);
	}
	
	/**
	 * Recursively traverses all the nodes in the tree
	 * @param node
	 */
	private void traverseInOrder(Node<T> node) {
		if (node != null) {
			// left
			traverseInOrder(node.left);
			// append node key to string & add node.data to list
			dataSequence += node.toString() + " ";
			traversedValues.add(node.datum);
			// right
			traverseInOrder(node.right);
		}
	}
	
	/**
	 * Goes through the nodes such that the root node is the last node
	 * @param printAfter if true, print the final traversal
	 */
	public void traversePostOrder(boolean printAfter) {
		traversedValues.clear();
		dataSequence = "";
		traversePostOrder(root);
		if (printAfter)
			System.out.println("Post-order: " + dataSequence);
	}
	
	/**
	 * Recursively traverses all the nodes in the tree
	 * @param node
	 */
	private void traversePostOrder(Node<T> node) {
		if (node != null) {
			// left
			traversePostOrder(node.left);
			// right
			traversePostOrder(node.right);
			// append node key to string & add node.data to list
			dataSequence += node.toString() + " ";
			traversedValues.add(node.datum);
		}
	}
	
	/**
	 * Recursively insert each datum from the queue (param) data until the queue is empty
	 * @param data Queue that holds data that'll be insert each element in the queue into the tree
	 * one at a time.
	 * @return true if the last operation was successful, false if the last operation failed
	 */
	public boolean insert(Queue<T> data) {
		if (data.isEmpty()) {
			return operationSuccessful;
		}
		operationSuccessful = insert(data.remove());
		return insert(data);
	}
	
	/**
	 * Insert a single element (param datum) into the tree
	 * @param datum The datum of the node that'll be inserted into the tree
	 * @return true if the operation was successful, false otherwise
	 */
	public boolean insert(T datum) {
		// datum not found & tree is not empty
		if (search(datum) != null && root != null)
			return false;
		return insert(datum, root, null, true);
	}
	
	/**
	 * Recursively insert param datum into the tree. The same general procedure was used in pre-order traversal.
	 * @param datum The datum / key of the node that'll be inserted
	 * @param currentNode The node that is being compared to the datum
	 * @param parentNode The parent node of the param currentNode
	 * @param onLeft true if the param currentNode is the left child of its parent, false otherwise
	 * @return true if the operation was successful, false otherwise
	 */
	private boolean insert(T datum, Node<T> currentNode, Node<T> parentNode, boolean onLeft) {
		// empty space has been reached
		if (currentNode == null) {
			Node<T> newNode = new Node<T>(datum);
			// make the parent link to newNode in left xor right attribute
			if (parentNode != null) {
				if (onLeft) {
					parentNode.left = newNode;
					newNode.parent = parentNode;
				} else {
					parentNode.right = newNode;
					newNode.parent = parentNode;
				}
			} else {
				// the tree was empty
				root = newNode;
			}
			return true;
		}
		// go left
		else if (datum.compareTo(currentNode.datum) <= 0) {
			return insert(datum, currentNode.left, currentNode, true);
		}
		// go right
		else if (datum.compareTo(currentNode.datum) > 0) {
			return insert(datum, currentNode.right, currentNode, false);
		}
		return true;
	}
	
	/**
	 * Search for a node containing datum equal to param datum
	 * @param datum The value that's being searched for
	 * @return The node matched containing datum equal to the param datum
	 */
	public Node<T> search(T datum) {
		// tree is empty
		if (root == null)
			return null;
		return search(datum, root);
	}
	
	/**
	 * Recursively search for the node containing datum equal to param datum.
	 * Procedure is similar to pre-order traversal.
	 * @param datum What is being searched for
	 * @param currentNode The current node whose datum is being compared to param datum
	 * @return The node matched containing datum equal to the param datum
	 */
	private Node<T> search(T datum, Node<T> currentNode) {
		int comparison = datum.compareTo(currentNode.datum);
		
		// node found 
		if (comparison == 0) {
			return currentNode;
		}
		// go left
		else if (comparison < 0) {
			if (currentNode.left != null)
				return search(datum, currentNode.left);
		}
		// go right
		else if (comparison > 0) {
			if (currentNode.right != null)
				return search(datum, currentNode.right);
		}
		// node not found
		return null;
	}
	
	/**
	 * Recursively deletes each value in the queue (param) data until it's empty
	 * @param data Each element in this will be deleted in the tree.
	 * @return true if last operation was successful, false if last operation failed
	 */
	public boolean delete(Queue<T> data) {
		if (data.isEmpty()) {
			return operationSuccessful;
		}
		operationSuccessful = delete(data.remove());
		return delete(data);
	}
	
	/**
	 * Attempt to delete the param datum
	 * @param datum The value that is searched for and deleted if found
	 * @return true if operation was successful, false otherwise
	 */
	public boolean delete(T datum) {
		// search to find the node that is to be deleted
		Node<T> deleteThis = this.search(datum);
		// the datum does no exist in the tree
		if (deleteThis == null) {
			return false;
		}
		return delete(deleteThis);
	}
	
	/**
	 * Perform the deletion operation given the node that is to be deleted
	 * @param nodeToDelete The node that'll be deleted from the tree
	 * @return true is operation was successful, false otherwise
	 */
	private boolean delete(Node<T> nodeToDelete) {
		// determine whether this node is the left or the right child of its parent
		boolean isLeftChild = true;
		if (nodeToDelete.parent != null) {
			if (nodeToDelete == nodeToDelete.parent.right) {
				isLeftChild = false;
			}
		}
		
		// no children (leaf node)
		if (nodeToDelete.right == null && nodeToDelete.left == null) {
			if (nodeToDelete.parent != null) {
				if (isLeftChild) {
					nodeToDelete.parent.left = null;
				} else {
					nodeToDelete.parent.right = null;
				}
			}
			nodeToDelete.datum = null;
			return true;
		}
		// 1 child on right
		else if (nodeToDelete.right != null && nodeToDelete.left == null) {
			if (nodeToDelete.parent != null) {
				if (isLeftChild) {
					nodeToDelete.parent.left = nodeToDelete.right;
				} else {
					nodeToDelete.parent.right = nodeToDelete.right;
				}
				
				nodeToDelete.right.parent = nodeToDelete.parent;
			} else {
				root = nodeToDelete.right;
				root.parent = null;
			}
			return true;
		}
		// 1 child on left
		else if (nodeToDelete.left != null && nodeToDelete.right == null) {
			if (nodeToDelete.parent != null) {
				if (isLeftChild) {
					nodeToDelete.parent.left = nodeToDelete.left;
				} else {
					nodeToDelete.parent.right = nodeToDelete.left;
				}
				nodeToDelete.left.parent = nodeToDelete.parent;
			} else {
				root = nodeToDelete.left;
				root.parent = null;
			}
			return true;
		}
		// 2 children
		else {
			// get max value node in left subtree recursively
			Node<T> replacer = getMaxNodeInSubtree(nodeToDelete.left);
			
			// get min value node of replacer 
			// (if replacer is the min val in its subtree then replacer is returned) recursively
			// this is used to help relink
			Node<T> minNodeOfReplacer = getMinNodeInSubtree(replacer);
			
			// remove the parent's link to the replacer 
			if (replacer.parent != nodeToDelete) {
				replacer.parent.right = null;
			} else {
				replacer.parent.left = null;
			}
			
			// relink new left side
			if (replacer == nodeToDelete.left) {
				
			} else {
				minNodeOfReplacer.left = nodeToDelete.left;
			}
			
			// relink right side
			replacer.right = nodeToDelete.right;
			
			// relink to new parent
			if (nodeToDelete.parent != null) {
				replacer.parent = nodeToDelete.parent;
				
				// link parent to replacer
				if (isLeftChild) {
					nodeToDelete.parent.left = replacer;
				} else {
					nodeToDelete.parent.right = replacer;
				}
			} else {
				// parent node of deletion node is null thus it was the root
				root = replacer;
				root.parent = null;
			}
			
			return true;
		}
	}
	
	/**
	 * Recursively goes as far right as possible then returns that node
	 * @param node Starting point
	 * @return Largest value in subtree
	 */
	private Node<T> getMaxNodeInSubtree(Node<T> node) {
		if (node.right == null) {
			return node;
		}
		return getMaxNodeInSubtree(node.right);
	}
	
	/**
	 * Recursively goes as far left as possible then returns that node
	 * @param node Starting point
	 * @return Smallest value in subtree
	 */
	private Node<T> getMinNodeInSubtree(Node<T> node) {
		if (node.left == null) {
			return node;
		}
		return getMinNodeInSubtree(node.left);
	}
	
	/**
	 * Searched for arg data returning one of its neighbors or null if not found
	 * @param datum key to search for
	 * @param elementBefore if true then returns the predecessor, else returns successor 
	 * @return null, predecessor or successor datum
	 */
	public T getNeighbor(T datum, boolean elementBefore) {
		Node<T> node = this.search(datum);
		// data was not found in tree & tree is not empty
		if (node == null && root != null) 
			return null;
		
		traverseInOrder(false);
		int index = this.binarySearch(datum, 0, traversedValues.size() - 1);
		
		// make sure the data was found in the ArrayList
		if (index != -1) {
			// get predecessor
			if (elementBefore && index - 1 >= 0)
				return traversedValues.get(index - 1);
			// get successor
			else if (!elementBefore && index + 1 < traversedValues.size())
				return traversedValues.get(index + 1);
		}
		// the data was not found in the traversedValues list
		return null;
	}
	
	/**
	 * Searches {@link #traversedValues} for the data recursively (assumes they're sorted least to greatest)
	 * using the binary search algorithm.
	 * {@link #traverseInOrder(boolean)} should be called before using this method
	 * @return the index of the value if found, else -1 if not found
	 */
	private int binarySearch(T datum, int rangeBegin, int rangeEnd) {
		int midpoint = (rangeEnd + rangeBegin) / 2;
		int comparison = datum.compareTo(traversedValues.get(midpoint));
		
		// failed to find datum that's being searched for
		if (rangeEnd < rangeBegin) {
			return -1;
		}
		// the data has been found
		else if (comparison == 0) {
			return midpoint;
		}
		// data would be in the 1st half
		else if (comparison < 0) {
			return binarySearch(datum, rangeBegin, midpoint - 1);
		} 
		// data would be in the 2nd half
		else if (comparison > 0) {
			return binarySearch(datum, midpoint + 1, rangeEnd);
		}
		// this will never be reached
		return -1;
	}
	
	/**
	 * Prints the entire tree level by level from root to the leaves roughly formatted
	 */
	public void printEachLevel() {
		ArrayList<ArrayList<Node<T>>> tree = new ArrayList<ArrayList<Node<T>>>();
		ArrayList<Node<T>> level1 = new ArrayList<Node<T>>();
		level1.add(root);
		tree.add(level1);
		int treeIndex = 0;
		
		// iterate through until all children are null
		while (true) {
			ArrayList<Node<T>> previousLevel = tree.get(treeIndex);
			ArrayList<Node<T>> nextLevel = new ArrayList<Node<T>>();
			
			double maxNodesAtLevel = Math.pow(2, treeIndex);
			boolean allLeavesAreNull = true;
			// check all the children in the previous level
			for (int nodeIndex = 0; nodeIndex < previousLevel.size(); nodeIndex++) {
				Node<T> node = previousLevel.get(nodeIndex);
				if (node == null) {
					nextLevel.add(null);
					nextLevel.add(null);
				} else {
					allLeavesAreNull = false;
					nextLevel.add(node.left);
					nextLevel.add(node.right);
				}
			}
			// end of iterating over next level of nodes array list
			if (allLeavesAreNull) {
				break;
			}
			tree.add(nextLevel);
			treeIndex++;
		}
		// finished getting all children nodes
		
		// iterate through the tree, printing each level formatted
		int maxWidth = (int)Math.pow(2, treeIndex);
		
		for (int index = 0; index < treeIndex; index++) {
			ArrayList<Node<T>> row = tree.get(index);
			int padding = (maxWidth - row.size() + 1) / 2;
			// left padding
			System.out.printf("%" + padding + "s", "");
			
			String line = "";
			for (Node<T> node : row) {
				if (node == null) {
					line += String.format("[] ");
				} else {
					line += node.toString() + " ";
				}
			}
			System.out.print(line);
			
			// right padding
			System.out.printf("%" + padding + "s\n", "");
		}
	}
}
