package eight_puzzle_a_star;

public class UnitTestFixedDepth {
	public static void main(String[] args) {
		UnitTestFixedDepth suite = new UnitTestFixedDepth();
		suite.depth20();
	}

	private void runOnTests(String boards) {
		int successes = 0;
		for (String boardStr : boards.split("\n")) {
			Graph graph = new Graph(new GameState(boardStr, new DistanceHeuristic()));
			Path path = graph.aStarExpansion();
			if (path.getClass() != NullPath.class) successes++;
			System.out.println(path.toStringFullPath());
		}

		int cases = boards.split("\n").length;
		System.out.println("test cases: " + cases);
		System.out.println("successes: " + successes);
	}

	/* these are boards used as test cases which all have a depth of 20 or are unsolveable */
	private void depth20() {
		String boards = "0 5 7 2 8 4 1 3 6\n" +
				"0 8 5 2 3 7 1 4 6\n" +
				"7 6 3 8 2 1 4 5 0\n" +
				"0 8 7 3 1 2 4 6 5\n" +
				"4 5 1 7 0 2 6 3 8\n" +
				"6 4 0 7 8 1 3 5 2\n" +
				"2 5 7 3 6 8 0 4 1\n" +
				"2 5 7 4 0 8 1 6 3\n" +
				"1 4 7 3 0 6 8 5 2\n" +
				"4 3 0 7 1 6 8 5 2\n" +
				"2 5 0 4 3 8 7 1 6\n" +
				"5 3 4 6 7 2 8 1 0\n" +
				"2 8 5 3 1 4 0 7 6\n" +
				"0 2 5 7 6 8 4 1 3\n" +
				"4 5 0 7 8 2 1 6 3\n" +
				"7 3 1 8 0 6 5 4 2\n" +
				"6 1 0 8 3 4 5 7 2\n" +
				"0 6 3 8 2 1 5 4 7\n" +
				"5 2 0 1 8 4 6 7 3\n" +
				"8 1 0 6 4 2 7 5 3\n" +
				"5 8 0 2 1 6 3 7 4\n" +
				"2 5 0 8 7 6 1 3 4\n" +
				"1 4 3 6 0 5 8 2 7\n" +
				"7 3 1 4 5 6 8 2 0\n" +
				"4 1 0 6 3 5 2 7 8\n" +
				"8 2 4 1 7 5 3 6 0\n" +
				"5 2 3 4 6 8 1 7 0\n" +
				"2 1 0 3 4 8 6 7 5\n" +
				"7 2 4 3 0 6 8 1 5\n" +
				"5 8 3 1 4 7 6 2 0\n" +
				"4 8 3 2 0 7 6 5 1\n" +
				"0 3 8 6 4 1 7 2 5\n" +
				"0 3 1 2 8 7 5 4 6\n" +
				"6 3 4 7 0 1 2 5 8\n" +
				"0 6 3 7 1 5 2 8 4\n" +
				"4 8 1 2 0 7 3 5 6\n" +
				"1 5 2 6 0 7 4 3 8\n" +
				"1 7 0 3 8 4 2 6 5\n" +
				"0 1 3 8 5 2 6 7 4\n" +
				"0 5 7 4 2 3 1 6 8\n" +
				"6 3 0 8 7 1 5 2 4\n" +
				"4 5 8 7 2 6 0 1 3\n" +
				"0 2 5 7 4 8 1 6 3\n" +
				"3 1 4 6 0 7 2 8 5\n" +
				"2 5 3 1 0 7 6 4 8\n" +
				"5 1 8 3 6 4 0 7 2\n" +
				"8 4 0 1 2 7 5 3 6\n" +
				"4 5 0 1 3 7 6 2 8\n" +
				"4 8 5 2 3 7 1 6 0\n" +
				"4 6 1 3 7 2 0 5 8\n" +
				"8 4 3 6 5 1 0 7 2\n" +
				"1 3 7 6 0 4 2 8 5\n" +
				"3 6 1 7 8 4 0 5 2\n" +
				"1 6 2 4 5 3 7 8 0\n" +
				"6 1 2 7 8 5 4 3 0\n" +
				"4 2 7 5 0 8 1 6 3\n" +
				"6 2 7 1 4 5 0 3 8\n" +
				"7 1 3 6 0 2 4 5 8\n" +
				"6 2 1 3 0 5 7 4 8\n" +
				"2 4 8 6 1 5 3 7 0\n" +
				"1 8 5 6 2 3 0 4 7\n" +
				"1 6 8 2 0 3 7 5 4\n" +
				"3 8 0 7 1 6 4 5 2\n" +
				"3 8 1 2 7 4 6 5 0\n" +
				"0 2 5 1 4 6 7 8 3\n" +
				"2 5 0 3 6 7 4 1 8\n" +
				"3 7 2 8 0 6 4 1 5\n" +
				"2 5 8 1 7 3 4 6 0\n" +
				"7 3 2 6 0 4 8 5 1\n" +
				"4 7 0 6 3 2 8 5 1\n" +
				"0 7 5 1 4 8 3 2 6\n" +
				"2 1 0 3 6 4 8 7 5\n" +
				"1 4 5 7 2 8 6 3 0\n" +
				"6 2 3 1 0 4 8 7 5\n" +
				"5 8 4 1 2 7 6 3 0\n" +
				"3 5 1 6 0 8 4 7 2\n" +
				"0 5 8 2 7 6 1 3 4\n" +
				"4 3 5 2 6 7 0 1 8\n" +
				"2 5 8 6 0 3 1 7 4\n" +
				"0 2 3 1 8 7 6 5 4\n" +
				"5 4 2 8 0 7 3 1 6\n" +
				"4 2 0 1 3 8 7 6 5\n" +
				"3 6 1 4 0 5 8 2 7\n" +
				"1 6 3 7 2 5 8 4 0\n" +
				"2 4 8 5 1 7 3 6 0\n" +
				"4 6 0 5 1 2 3 7 8\n" +
				"5 8 3 2 4 1 0 6 7\n" +
				"5 2 8 4 1 3 6 7 0\n" +
				"0 5 1 6 2 8 7 3 4\n" +
				"5 1 8 3 6 4 0 7 2\n" +
				"7 4 3 6 0 2 8 5 1\n" +
				"6 1 0 3 7 4 5 8 2\n" +
				"1 4 0 7 6 3 5 8 2\n" +
				"6 3 0 8 7 2 5 4 1\n" +
				"3 2 0 1 5 8 6 4 7\n" +
				"3 2 7 4 6 8 1 5 0\n" +
				"4 5 8 6 0 1 2 3 7\n" +
				"1 6 5 7 0 8 3 2 4\n" +
				"3 5 8 2 4 7 0 1 6\n" +
				"3 5 0 6 8 2 7 4 1\n";

		runOnTests(boards);
	}
}
