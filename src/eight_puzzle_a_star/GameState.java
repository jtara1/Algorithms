package eight_puzzle_a_star;

import java.util.ArrayList;
import java.util.Arrays;

public class GameState<H extends Heuristic> extends Vertex {
	private ArrayList<Byte> board;
	private Heuristic heuristic;

	public static Boolean isSolveableBoard(ArrayList<Integer> board) {
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

	public GameState(ArrayList<Byte> board) {
		assert(board.size() == 9);
		this.board = board;
	}

	public GameState(ArrayList<Byte> board, Heuristic heuristic) {
		this(board);
		this.heuristic = heuristic;
	}

	/* for the 8 puzzle problem, this'll generate the 2-4 neighbors of the vertex & the neighbors */
	public void generateNeighbors() {

	}

	public float getMisplacedTileCost() {
		byte misplaced = 0;
		for (byte i = 0; i < board.size(); ++i) {
			// board tile state is different than the tile in the goal state, it's misplaced
			if (!board.get(i).equals(goalGameState.board.get(i)))
				++misplaced;
		}

		return (float)misplaced;
	}

	public float getDistanceCost() {
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
}
