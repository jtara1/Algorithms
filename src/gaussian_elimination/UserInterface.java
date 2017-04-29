package gaussian_elimination;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Command line driven user interface to do operations with the binary search tree with integers as the data
 * @author James T
 *
 */
public class UserInterface {
	
	private Scanner scanner = new Scanner(System.in);
	
	private String menuOptions = 
			"Enter the coefficients of a system of linear equations to solve:\n"
			+ "[1] command line\n"
			+ "[2] loading from a file\n"
			+ "[3] randomly generated coefficients";
	
	/**
	 * Begin running the UI
	 */
	public void start() {
//		System.out.print("Enter the number of equations (n): ");
//		int rows = scanner.nextInt();

		try {
			menu();
		} catch (Exception e) {
			// matrix could possibly have infinite solutions or no solution
			e.printStackTrace();
		}
	}
	
	public void menu() throws Exception {
		while (true) {
			System.out.println(menuOptions);
			
			ScaledPartialPivotingGE ge = null;
			switch (scanner.nextInt()) {
			case 1:
				// get coefficients via CLI
				Double[][] matrix = listToMatrix(getRowsFromCommandLine());
				// solve & print solutions
				ge = new ScaledPartialPivotingGE(matrix);
				Number[] x = ge.solve();
				ge.printSolution(x);
				return;
			case 2:
				// load coefficients via file
				matrix = listToMatrix(getRowsFromFile());
				ge = new ScaledPartialPivotingGE(matrix);
				x = ge.solve();
				ge.printSolution(x);
				return;
			case 3:
				// generate coefficients as random values
				ge = getNumberOfEquations();
				x = ge.solve();
				ge.printSolution(x);
				return;
			default:
				System.out.println("Invalid input entered.");
				break;
			}
		}
	}
	
	public List<String> getRowsFromCommandLine() {
		List<String> rowsAsStrings = new ArrayList<String>();
		System.out.println("Enter the coefficients for each row: ");
		
		scanner.nextLine();
		// get all rows entered
		while (true) {
			String line = scanner.nextLine();
			if (line.equals(""))
				break;
			rowsAsStrings.add(line);
		}

		return rowsAsStrings;
	}
	
	public List<String> getRowsFromFile() {
		System.out.println("Enter the file name: ");
		
		Path filePath = Paths.get(scanner.next());
		try {
			return Files.readAllLines(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Double[][] listToMatrix(List<String> rowsAsStrings) {
		Double[][] matrix = new Double[rowsAsStrings.size()][];
		int rowIndex = 0;
		// parse coefficients from each row and insert into the matrix
		for (String rowAsString : rowsAsStrings) {
			
			Double[] row = new Double[rowAsString.split(" ").length];
			int columnIndex = 0;
			
			// iterate through each coefficient string in the row
			for (String coefficient : rowAsString.split(" ")) {
				row[columnIndex] = Double.parseDouble(coefficient);
				columnIndex++;
			}
			
			matrix[rowIndex] = row;
			rowIndex++;
		}
		
		return matrix;
	}
	
	/**
	 * Get number of equations to generate a matrix w/ random coefficients
	 * @return ScaledPartialPivotingGE object with the matrix containing random values
	 */
	public ScaledPartialPivotingGE getNumberOfEquations() {
		System.out.println("Enter the number of equations (m for m x n matrix): ");
		int rows = scanner.nextInt();
		return new ScaledPartialPivotingGE(rows, rows + 1, -100., 100.);
	}
}
