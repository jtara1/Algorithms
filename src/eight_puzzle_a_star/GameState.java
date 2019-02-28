package eight_puzzle_a_star;

import java.util.*;

public class GameState extends Vertex {
	// attrs
	private ArrayList<Byte> board;
	private Heuristic heuristic;

	protected ArrayList<GameState> neighbors;

	// getters & setters
	public ArrayList<GameState> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(ArrayList<GameState> neighbors) {
		this.neighbors = neighbors;
	}

	public ArrayList<Byte> getBoard() {
		return board;
	}

	// static
	public static Boolean isSolveableBoard(ArrayList<Byte> board) {
		assert(board.size() == 9);
		int inversions = 0;

		for (int i = 0; i < board.size() - 1; ++i) {
			if (board.get(i) > board.get(i + 1))
				inversions++;
		}

		return inversions % 2 == 0;
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
	public GameState(ArrayList<Byte> board) {
		assert(board.size() == 9);
		this.board = board;
		neighbors = new ArrayList<>(4);
	}

	public GameState(ArrayList<Byte> board, Heuristic heuristic) {
		this(board);
		this.heuristic = heuristic;
	}

//	public GameState(Heuristic heuristic, int... board) {
//		this.board = new ArrayList<>(9);
//		for (int tileState: board) {
//			this.board.add((byte)tileState);
//		}
//
//		this.heuristic = heuristic;
//		assert(this.board.size() == 9);
//	}

	// methods
	/* for the 8 puzzle problem, this'll generate the 2-4 neighbors of the vertex & the neighbors */
	public void generateNeighbors(HashMap<String, Vertex> vertices) {
		int indexOfBlank = board.indexOf((byte)0);
		GameState up, right, down, left;
		GameState existingUp, existingRight, existingDown, existingLeft;

		int upIndex = indexOfBlank - 3;
		int rightIndex = indexOfBlank + 1;
		int downIndex = indexOfBlank + 3;
		int leftIndex = indexOfBlank - 1;

		up = new GameState(swapTileStates(indexOfBlank, indexOfBlank - 3), heuristic);
		existingUp = (GameState)vertices.get(up.toString());
		if (existingUp != null) up = existingUp;
		up.getNeighbors().set(downIndex, this);

		right = new GameState(swapTileStates(indexOfBlank, indexOfBlank + 1), heuristic);
		existingRight = (GameState)vertices.get(right.toString());
		if (existingRight != null) right = existingRight;
		right.getNeighbors().set(leftIndex, this);

		down = new GameState(swapTileStates(indexOfBlank, indexOfBlank + 3), heuristic);
		existingDown = (GameState)vertices.get(down.toString());
		if (existingDown != null) up = existingDown;
		down.getNeighbors().set(upIndex, this);

		left = new GameState(swapTileStates(indexOfBlank, indexOfBlank - 1), heuristic);
		existingLeft = (GameState)vertices.get(left.toString());
		if (existingLeft != null) up = existingLeft;
		left.getNeighbors().set(rightIndex, this);

		neighbors = new ArrayList<>(Arrays.asList(up, right, down, left));
	}

	public boolean isSolution() {
		return goalGameState.board.equals(board);
	}

	public float getMisplacedTileCost() {
		if (!isSolveableBoard(board)) return Float.POSITIVE_INFINITY;
		byte misplaced = 0;
		for (byte i = 0; i < board.size(); ++i) {
			// board tile state is different than the tile in the goal state, it's misplaced
			if (!board.get(i).equals(goalGameState.board.get(i)))
				++misplaced;
		}

		return (float)misplaced;
	}

	public float getDistanceCost() {
		if (!isSolveableBoard(this.board)) return Float.POSITIVE_INFINITY;

		byte totalDistance = 0;

		for (byte i = 0; i < board.size(); ++i) {
			byte tileState = board.get(i);
			byte distance = 0;
			byte index = i;

			// each action will have a step cost of 1; total steps to get any tile state to goal is [0, 4]
			while (tileState != index) {
				if (Math.abs(tileState - index) < 3 && isValidHorizontalMove(index, (byte)(tileState - index))) {
					distance += Math.abs(tileState - index);
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

	public float getHeuristicCost(Heuristic heuristic) {
		return heuristic.getCost(this);
	}

	public float getHeuristicCost() {
		return this.heuristic.getCost(this);
	}

	public ArrayList<Byte> swapTileStates(int index1, int index2) {
		try {
			int diff = Math.abs(index1 - index2);
			if ((diff != 3) && (diff != 1)) return null; // diff of indices is not 1 and not 3
			if (!isValidHorizontalMove((byte)index1, (byte)index2)) return null;

			ArrayList<Byte> newBoard = new ArrayList<>(board);
			Collections.swap(newBoard, index1, index2);
			return newBoard;
		} catch (IndexOutOfBoundsException exception) {
			return null;
		}
	}

	public boolean equals(Object object) {
		return ((GameState)object).getBoard().equals(board);
	}

	public int hashCode() {
		return board.hashCode();
	}

	public String toString() {
		StringBuilder str = new StringBuilder();

		for (byte i = 1; i < board.size(); ++i) {
			String tile = board.get(i - 1) + " ";
			if (i % 3 == 0)
				tile = tile.replace(" ", "\n");

			str.append(tile);
		}

		return str.toString();
	}
}
