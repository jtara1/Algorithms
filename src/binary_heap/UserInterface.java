package binary_heap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

/**
 * Interface for the user to use to interact with 
 * the MaxHeap.
 * @author j
 *
 */
public class UserInterface {
	Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		ui.start();
	}
	
	public void start() {
		menu();
	}
	
	private void menu() {
		String options = "[1] 20 sets of 100 randomly generated integers\n"
				+ "[2] Fixed integer values 1-100\n"
				+ "Enter choice: ";
		
		System.out.print(options);
		
		int input = scanner.nextInt();
		switch (input) {
		case 1:
			createTwentyRandomHeaps();
			break;
		case 2:
			createHeapOfIncrementingIntegers();
			break;
		default:
			System.out.println("invalid input");
			break;
		}
	}
	
	/**
	 * Option 1 in the menu
	 */
	private void createTwentyRandomHeaps() {
		double averageSuccessiveSwaps = 0, averageOptimalSwaps = 0;
		
		for (int i = 0; i < 20; i++) {
			Queue<Integer> queue2 = getQueueOfRandomIntegers();
			Queue<Integer> queue1 = new LinkedList<Integer>(queue2);
			// optimal construction
			MaxHeap<Integer> heap2 = new MaxHeap<Integer>(queue1);
			averageOptimalSwaps += heap2.getNumberOfSwaps();
			
			// successive insertion construction
			MaxHeap<Integer> heap1 = new MaxHeap<Integer>();
			while (!queue2.isEmpty()) {
				heap1.insert(queue2.remove());
			}
			averageSuccessiveSwaps += heap1.getNumberOfSwaps();
		}
		
		averageSuccessiveSwaps /= 20;
		averageOptimalSwaps /= 20;
		
		System.out.println("Average swaps for successive insertions: " + averageSuccessiveSwaps);
		System.out.println("Average swaps for optimal method: " + averageOptimalSwaps);
	}
	
	/**
	 * Option 2 in the menu
	 */
	protected void createHeapOfIncrementingIntegers() {
		Queue<Integer> queue = new LinkedList<Integer>();
		MaxHeap<Integer> heap = new MaxHeap<Integer>();
		// successive insertion
		for (int i = 1; i <= 100; i++) {
			heap.insert(i);
			queue.add(i);
		}
		// print the heap
		System.out.printf("Successive insertions: %s\n", heap);
		// print the number of swap operations
		System.out.printf("Number of swaps: %s\n", heap.getNumberOfSwaps());
		
		// rm root 10 times then print
		for (int i = 0; i < 10; i++) {
			heap.remove();
		}
		System.out.printf("Heap after 10 deletions: %s\n", heap);
		
		System.out.printf("==============================\n");
		
		// optimal construction
		MaxHeap<Integer> heap2 = new MaxHeap<Integer>(queue);
		System.out.printf("Optimal construction: %s\n", heap2);
		System.out.printf("Number of swaps: %s\n", heap2.getNumberOfSwaps());
		// rm root 10 times then print
		for (int i = 0; i < 10; i++) {
			heap2.remove();
		}
		System.out.printf("Heap after 10 deletions: %s\n", heap2);
	}
	
	/**
	 * 
	 * @return Queue containing numbers [1, 100] rearranged in a range order
	 */
	private Queue<Integer> getQueueOfRandomIntegers() {
		ArrayList<Integer> numbs = new ArrayList<Integer>();
		// make list with numbers in the range [1, 100] in it
		for (int i = 1; i <= 100; i++) {
			numbs.add(i);
		}
		
		Random random = new Random();
		Queue<Integer> queue = new LinkedList<Integer>();
		
		while (numbs.size() > 1) {
			int randomIndex = random.nextInt(numbs.size() - 1);
			queue.add(numbs.get(randomIndex));
			numbs.remove(randomIndex);
		}
		queue.add(numbs.get(0));
		
		return queue;
	}
}
