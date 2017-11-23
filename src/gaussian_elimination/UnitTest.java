package gaussian_elimination;

/**
 * Includes a number of test cases to check the functionality of ScaledPartialPivotingGE
 * @author James T
 *
 */
public class UnitTest {
	public static void main(String[] args) {
		UnitTest ut = new UnitTest();
		ut.testCase1();
		ut.testCase2();
		ut.testRandomValues(100, 101);
	}
	
	/**
	 * 2 x 3
	 */
	public void testCase1() {
		// basic test case
		Double[] row1 = {2.0, 4., 5.};
		Double[] row2 = {-1., 2., 3.};
		Double[][] matrix = {row1, row2};
		
		ScaledPartialPivotingGE ge = null;
		try {
			ge = new ScaledPartialPivotingGE(matrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ge.printMatrix(matrix);
		Number[] x = ge.solve();
		System.out.println("-------");
		ge.printMatrix(matrix);
		System.out.println("-------");
		ge.printSolution(x);
	}
	
	/**
	 * 3 x 4
	 */
	public void testCase2() {
		Double[] row1 = {0., 9., 2., 2.};
		Double[] row2 = {7., 0., 6., 0.};
		Double[] row3 = {1., 6., 4., 6.};
		Double[][] matrix = {row1, row2, row3};
		
		ScaledPartialPivotingGE ge = null;
		try {
			ge = new ScaledPartialPivotingGE(matrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ge.printMatrix(matrix);
		Number[] x = ge.solve();
		ge.printSolution(x);
	}
	
	/**
	 * 3 x 4
	 */
	public void testCase3() {
		Double[] row1 = {9., 7., 3., 3.};
		Double[] row2 = {5., 0., 6., 4.};
		Double[] row3 = {5., 8., 6., 8.};
		Double[][] matrix = {row1, row2, row3};
		
		ScaledPartialPivotingGE ge = null;
		try {
			ge = new ScaledPartialPivotingGE(matrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ge.printMatrix(matrix);
		Number[] x = ge.solve();
		ge.printSolution(x);
	}
	
	/**
	 * 3 x 4
	 */
	public void testCase4() {
		Double[] row1 = {4., 9., 4., 7.};
		Double[] row2 = {9., 7., 3., 2.};
		Double[] row3 = {7., 7., 9., 5.};
		Double[][] matrix = {row1, row2, row3};
		
		ScaledPartialPivotingGE ge = null;
		try {
			ge = new ScaledPartialPivotingGE(matrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ge.printMatrix(matrix);
		Number[] x = ge.solve();
		ge.printSolution(x);
	}
	
	/**
	 * 5 x 6
	 */
	public void testCase5() {
		Double[] row1 = {4., 4., 9., 6., 5., 1.};
		Double[] row2 = {4., 8., 2., 2., 3., 6.};
		Double[] row3 = {3., 1., 6., 0., 3., 7.};
		Double[] row4 = {7., 9., 0., 2., 8., 4.};
		Double[] row5 = {2., 2., 5., 3., 0., 4.};
		
		Double[][] matrix = {row1, row2, row3, row4, row5};
		
		ScaledPartialPivotingGE ge = null;
		try {
			ge = new ScaledPartialPivotingGE(matrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ge.printMatrix(matrix);
		Number[] x = ge.solve();
		ge.printSolution(x);
	}
	
	public void testRandomValues(int rows, int columns) {
		// random values
		ScaledPartialPivotingGE ge = null;
		try {
			ge = new ScaledPartialPivotingGE(rows, columns, -100., 100.);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Number[] x = ge.solve();
		System.out.println("-------");
		ge.printSolution(x);
	}
}
