package gaussian_elimination;

import java.lang.Math;
import java.lang.Exception;

public class ScaledPartialPivotingGE {
	private Double[][] matrix = null;
	
	public static void main(String[] args) {
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
		ge.printVector(x);
	}
	
	public ScaledPartialPivotingGE(int rows, int columns) {
		// create matrix of size rows x columns with random values
	}
	
	public ScaledPartialPivotingGE(Double[][] matrix) throws Exception {
		if (matrix.length >= matrix[0].length - 1)
			this.matrix = matrix;
		else
			throw new Exception("invalid matrix size; requirements: m >= n - 1 for a m x n matrix");
	}
	
//	public static Double[][] getRandomMatrix(int rows, int columns, Double valueRangeBegin, Double valueRangeEnd) {
//		for (int i = 0; i < rows; i++) {
//			Double[] row = new Double[rows];
//			for (int j = 0; j < columns; j++) {
//				
//			}
//		}
//	}
	
	public Number[] solve() {	
		// the indices of the rows in the matrix
		Integer[] matrixRowIndices = new Integer[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			matrixRowIndices[i] = i;
		}
		
		// largest absolute value in each row
		Double[] rowMaxValues = new Double[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			int index = getMaxValueIndex(matrix[i]);
			rowMaxValues[i] = matrix[i][index];
		}
		
		// begin iterative steps
		for (int columnIndex = 0; columnIndex < matrix[0].length - 1; columnIndex++) {
			Double[] ratios = new Double[matrix.length - columnIndex];

			// calculate ratios
			for (int ratiosIndex = 0, startingIndex = columnIndex; ratiosIndex < ratios.length; ratiosIndex++, startingIndex++) {
				Double rowLeadingCoefficient = matrix[matrixRowIndices[startingIndex]][columnIndex];
				Double rowMaxValue = rowMaxValues[startingIndex];
				
				ratios[ratiosIndex] = rowLeadingCoefficient / rowMaxValue;
			}
			
			// swap pivot & maxValues values to front; this is not done during the last iteration
			if (columnIndex != matrix[0].length - 2) {
				int largestValueIndex = getMaxValueIndex(ratios);
				swap(rowMaxValues, columnIndex, largestValueIndex);
				swap(matrixRowIndices, columnIndex, largestValueIndex);
			}
			
			Double[] pivotRow = matrix[matrixRowIndices[columnIndex]];
			
			// multiply rows below pivot to make coefficients of the column 0
			for (int i = columnIndex + 1; i < matrix.length; i++) {
				Double multiplier = matrix[matrixRowIndices[i]][columnIndex] / pivotRow[columnIndex];
				subtract(multiplier, matrix[matrixRowIndices[columnIndex]], matrix[matrixRowIndices[i]]);
			}
			
		}
		
		return backSubstitute(matrixRowIndices);
	}
	
	/**
	 * @param vector
	 * @return index of the largest value in the array
	 */
	private int getMaxValueIndex(Double[] vector) {
		Double maxValue = Math.abs(vector[0]);
		int indexOfMaxValue = 0;
		for (int i = 0; i < vector.length; i++) {
			Double thisElement = Math.abs(vector[i]); 
			if (thisElement > maxValue) {
				maxValue = thisElement;
				indexOfMaxValue = i;
			}
		}
		return indexOfMaxValue;
	}
	
	/**
	 * Swaps the position of the two values located at arg index1 and arg index2 in arg vector
	 * @param vector
	 * @param index1
	 * @param index2
	 */
	private void swap(Number[] vector, int index1, int index2) {
		Number temp = vector[index1];
		vector[index1] = vector[index2];
		vector[index2] = temp;
	}
	
	/**
	 * Mutates vector2 such that vector2 = multiplier * vector1 - vector2
	 */
	private void subtract(Double multiplier, Double[] vector1, Double[] vector2) {
		for (int i = 0; i < vector2.length; i++) {
			vector2[i] = vector1[i] * multiplier - vector2[i]; 
		}
	}
	
	/**
	 * Attempts to solve a matrix with back substitution.
	 * @param indices Indicates the order of each row that was used as a pivot in the elimination method 
	 * @return The vector x
	 */
	private Double[] backSubstitute(Integer[] indices) {
		Double[] x = new Double[matrix[0].length - 1];
		
		for (int rowIndex = indices.length - 1; rowIndex >= 0; rowIndex--) {
			Double[] row = matrix[indices[rowIndex]];
			Double rowSum = row[row.length - 1];
			Double divisor = null;
			Integer indexOfXToSolveFor = null;
			// iterate over each column in the row
			for (int columnIndex = 0; columnIndex < row.length - 1; columnIndex++) {
				Double coefficient = row[columnIndex];
				if (coefficient == 0.0) {
					continue;
				} else {
					if (x[columnIndex] == null) {
						// solve for this x
						divisor = coefficient;
						if (indexOfXToSolveFor != null) {
							// something is not right
						} else {
							indexOfXToSolveFor = columnIndex;
						}
					} else {
						// already found this x
						rowSum -= coefficient * x[columnIndex];
					}
				}
			}
			// end of iterating over columns of the row
			x[indexOfXToSolveFor] = rowSum / divisor;
		}
		return x;
	}
	
	/**
	 * Print each element in arg matrix
	 * @param matrix
	 */
	public void printMatrix(Object[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Print each element in arg vector
	 * @param vector
	 */
	public void printVector(Object[] vector) {
		for (int i = 0; i < vector.length; i++) {
			System.out.print(vector[i] + " ");
		}
		System.out.println();
	}
}
