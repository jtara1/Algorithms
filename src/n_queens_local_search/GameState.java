package n_queens_local_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameState implements State {
	// attrs
	private int size;
	private Tile[] columns;

	// get & set
	public Tile[] getColumns() { return columns; }

	// static
	static Random random = new Random();

	/* TODO: generate a truly random game state */
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

	static GameState trulyRandomState(int size) {
		Tile[] columns = new Tile[size];

		for (int col = 0; col < size; ++col) {
			columns[col] = new Tile(random.nextInt(size), col);
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

	static int queensOnSameLines(Tile[] columns, int columnIndex) {
		int attackable = 0;

		LinearEquation horizon, diagonal, diagonal2;
		Tile tile = columns[columnIndex];

		horizon = new LinearEquation(0, tile.getCol(), tile.getRow());
		diagonal = new LinearEquation(tile.getCol(), tile.getRow());
		diagonal2 = new LinearEquation(-1, tile.getCol(), tile.getRow()); // negative slope

		// for each piece placed after the current tile
		for (int i = 0; i < columns.length; ++i) {
			if (i == columnIndex) continue;

			int x = columns[i].getCol();
			int y = columns[i].getRow();

			// horizontal line
			attackable += horizon.intersects(x, y);
			// pos slope diagonal
			attackable += diagonal.intersects(x, y);
			// neg slope diagonal
			attackable += diagonal2.intersects(x, y);
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
	public State getRandomNeighbor() {
//		return getTrulyRandomNeighbor();
//		return getNeighborOfMostCostlyTile();
		return getRandomNeighborWhoseCostIsNotZero();
	}

	/* TODO: just change state starting from right side going left, skipping those whose cost (energy) == 0 */
	private State getNeighborOfMostCostlyTile() {
		int largestAmountOfAttacks = 0;
		Tile worstQueen = columns[0];
		ArrayList<Tile> worstQueens = new ArrayList<>();

		int index = 0;
		// iterate over columns
		for (Tile tile : columns) {
			int attackingCount = queensOnSameLines(columns, index);
			if (attackingCount > largestAmountOfAttacks) {
				largestAmountOfAttacks = attackingCount;
				worstQueen = tile;
				worstQueens = new ArrayList<>();
				worstQueens.add(worstQueen);
			}
			else if (attackingCount == largestAmountOfAttacks) {
				worstQueens.add(worstQueen);
			}
		}

		if (worstQueens.size() > 1) {
			worstQueen = worstQueens.get(random.nextInt(worstQueens.size()));
		}

		// get queen that's attacking the largest amount of queens
		int rowIndex = worstQueen.getRow();
		int newRowIndex;

		do {
			newRowIndex = random.nextInt(this.size);
		} while (newRowIndex == rowIndex);

		return createNewState(worstQueen.getCol(), newRowIndex);
	}

	private State getRandomNeighborWhoseCostIsNotZero() {
		Tile[] columns = ArrayUtils.filter(this, ArrayUtils::filterOutZeroCostTiles);
		Tile tileToChange;

		if (columns.length > 0) tileToChange = ArrayUtils.sample(columns);
		else tileToChange = ArrayUtils.sample(this.columns);

		return createNewState(tileToChange.getCol(), random.nextInt(this.size));

//		GameState neighbor = null;
//		Tile tile = null;
//		int cost = 0;
//		do {
//			neighbor = (GameState)getTrulyRandomNeighbor();
//			ArrayList<Tile> disjoinRightSet = disjoinRightSet(neighbor.getColumns());
//
//			if (disjoinRightSet.size() > 0) tile = disjoinRightSet.get(0);
//			else tile = null;
//
//			cost = queensOnSameLines(neighbor.getColumns(), tile.getRow());
//		} while (cost == 0);
//
//		return neighbor;
	}

	/* select random Tile in board columns, move to a random spot in the same column TODO: be biased to pick a certain one to change ? */
	private State getTrulyRandomNeighbor() {
		int column = random.nextInt(this.size);
		int newRow = random.nextInt(this.size);
		return createNewState(column, newRow);
	}

	private State createNewState(int col, int newRow) {
		Tile[] newColumns = Arrays.copyOf(columns, this.size);
		newColumns[col].setRow(newRow);

		return new GameState(newColumns);
	}

	@Override
	public float temperatureScheduling(int step) {
//		float temp = (float)this.size / (step <= 0 ? 1 : step);
		float temp = 5000f / (step <= 0 ? 1 : step);

		if (temp < 0f) return 0;
		return temp;
	}

	@Override
	/* todo: give lower (better) score for boards with queens that have 1 or 2 pieces in it's L path (size 3) */
	public int energy() {
//		if (queensOnSameLines(columns, false) == 0)
//			return -Integer.MAX_VALUE;
		return queensOnSameLines(columns, false);
	}

	public boolean isSolution() {
		return queensOnSameLines(columns, true) == 0;
	}

	public ArrayList<Tile> disjoinRightSet(Tile[] tiles) {
		ArrayList<Tile> disjointSet = new ArrayList<>();
		assert(tiles.length == this.size);

		for (int i = 0; i < this.size; ++i) {
			if (tiles[i].compareTo(columns[i]) == 0) continue;
			disjointSet.add(tiles[i]);
		}

		return disjointSet;
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
