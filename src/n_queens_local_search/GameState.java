package n_queens_local_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameState<T extends Tile> implements State {
	// attrs
	private int size;
	private T[] columns;

	// get & set

	// static
	static Random random = new Random();

	static GameState<Tile> randomState(int size) {
		ArrayList<Integer> rowIndices = new ArrayList<>();
		// row indices will be 0, 1, 2, ..., size - 1
		for (int i = 0; i < size; ++i) {
			rowIndices.set(i, i);
		}

		for (int i = 0; i < size; ++i) {
			// select a random row index, if it breaks the rules of n-queen, discard the value from the indices & pick another
			int index = random.nextInt(rowIndices.size());
			
		}
	}

	static boolean isSolution(Tile[] columns) {

	}

	/**
	 * Counts the pieces on the board that're on the same horizontal or diagonal lines from each piece
	 * Can be used to measure the cost / distance / energy of the state (board).
	 * Can be used to check if this state is solution
	 * @param columns
	 * @param onlyCheckIfSolution
	 * @return
	 */
	static int queensOnSameLine(Tile[] columns, boolean onlyCheckIfSolution) {
		int attackable = 0;
		int columnIndex = 0;

		LinearEquation horizon, diagonal, diagonal2;

		// for each column
		for (Tile tile : columns) {
			horizon = new LinearEquation(0, tile.getCol(), tile.getRow());
			diagonal = new LinearEquation(tile.getCol(), tile.getRow());
			diagonal2 = new LinearEquation(-1, tile.getCol(), tile.getRow()); // negative slope

			// for each piece placed after the current tile
			for (int i = columnIndex + 1; i < columns.length; ++i) {
				int x = columns[i].getCol();
				int y = columns[i].getRow();

				// horizontal line
				attackable += horizon.intersects(x, y);
				// pos slope diagonal
				attackable += diagonal.intersects(x, y);
				// neg slope diagonal
				attackable += diagonal2.intersects(x, y);
			}

			if (onlyCheckIfSolution && attackable > 0) return 1;
		}

		return attackable;
	}

	// constructors
	public GameState(T... columns) {
		this.columns = columns;
		this.size = columns.length;
	}

	// methods
	@Override
	/* select random Tile in board columns, move to a random spot in the same column */
	public State getRandomNeighbor() {
		return null;
	}

	@Override
	public float temperatureScheduling(int step) {
		return 0;
	}

	@Override
	public int energy() {
		return queensOnSameLine(columns, false);
	}
}
