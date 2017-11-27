package binary_heap;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Holds a number of test cases to help make sure things are still working.
 * Inherits from UserInterface to use one of it's methods
 * @author j
 *
 */
public class UnitTest extends UserInterface {
	public static void main(String[] args) {
		UnitTest ut = new UnitTest();
		
		ut.testCase1();
		System.out.println("---------------------");
		ut.testCase2();
		System.out.println("---------------------");
		ut.createHeapOfIncrementingIntegers();
		System.out.println("---------------------");
		ut.testCase3();
		System.out.println("---------------------");
		ut.testCase4();
	}
	
	/**
	 * Expected:
	 *     2
	 *    /
	 *   1
	 */
	public void testCase1() {
		MaxHeap<Integer> tree = new MaxHeap<Integer>();
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.remove();
		tree.printEachLevel();
		
//		Integer[] expectation = {2, 1};
//		assert(tree.getHeap().equals(expectation));
	}
	
	/**
	 * Same heap as heap from visualization
	 */
	public void testCase2() {
		Queue<Integer> l = new LinkedList<Integer>();
		for (int i = 1; i <= 31; i++)
			l.add(i);
		
		MaxHeap<Integer> tree = new MaxHeap<Integer>(l);
		System.out.printf("Number of swaps: %d\n", tree.getNumberOfSwaps());
		// min heap visualization optimal construction has 26 swaps for a heap including the range
		// values [1, 31]
		tree.printEachLevel();
	}
	
	/**
	 * Test behavior of empty heap
	 */
	public void testCase3() {
		MaxHeap<Integer> tree = new MaxHeap<Integer>();
		tree.remove();
		tree.peek();
		tree.insert(5);
		System.out.printf("root = %d\n", tree.peek());
		tree.remove();
		tree.remove();
	}
	
	public void testCase4() {
		MaxHeap<Integer> tree = new MaxHeap<Integer>();
		tree.insert(10);
		System.out.printf("root = %d\n", tree.peek());
		tree.insert(30);
		tree.insert(5);
		tree.insert(31);
		tree.insert(-1);
		tree.insert(12);
		tree.insert(144);
		tree.insert(29);
		tree.insert(30); // duplicate value
		tree.insert(10); // duplicate value
		tree.printEachLevel();
		tree.remove();
		tree.remove();
		tree.remove();
		tree.printEachLevel();
	}
}
