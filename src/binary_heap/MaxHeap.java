package binary_heap;

import java.util.Queue;

/**
 * Max Heap implementation which is a type of Binary tree
 * @author James Taracevicz
 *
 * @param <T> Data type of the keys that are stored in the array (the {@link #heap} of {@link #MaxHeap})
 */
public class MaxHeap<T extends Comparable<T>> {
	
	/**
	 * The array on keys of the heap
	 */
	private T[] heap;
	
	/**
	 * The index of the last non-null key in the heap
	 */
	private int lastIndex;
	
	/**
	 * Default initial capacity of {@link #heap}
	 */
	private static final int INITIAL_CAPACITY = 100;
	
	/**
	 * Counter for the number of swaps done with this heap
	 */
	private int numberOfSwaps = 0;
	
	/**
	 * This is multiplied by the {@link #heap} length everything
	 * a larger array is needed.
	 */
	private int scalingHeapGrowth = 2;
	
	/**
	 * Calls other constructor with default init capacity
	 */
	public MaxHeap() {
		this(INITIAL_CAPACITY);
	}
	
	/**
	 * Create a MaxHeap
	 * @param initCapacity initial size of the array representing the tree
	 */
	public MaxHeap(int initCapacity) {
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Comparable[initCapacity];
		this.heap = temp;
		lastIndex = 0;
	}
	
	/**
	 * Creation of a max heap using the optimal construction O(n)
	 * @param queue The collection of keys that'll be put in the heap
	 */
	public MaxHeap(Queue<T> queue) {
		this(queue.size());                           // k0
		
		int index = 0;                                // k1
		lastIndex = queue.size() - 1;                 // k2
		// insert each element from the queue into the heap successively
		while (!queue.isEmpty()) {                    // n
			heap[index] = queue.remove();             // k3
			index++;                                  // k4
		}
		
		// optimal construction
		for (int i = lastIndex / 2; i > 0; i--) {     // n/2
			heapifyDownwards(i);                      // k11 * log(n)
		}
		heapifyDownwards(0);                          // k12 * log(n)
	}
	
	/**
	 * Insert a value / key into the heap
	 * @param key TransitionCost to be inserted
	 */
	public void insert(T key) {
		// avoid incrementing lastIndex when root is null
		if (heap[lastIndex] != null) {
			lastIndex++;
		}
		checkBounds(lastIndex);
		heap[lastIndex] = key;
		heapifyUpwards(lastIndex);
	}
	
	/**
	 * Delete root replacing it with the key at the {@link #lastIndex}
	 * @return (Removed) root's key
	 */
	public T remove() {
		T oldRoot = heap[0];
		
		// find replacer
		T replacer = heap[lastIndex];
		
		// remove replacer from orig position
		heap[lastIndex] = null;
		lastIndex -= lastIndex == 0 ? 0 : 1;
		
		// replace root with replacer
		heap[0] = replacer;
		
		heapifyDownwards(0);
		return oldRoot;
	}
	
	/**
	 * @return The key of the root of the tree
	 */
	public T peek() {
		return heap[0];
	}
	
	/**
	 * Removes all the keys in the heap keeping the capacity/size of the heap
	 */
	public void clear() {
		heap = (T[]) new Comparable[lastIndex + 1];
		lastIndex = 0;
	}
	
	/**
	 * Swap places of the two values in the {@link #heap} at locations
	 * index1 and index2
	 * @param index1
	 * @param index2
	 */
	private void swap(int index1, int index2) {
		T temp = heap[index1];
		heap[index1] = heap[index2];
		heap[index2] = temp;
		numberOfSwaps++;
	}
	
	/**
	 * Checks if given index in within bounds the of {@link #heap}
	 * if it's out of bounds, resize the array
	 * @param index
	 */
	private void checkBounds(int index) {
		if (index >= heap.length) {
			// out of bounds; create larger heap containing current heap
			int newSize = scalingHeapGrowth * heap.length + 1;
			// up size heap copying old values from heap into the new heap
			T[] temp = heap;
			heap = (T[]) new Comparable[newSize];
			System.arraycopy(temp, 0, heap, 0, newSize);
		}
	}
	
	/**
	 * 
	 * @param index
	 * @return The sibling index of this index or null if this index
	 * is the root
	 */
	private Integer getBrotherIndex(int index) {
		if (index == 0)
			return null;
		return index + (index % 2 == 0 ? -1 : 1);
	}
	
	/**
	 * 
	 * @param index
	 * @return The parent's index of this (parameter) index or null
	 * if there is no parent 
	 */
	private Integer getParentIndex(int index) {
		int parent = (index - 1) / 2;
		return parent < 0 ? null : parent;
	}
	
	/**
	 * Gets the left xor right child of the node at the given index
	 * @param index
	 * @param leftChild if true, return the left child, else return right child
	 * @return The child key
	 */
	private T getChild(int index, boolean leftChild) {
		return heap[getChildIndex(index, leftChild)];
	}
	
	/**
	 * Get the index of one of the children specified by the boolean leftChild
	 * @param index
	 * @param leftChild
	 * @return
	 */
	private int getChildIndex(int index, boolean leftChild) {
		int childIndex = 2 * index + (leftChild ? 1 : 2);
		checkBounds(childIndex);
		return childIndex;
	}
	
	/**
	 * Used to get the child index without possibly expanding the heap array.
	 * It's possible the child index is out of bounds of the array or points to null.
	 * @param index
	 * @param leftChild
	 * @param expandIfNeeded if true expands the {@link heap} if needed to avoid out of bounds exception
	 * @return index of the child
	 */
	private int getChildIndex(int index, boolean leftChild, boolean expandIfNeeded) {
		int childIndex = 2 * index + (leftChild ? 1 : 2);
		if (expandIfNeeded)
			checkBounds(childIndex);
		return childIndex;
	}
	
	/**
	 * Of all the children, return the index of the child whose value / key is
	 * the largest
	 * @param index
	 * @return if there are no children, -1 is returned
	 */
	private int getLargestChildIndex(int index) {
		int leftChild = getChildIndex(index, true, false);
		// there's no children
		if (leftChild > lastIndex)
			return -1;
		
		int rightChild = getChildIndex(index, false, false);
		// there's no rightChild
		if (rightChild > lastIndex)
			return leftChild;
		
		// return the larger of the two
		return heap[leftChild].compareTo(heap[rightChild]) >= 0 ? leftChild : rightChild;
	}
	
	/**
	 * Moves a value in the {@link #heap} as far down as possible
	 * @param index
	 */
	private void heapifyDownwards(int index) {
		int largestChild = getLargestChildIndex(index);             // k6
		// no children of node at this index
		if (largestChild == -1) {                                   // k7
			return;                                                 // k8
		}
		// largest child is larger than its parent, swap and reheapify downwards
		else if (heap[largestChild].compareTo(heap[index]) > 0) {   // k9
			swap(largestChild, index);                              // k10
			heapifyDownwards(largestChild); // recursive            // log(|Nodes|)
		}
	}
	
	/**
	 * Moves a value in the {@link #heap} upwards as far as possible
	 * @param index
	 */
	private void heapifyUpwards(int index) {
		Integer parentIndex = getParentIndex(index);
		
		// reached the root; it has no parent
		if (parentIndex == null) {
			return;
		}
		// key at this index is greater than key at parent index
		if (heap[index].compareTo(heap[parentIndex]) > 0) {
			swap(index, parentIndex);
			heapifyUpwards(parentIndex);
		}
		// key is less than its parent's key
		return;
	}
	
	/**
	 * @return The depth of the tree
	 */
	public int getDepth() {
		int depth = 1;
		int ancestorIndex = lastIndex;
		while (ancestorIndex > 0) {
			ancestorIndex = (ancestorIndex - 1) / 2;
			depth++;
		}
		return depth;
	}
	
	/**
	 * @return {@link #numberOfSwaps}
	 */
	public int getNumberOfSwaps() {
		return numberOfSwaps;
	}
	
	/**
	 * Print each level from top to bottom roughly formatted
	 */
	public void printEachLevel() {
		int depth = getDepth();
		double maxWidth = Math.pow(2, depth - 1);
		
		for (int i = 0, level = 0; i <= lastIndex; i++) {
			System.out.printf("%" + (depth - level) + "s%d", " ", heap[i]);
			// last node in level
			if (Math.pow(2, level + 1) - 2 == i) {
				System.out.println();
				level++;
			}
		}
		System.out.println();
	}
	
	/**
	 * @return {@link #heap}
	 */
	public T[] getHeap() {
		return heap;
	}
	
	@Override
	/**
	 * O(n) to print the heap
	 */
	public String toString() {
		String arrayRepresentation = "[";
		for (int i = 0; i <= lastIndex; i++) {
			arrayRepresentation += heap[i] + (i == lastIndex ? "]" : ", ");
		}
		return arrayRepresentation;
	}
}
