package eight_puzzle_a_star;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CsvWriter {
	public static void write(ArrayList<String> header, ArrayList<ArrayList<String>> csvContent, String outputFileName) {
		try (PrintWriter writer = new PrintWriter(new File(outputFileName))) {
			StringBuilder stringBuilder = new StringBuilder();

			// header
			stringBuilder.append(String.join(",", header));
			stringBuilder.append("\n");

			// content
			for (ArrayList<String> row : csvContent) {
				stringBuilder.append(String.join(",", row));
				stringBuilder.append("\n");
			}

			writer.write(stringBuilder.toString());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
