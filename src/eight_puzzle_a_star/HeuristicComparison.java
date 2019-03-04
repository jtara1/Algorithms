package eight_puzzle_a_star;

import java.util.*;

public class HeuristicComparison {
	// attributes
	private ArrayList<ArrayList<String>> resultsHeuristic1 = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> resultsHeuristic2 = new ArrayList<ArrayList<String>>();

	// static
	public static Heuristic misplacedTilesH = new MisplacedTilesHeuristic();
	public static Heuristic distanceH = new DistanceHeuristic();

	public static void main(String[] args) {
		HeuristicComparison cmp = new HeuristicComparison();
		cmp.generateReport(2000);
	}

	/**
	 * output grouped by depth of path to solution: avg runtime, total vertices generated (search cost), count
	 * output will be an array of arrays formatted as:
	 * [[<depth to solution>, <heuristic name>, <runtime>, <vertices>]]
	 */
	private ArrayList<ArrayList<String>> compare(int testCount) {
		for (int i = 0; i < testCount; ++i) {
			GraphAndPathCollection collection = Graph.solveSolveable8Puzzle(misplacedTilesH);
			GraphAndPathCollection collection2 = Graph.solveBoard(distanceH, collection.board);

			addToResults(
					resultsHeuristic1,
					collection.path.getNeighborIndicesSequence().size(),
					misplacedTilesH.getName(),
					collection.graph.getRuntimeDuration(),
					collection.graph.getVertices().size()
			);

			addToResults(
					resultsHeuristic2,
					collection2.path.getNeighborIndicesSequence().size(),
					distanceH.getName(),
					collection2.graph.getRuntimeDuration(),
					collection2.graph.getVertices().size()
			);
		}

		return resultsHeuristic1;
	}

	/* a table grouped by depth to solution that lists the avg runtime, avg vertices, & count for each depth */
	public void generateReport(int testCount) {
		compare(testCount);

		// heuristic1.csv
		processResults(resultsHeuristic1, "misplaced tiles heuristic.csv");

		// heuristic2.csv
		processResults(resultsHeuristic2, "distance heuristic.csv");
	}

	/**
	 * // make a set of depths listed which are at column 0
	 * // make an array of size of the set of depths (should be 23 for depths [2, 24]) for 8 puzzle
	 * // get averages of col 1 then col 2, then insert them into the correct row defined by their depth
	 * @param results
	 */
	private void processResults(ArrayList<ArrayList<String>> results, String outputFileName) {
		HashSet<Integer> depths = getDepths(results);

		ArrayList<ArrayList<String>> processedResults = new ArrayList<ArrayList<String>>(depths.size());
		for (Integer depth : depths) { // initialize with empty arrays w/ proper size
			ArrayList<String> row = new ArrayList<String>(3);

			row.add(depth.toString());

			ArrayList<Integer> runtimes = getColumn(results, 2, true);
			row.add(average(runtimes));

			ArrayList<Integer> vertices = getColumn(results, 3, true);
			row.add(average(vertices));

			processedResults.add(row); // row contains the averages for this depth
		}

		CsvWriter.write(
				new ArrayList<String>(Arrays.asList("depth", "avg runtime", "avg vertices generated for graph (search cost)")),
				processedResults,
				outputFileName
		);
	}

	private HashSet<Integer> getDepths(ArrayList<ArrayList<String>> results) {
		return new HashSet<Integer>(getColumn(results, 0, true));
	}

	private ArrayList getColumn(ArrayList<ArrayList<String>> data, int columnIndex, boolean parseAsInt) {
		ArrayList column = new ArrayList<Integer>();
		for (ArrayList<String> row : data) {
			String value = row.get(columnIndex);
			int valueAsInt = Integer.parseInt(value);

			column.add(parseAsInt ? valueAsInt : value);
		}

		return column;
	}

	private ArrayList getColumnFilteredByDepth(ArrayList<ArrayList<String>> data, int columnIndex, boolean parseAsInt, int depth) {
		ArrayList<ArrayList<String>> filteredResults = new ArrayList<ArrayList<String>>();

		for (ArrayList<String> row : data) {
			if (Integer.parseInt(row.get(0)) != depth) { // filter out depths we dont care about
				continue;
			}

			filteredResults.add(row);
		}

		return getColumn(filteredResults, columnIndex, parseAsInt);
	}

	private String average(ArrayList<Integer> numbers) {
		int count = 0;
		float sum = 0f;

		for (; count < numbers.size(); ++count) {
			sum += numbers.get(count);
		}

		return new Float(sum / count).toString();
	}

	private void addToResults(ArrayList<ArrayList<String>> results, Integer depth, String heuristicName, Long runtime, Integer verticesCount) {
		results.add(new ArrayList<String>(Arrays.asList(depth.toString(), heuristicName, runtime.toString(), verticesCount.toString())));
	}
}
