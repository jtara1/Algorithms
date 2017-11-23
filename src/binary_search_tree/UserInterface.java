package binary_search_tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Command line driven user interface to do operations with the binary search tree with integers as the data
 * @author j
 *
 */
public class UserInterface {
	
	private Scanner scanner = new Scanner(System.in);
	private BST<Integer> tree = new BST<Integer>();
	
	private String menuOptions = 
			"I Insert a value\n"
			+ "D Delete a value\n"
			+ "P Find predecessor\n"
			+ "S Find successor\n"
			+ "E Exit the program\n"
			+ "H Display the message";
	
	/**
	 * Begin running the UI
	 */
	public void start() {
		System.out.println("Please enter the initial sequence of values:");
		
		// read the input line, split it by the empty space, parse for int, then insert the int into the tree
		for (String numberAsString : scanner.nextLine().split(" ")) {
			tree.insert(Integer.parseInt(numberAsString));
		}
		// print pre order
		tree.traversePreOrder(true);
		// print in order
		tree.traverseInOrder(true);
		// print post order 
		tree.traversePostOrder(true);
		
		commandMenu();
		return;
	}
	
	/**
	 * Command menu interface for the user
	 */
	public void commandMenu() {
		while (true) {
			System.out.print("Command? ");
			
			String input = scanner.nextLine().toUpperCase().trim();
			
			if (input.equals("H")) {
				// display help menu
				System.out.println(menuOptions);
			} else if (input.equals("E")) {
				// exit program
				System.out.println("Thanks for using my program! -jtara1");
				return;
			} else {
				// do an operation with the binary search tree
				String[] inputValues = input.split(" ");
				Queue<Integer> values = new LinkedList<Integer>();
				
				// add each integer from cli into queue
				for (int i = 1; i < inputValues.length; i++) {
					values.add(Integer.parseInt(inputValues[i]));
				}
				
				// determine which operation is to be done
				switch (inputValues[0]) {
				// insert arbitrary number of integers
				case "I":
					if (!tree.insert(values))
						System.out.println("The number(s) entered were already in the tree.");
					else
						tree.traverseInOrder(true);
					break;
				// delete arbitrary number of integers
				case "D":
					if (!tree.delete(values))
						System.out.println("Did not find the number(s) entered.");
					else
						tree.traverseInOrder(true);
					break;
				// print the predecessor
				case "P":
					System.out.println(tree.getNeighbor(values.remove(), true));
					break;
				// print the successor
				case "S":
					System.out.println(tree.getNeighbor(values.remove(), false));
					break;
				}
			}
		}
	}
}
