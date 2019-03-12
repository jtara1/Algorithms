package n_queens_local_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameState implements State {
	// attrs
	private int size;
	private Tile[] columns;

	// get & set

	// static
	static Random random = new Random();

	static GameState randomState(int size) {
		ArrayList<Integer> rowIndices = new ArrayList<>();
		ArrayList<Integer> currentRowIndices; // copies rowIndices at ea iteration to use with each creation of next tile on column
		Tile[] columns = new Tile[0];

		// row indices will be 0, 1, 2, ..., size - 1
		for (int i = 0; i < size; ++i) {
			rowIndices.add(i);
		}

		for (int i = 0; i < size; ++i) {
			currentRowIndices = new ArrayList<>(rowIndices);
			boolean searchForPlacement = true;

			while (searchForPlacement) {
//				if (currentRowIndices.isEmpty()) {
//					 break;
//				}
				// select a random row index, if it breaks the rules of n-queen, discard the value from the indices & pick another
				int index = random.nextInt(currentRowIndices.size());
				Tile[] columns2 = Arrays.copyOf(columns, i + 1);

				int row = currentRowIndices.get(index);
				columns2[i] = new Tile(row, i);
				currentRowIndices.remove(index);

				boolean isSolution = queensOnSameLines(columns2, true) < 1;
				if (currentRowIndices.isEmpty()) searchForPlacement = false; // there is no tile for this queen such that it will not be attacked by another queen

				if (!searchForPlacement || isSolution) {
					columns = columns2;
					rowIndices.remove(Integer.valueOf(row));
					searchForPlacement = false;
				}
			}
		}

		return new GameState(columns);
	}

	static boolean isSolution(Tile[] columns) {
		return queensOnSameLines(columns, true) == 0;
	}

	/**
	 * Counts the pieces on the board that're on the same horizontal or diagonal lines from each piece
	 * Can be used to measure the cost / distance / energy of the state (board).
	 * Can be used to check if this state is solution
	 * @param columns
	 * @param onlyCheckIfSolution
	 * @return
	 */
	static int queensOnSameLines(Tile[] columns, boolean onlyCheckIfSolution) {
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
	public GameState(Tile... columns) {
		this.columns = columns;
		this.size = columns.length;
	}

	public GameState(int... rowIndices) {
		this(Tile.rowSequenceToTileArray(rowIndices));
	}

	// methods
	@Override
	/* select random Tile in board columns, move to a random spot in the same column TODO: be biased to pick a certain one to change ? */
	public State getRandomNeighbor() {
		int index = random.nextInt(this.size);
		int newRow = random.nextInt(this.size);

		Tile[] newColumns = Arrays.copyOf(columns, this.size);
		newColumns[index].setRow(newRow);

		return new GameState(newColumns);
	}

	@Override
	public float temperatureScheduling(int step) {
		return (float)this.size / (step <= 0 ? 1 : step);
	}

	@Override
	public int energy() {
		return queensOnSameLines(columns, false);
	}

	public boolean isSolution() {
		return queensOnSameLines(columns, true) == 0;
	}

	// meta methods
	@Override
	public String toString() {
		return toStringListOfRowIndices();
	}

	/* eg: 3 5 6 8 0 1 2 4 indicates queens on tiles: (0, 3), (1, 5), (2, 6), ... */
	public String toStringListOfRowIndices() {
		StringBuilder stringBuilder = new StringBuilder();

		for (Tile tile : columns) {
			stringBuilder.append(tile.getRow());
			stringBuilder.append(" ");
		}

		return stringBuilder.toString().trim();
	}
}
