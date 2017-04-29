package gaussian_elimination;

import java.lang.Math;
import java.util.Random;
import java.lang.Exception;

/**
 * Solve a system of linear equations using the 
 * Scaled Partial Pivoting with Gaussian Elimination method
 * @author James T
 *
 */
public class ScaledPartialPivotingGE {
	private Double[][] matrix = null;
//	private Double[] x = null;
	
	public static void main(String[] args) {
		UnitTest ut = new UnitTest();
		ut.testCase5();
	}
	
	
	public ScaledPartialPivotingGE(int rows, int columns, Double rangeStart, Double rangeEnd) {
		// create matrix of size rows x columns with random values
		matrix = getRandomMatrix(rows, columns, rangeStart, rangeEnd);
	}
	
	public ScaledPartialPivotingGE(Double[][] matrix) throws Exception {
		if (matrix.length >= matrix[0].length - 1)
			this.matrix = matrix;
		else
			throw new Exception("invalid matrix size; requirements: m >= n - 1 for a m x n matrix");
	}
	
	public static Double[][] getRandomMatrix(int rows, int columns, Double valueRangeBegin, Double valueRangeEnd) {
		Random random = new Random();
		
		Double[][] matrix = new Double[rows][];
		// iterate over the rows then the columns & insert a random value
		for (int i = 0; i < rows; i++) {
			Double[] row = new Double[columns];
			for (int j = 0; j < columns; j++) {
				// get random value in the range [valueRangeBegin, valueRangeEnd]
				Double randomValue = random.nextDouble();
				randomValue *= valueRangeBegin - valueRangeEnd;
				randomValue += valueRangeEnd;
				
				// debug
				randomValue = new Double((randomValue.intValue()));
				
				row[j] = randomValue; 
			}
			matrix[i] = row;
		}
		
		return matrix;
	}
	
	public Number[] solve() {	
		printMatrix(matrix);
		
		// the indices of the rows in the matrix
		Integer[] indexVector = new Integer[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			indexVector[i] = i;
		}
		
		// largest absolute value in each row
		Double[] scaleVector = new Double[matrix.length];
		for (int row = 0; row < matrix.length; row++) {
			int index = getMaxValueIndex(matrix[row]);
			scaleVector[row] = matrix[row][index];
		}
		
		// iterate over all rows using this as the column index
		for (int columnIndex = 0; columnIndex < matrix.length - 1; columnIndex++) {
//			printMatrix(matrix);
			Double[] ratios = new Double[matrix.length - columnIndex];

			// calculate ratios
			for (int ratiosIndex = 0, startingIndex = columnIndex; ratiosIndex < ratios.length; ratiosIndex++, startingIndex++) {
				Double rowLeadingCoefficient = matrix[indexVector[startingIndex]][columnIndex];
				Double rowMaxValue = scaleVector[startingIndex];
				
				ratios[ratiosIndex] = rowLeadingCoefficient / rowMaxValue;
			}
			
			// swap pivot & maxValues values towards front; this is not done during the last iteration
			if (columnIndex != matrix.length - 1) {
				int largestValueIndex = getMaxValueIndex(ratios) + columnIndex;
				swap(scaleVector, columnIndex, largestValueIndex);
				swap(indexVector, columnIndex, largestValueIndex);
			}
			
			Double[] pivotRow = matrix[indexVector[columnIndex]];
			// pivotRow is matrix[indexVector[columnIndex]]
			// one of the nonPivotRow = matrix[indexVector[i]]
			
			// multiply all rows except pivot to make leading coefficients of the column 0
			for (int i = columnIndex + 1; i < matrix.length; i++) {
				Double leadingCoefficient = matrix[indexVector[i]][columnIndex];
				Double pivotLeadingCoefficient = pivotRow[columnIndex];
				
				Double multiplier = leadingCoefficient / pivotLeadingCoefficient;
				// arg3 = arg1 * arg2 - arg3;
				subtract(multiplier, pivotRow, matrix[indexVector[i]]);
			}
			printMatrix(matrix);
			
		}
//		printMatrix(matrix);
		Double[] x = null;
		try {
			x = backSubstitute(indexVector);
		} catch (Exception e) {
			// it's possible there are no solutions
			e.printStackTrace();
		}
		return x;
	}
	
	/**
	 * @param vector
	 * @return index of the largest value in the array
	 */
	private int getMaxValueIndex(Double[] vector) {
		Double maxValue = Math.abs(vector[0]);
		int indexOfMaxValue = 0;
		for (int i = 1; i < vector.length; i++) {
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
	 * @param indexVector Indicates the order of each row that was used as a pivot in the elimination method 
	 * @return The vector x
	 */
	private Double[] backSubstitute(Integer[] indexVector) throws Exception {
		Double[] x = new Double[matrix[0].length - 1];
		
		// iterate through each row in the matrix
		for (int rowIndex = indexVector.length - 1; rowIndex >= 0; rowIndex--) {
			Double[] row = matrix[indexVector[rowIndex]];
			Double rowSum = row[row.length - 1];
			Double divisor = null;
			Integer indexOfXToSolveFor = null;
			
			// iterate over each column in the row except the last
			for (int columnIndex = 0; columnIndex < row.length - 1; columnIndex++) {
				Double coefficient = row[columnIndex];
				// if the coefficient is 0.0 with some possible floating point error
				if (coefficient > -9E-13 && coefficient < 9E-13) {
					continue;
				} else {
					if (x[columnIndex] == null) {
						if (divisor == null) {
							// solve for this x
							divisor = coefficient;
							indexOfXToSolveFor = columnIndex;
						} else {
							// multiple values of x need to be "solved" for; thus there are infinitely many solutions
							throw new Exception("The matrix contains infinite solutions.");
						}
					} else {
						// already found this x
						rowSum -= coefficient * x[columnIndex];
					}
				}
			}
			// end of iterating over columns of the row
			if (divisor == null && !(rowSum > -9E-13 && rowSum < 9E-13)) {
				// avoids attempting to divide by null
				throw new Exception("The matrix has no solution as 0.0 != constant");
			} else if (divisor == null) {
				// the entire row is zero
				continue;
			} else {
				x[indexOfXToSolveFor] = rowSum / divisor;
			}
		}
		return x;
	}
	
	/**
	 * Print each element in arg matrix formatted
	 * @param matrix
	 */
	public void printMatrix(Number[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				String printStr = String.format("%10.2f ", matrix[i][j]);
				System.out.print(printStr);
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
	
	/**
	 * Prints each element in arg vector with "xi = " to indicate which value is it
	 * @param vector
	 */
	public void printSolution(Object[] vector) {
		for (Integer i = 0; i < vector.length; i++) {
			String xNumber = Integer.toString(i + 1);
			System.out.println("x" + xNumber + " = " + (vector[i] == null ? "free variable" : vector[i]));
		}
	}
	
	public Double[][] getMatrix() {
		return matrix;
	}
}
