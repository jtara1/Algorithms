package binary_search_tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class UnitTest {
	public static void main (String[] args) {
		UnitTest ut = new UnitTest();
		
		// run tests
		ut.manualOperationsTest();
		System.out.println("-----------------");
		ut.testCase1();
		System.out.println("-----------------");
		ut.testCase2();
		System.out.println("-----------------");
		ut.testCaseFromProfessor();
	}
	
	public void manualOperationsTest() {
		Node<Integer>node1 = new Node<Integer>(5);
		Node<Integer>node2 = new Node<Integer>(4);
		Node<Integer>node3 = new Node<Integer>(6);
		Node<Integer> root = node1;
		root.left = node2;
		root.right = node3;
		
		node2.parent = root;
		node3.parent = root;
				
		// prints the value of the root (5)
		System.out.println(root);
		// prints the debug statement defined in Node class
		System.out.println(root.toStringDebug());
		System.out.println(root.left.toStringDebug());
		System.out.println(root.right.toStringDebug());
	}
	
	public void testCase1() {
		BST<Integer> tree = new BST<Integer>();
		
		// insert set 1
		Integer[] a1 = {5, 4, 6};
		Queue<Integer> q = new LinkedList<Integer>(Arrays.asList(a1));
		tree.insert(q);
		
		// print
		tree.traverseInOrder(true);
		System.out.println(tree.getRoot().toStringDebug());
		
		// insert set 2
		Integer[] a2 = {7, 8, 9};
		q = new LinkedList<Integer>(Arrays.asList(a2));
		tree.insert(q);
		
		// insert set 3
		Integer[] a3 = {388, 99, 33};
		q = new LinkedList<Integer>(Arrays.asList(a3));
		tree.insert(q);
		
		// insert set 4
		Integer[] a4 = {10, 14, -14, 22};
		q = new LinkedList<Integer>(Arrays.asList(a4));
		tree.insert(q);
		
		// print root
		System.out.println(tree.getRoot().toStringDebug());
		
		// delete set 5
		Integer[] a5 = {-14, 4, 5};
		q = new LinkedList<Integer>(Arrays.asList(a5));
		tree.delete(q);
		
		// print root
		System.out.println(tree.getRoot().toStringDebug());
	}
	
	/**
	 *		10
	 *		/\
	 *	   5  50
	 *	      /\
	 *	  	 40 55
	 *		  \
	 *		  45
	 */
	public void testCase2() {
		BST<Integer> tree = new BST<Integer>();
		
		tree.insert(10);
		tree.insert(5);
		tree.insert(50);
		tree.insert(40);
		tree.insert(55);
		tree.insert(45);
		tree.traverseInOrder(true);
		
		// delete node with two children
		tree.delete(50);
		tree.traverseInOrder(true);
		
		// delete root node
		tree.delete(10);
		
		tree.traverseInOrder(true);
		
		tree.printEachLevel();
	}
	
	public void testCaseFromProfessor() {
		BST<Integer> tree = new BST<Integer>();
		
		// insert set 1
		Integer[] a1 = {51, 29, 68, 90, 36, 40, 22, 59, 44, 99, 77, 60, 27, 83, 15, 75, 3};
		Queue<Integer> q = new LinkedList<Integer>(Arrays.asList(a1));
		tree.insert(q);
		
		tree.traversePreOrder(true);
		tree.traverseInOrder(true);
		tree.traversePostOrder(true);
		
		tree.insert(88);
		tree.traverseInOrder(true);
		
		tree.insert(42);
		tree.traverseInOrder(true);
		
		tree.insert(22);
		tree.traverseInOrder(true);
		
		tree.delete(44);
		tree.traverseInOrder(true);
		
		tree.delete(90);
		tree.traverseInOrder(true);
		
		tree.delete(70);
		tree.traverseInOrder(true);
		
		tree.delete(68);
		tree.traverseInOrder(true);
		
		tree.printEachLevel();

		System.out.println(tree.getNeighbor(75, false));
		
		System.out.println(tree.getNeighbor(99, true));
	}
}
