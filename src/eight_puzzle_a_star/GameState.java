package eight_puzzle_a_star;

import java.util.*;

public class GameState extends Vertex {
	// attrs
	private ArrayList<Byte> board;
	private Heuristic heuristic;

	// getters & setters
	public ArrayList<Byte> getBoard() {
		return board;
	}

	public Heuristic getHeuristic() {
		return heuristic;
	}

	// static
	public static Boolean isSolveableBoard(ArrayList<Byte> board) {
		assert(board.size() == 9);

		Set<Byte> tileStates = new HashSet<>(GameState.goalGameState.getBoard());
		tileStates.remove(0);
		int inversions = 0;

		for (int i = 0; i < board.size() - 1; ++i) {
			Byte tileState = board.get(i);
			Byte nextTileValue = board.get(i + 1);

			if (tileState > nextTileValue)
				inversions += getInversions(tileState, tileStates);
			tileStates.remove(tileState);
		}

		return inversions % 2 == 0;
	}

	public static int getInversions(Byte tileState, Set<Byte> tileStates) {
		int inversions = 0;
		for (int i = 0; i < tileState; ++i) {
			if (tileStates.contains((byte)i))
				++inversions;
		}
		return inversions;
	}

	public static ArrayList<Byte> generateBoard() {
		ArrayList<Byte> board = new ArrayList<Byte>(GameState.goalGameState.board);
		Collections.shuffle(board);
		return board;
	}

	public static GameState unsolveableGameState = new GameState(
			new ArrayList<Byte>(
					Arrays.asList((byte)8, (byte)7, (byte)6, (byte)5, (byte)4, (byte)3, (byte)2, (byte)1, (byte)0)
			)
	);

	public static GameState goalGameState = new GameState(
			new ArrayList<Byte>(
					Arrays.asList((byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8)
			)
	);

	// constructors
	public GameState(Heuristic heuristic) {
		this(heuristic, 8, 7, 6, 5, 4, 3, 2, 1, 0);
	}

	public GameState(ArrayList<Byte> board) {
		assert(board.size() == 9);
		this.board = board;
		neighbors = new ArrayList<Vertex>(Arrays.asList(null, null, null, null));
	}

	public GameState(ArrayList<Byte> board, Heuristic heuristic) {
		this(board);
		this.heuristic = heuristic;
	}


	public GameState(ArrayList<Byte> board, Heuristic heuristic, float pathCost) {
		this(board, heuristic);
		this.pathCost = pathCost + getHeuristicCost();
	}

	public GameState(String board, Heuristic heuristic) {
		ArrayList<Byte> realBoard = new ArrayList<>();
		for (String numb: board.split(" ")) {
			realBoard.add(Byte.parseByte(numb));
		}
		this.board = realBoard;
		neighbors = new ArrayList<>(Arrays.asList(null, null, null, null));
		this.heuristic = heuristic;
	}

	public GameState(Heuristic heuristic, int... board) {
		this.board = new ArrayList<>(9);
		for (int tileState: board) {
			this.board.add((byte)tileState);
		}

		this.heuristic = heuristic;
		assert(this.board.size() == 9);
	}

	// methods
	/* for the 8 puzzle problem, this'll generate the 2-4 neighbors of the vertex & the neighbors */
	public void generateNeighbors(HashMap<String, Vertex> vertices) {
		int indexOfBlank = board.indexOf((byte)0);
		GameState up = null;
		GameState right = null;
		GameState down = null;
		GameState left = null;
		GameState existingUp, existingRight, existingDown, existingLeft;

		int upIndex = indexOfBlank - 3;
		int rightIndex = indexOfBlank + 1;
		int downIndex = indexOfBlank + 3;
		int leftIndex = indexOfBlank - 1;

		ArrayList<Byte> newBoard;

		newBoard = swapTileStates(indexOfBlank, upIndex);
		if (newBoard != null) {
			up = new GameState(newBoard, heuristic, pathCost);
			existingUp = (GameState)vertices.get(up.toString());
			up.neighbors.set(2, this);
			if (existingUp != null) up = null;
		}

		newBoard = swapTileStates(indexOfBlank, rightIndex);
		if (newBoard != null) {
			right = new GameState(newBoard, heuristic, pathCost);
			existingRight = (GameState)vertices.get(right.toString());
			right.neighbors.set(3, this);
			if (existingRight != null) right = null;
		}

		newBoard = swapTileStates(indexOfBlank, downIndex);
		if (newBoard != null) {
			down = new GameState(newBoard, heuristic, pathCost);
			existingDown = (GameState) vertices.get(down.toString());
			down.neighbors.set(0, this);
			if (existingDown != null) down = null;
		}

		newBoard = swapTileStates(indexOfBlank, leftIndex);
		if (newBoard != null) {
			left = new GameState(newBoard, heuristic, pathCost);
			existingLeft = (GameState)vertices.get(left.toString());
			left.neighbors.set(1, this);
			if (existingLeft != null) left = null;
		}

		neighbors = new ArrayList<Vertex>(Arrays.asList(up, right, down, left));
	}

	public boolean isSolution() {
		return goalGameState.board.equals(board);
	}

	public int getNeighborsSize() {
		return neighbors.size();
	}

	public float getMisplacedTileCost() {
//		if (!isSolveableBoard(board)) return Float.POSITIVE_INFINITY;

		byte misplaced = 0;
		for (byte i = 0; i < board.size(); ++i) {
			// board tile state is different than the tile in the goal state, it's misplaced
			if (!board.get(i).equals(goalGameState.board.get(i)))
				++misplaced;
		}

		return (float)misplaced;
	}

	public float getDistanceCost() {
//		if (!isSolveableBoard(this.board)) return Float.POSITIVE_INFINITY;

		byte totalDistance = 0;

		for (byte i = 0; i < board.size(); ++i) {
			byte tileState = board.get(i);
			byte distance = 0;
			byte index = i;

			// each action will have a step cost of 1; total steps to get any tile state to goal is [0, 4]
			while (tileState != index) {
				byte difference = (byte)Math.abs(tileState - index);

				if (difference < 3 && isValidHorizontalMove(index, tileState)) {
					distance += difference;
					index += tileState - index;
				}
				else { // add xor sub 3
					if (tileState > index)
						index += (byte)3;
					else
						index -= (byte)3;

					++distance;
				}
			}

			totalDistance += distance;
		}

		return (float)totalDistance;
	}

	/* you can't get to the row above or below by moving horizontally */
	private Boolean isValidHorizontalMove(byte startIndex, byte endIndex) {
		ArrayList<Byte> row1 = new ArrayList<Byte>(Arrays.asList((byte)0, (byte)1, (byte)2));
		ArrayList<Byte> row2 = new ArrayList<Byte>(Arrays.asList((byte)3, (byte)4, (byte)5));
		ArrayList<Byte> row3 = new ArrayList<Byte>(Arrays.asList((byte)6, (byte)7, (byte)8));

		return ((row1.contains(startIndex) && row1.contains(endIndex)) ||
				(row2.contains(startIndex) && row2.contains(endIndex)) ||
				(row3.contains(startIndex) && row3.contains(endIndex)));
	}

	private boolean isValidVerticalMove(int index1, int index2) {
		return Math.abs(index1 - index2) == 3;
	}

	public float getHeuristicCost() {
		return this.heuristic.getCost(this);
	}

	public ArrayList<Byte> swapTileStates(int index1, int index2) {
		try {
			int diff = Math.abs(index1 - index2);
			if ((diff != 3) && (diff != 1)) return null; // diff of indices is not 1 and not 3
			if (!isValidHorizontalMove((byte)index1, (byte)index2) &&
				!isValidVerticalMove(index1, index2)
				)
				return null;

			ArrayList<Byte> newBoard = new ArrayList<>(board);
			Collections.swap(newBoard, index1, index2);
			return newBoard;
		} catch (IndexOutOfBoundsException exception) {
			return null;
		}
	}

	public boolean isSolveable() {
		return isSolveableBoard(board);
	}

	public boolean isValidVertex() {
		return GameState.isSolveableBoard(board);
	}

	public boolean equals(Object object) {
		return ((GameState)object).getBoard().equals(board);
	}

	public int hashCode() {
		return board.hashCode();
	}

	public String toString() {
		return toFlattenedString();
	}

	public String toGridString() {
		StringBuilder str = new StringBuilder();

		for (byte i = 1; i < board.size() + 1; ++i) {
			String tile = board.get(i - 1) + " ";
			if (i % 3 == 0)
				tile = tile.replace(" ", "\n");

			str.append(tile);
		}

		return str.toString();
	}

	public String toFlattenedString() {
		return toGridString().replace("\n", " ").trim();
	}
}
