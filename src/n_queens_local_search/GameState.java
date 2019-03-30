package n_queens_local_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameState implements State {
	// attrs
	protected int size;
	protected Tile[] rows;
	protected Integer maxFitness;
	protected Integer fitness = null; // cached fitness
	protected Integer energy = null; // cached energy

	// get & set
	public Tile[] getColumns() { return rows; }
	public int getMaxFitness() {
		if (maxFitness != null) return maxFitness;
//		maxFitness = 0
//		for (int i = size - 1; i > 0; --i) {
//			maxFitness += i;
//		}
		maxFitness = size * (size - 1) / 2;
		return maxFitness;
	}
	public int getFitness() { return fitness; }
	public int getSize() { return size; }

	// static
	protected static Random random = new Random();

	static GameState randomState(int size) {
		ArrayList<Integer> rowIndices = new ArrayList<>();
		ArrayList<Integer> currentRowIndices; // copies rowIndices at ea iteration to use with each creation of next tile on column
		Tile[] rows = new Tile[0];

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
				Tile[] columns2 = Arrays.copyOf(rows, i + 1);

				int row = currentRowIndices.get(index);
				columns2[i] = new Tile(row, i);
				currentRowIndices.remove(index);

				boolean isSolution = queensOnSameLines(columns2, true) < 1;
				if (currentRowIndices.isEmpty()) searchForPlacement = false; // there is no tile for this queen such that it will not be attacked by another queen

				if (!searchForPlacement || isSolution) {
					rows = columns2;
					rowIndices.remove(Integer.valueOf(row));
					searchForPlacement = false;
				}
			}
		}

		return new GameState(rows);
	}

	static GameState trulyRandomState(int size) {
		Tile[] rows = new Tile[size];

		for (int col = 0; col < size; ++col) {
			rows[col] = new Tile(random.nextInt(size), col);
		}

		return new GameState(rows);
	}

	static boolean isSolution(Tile[] rows) {
		return queensOnSameLines(rows, true) == 0;
	}

	/**
	 * Counts the pieces on the board that're on the same horizontal or diagonal lines from each piece
	 * Can be used to measure the cost / distance / energy of the state (board).
	 * Can be used to check if this state is solution
	 * @param rows
	 * @param onlyCheckIfSolution
	 * @return
	 */
	static int queensOnSameLines(Tile[] rows, boolean onlyCheckIfSolution) {
		return queensOnSameLines(rows, onlyCheckIfSolution, false, null);
	}

	static int queensOnSameLines(Tile[] rows, boolean onlyCheckIfSolution, boolean checkLeftAndRight) {
		return queensOnSameLines(rows, onlyCheckIfSolution, checkLeftAndRight, null);
	}

	static int queensOnSameLines(Tile[] rows, int columnIndex) {
		return queensOnSameLines(rows, false, true, columnIndex);
	}

	static int queensOnSameLines(Tile[] rows, boolean onlyCheckIfSolution, boolean checkLeftAndRight, Integer onlyCheckThisIndex) {
		int attackable = 0;
		int columnIndex = 0;

		LinearEquation horizon, diagonal, diagonal2;

		// for each column
		for (int tileIndex = 0; tileIndex < rows.length; ++tileIndex) {
			if (onlyCheckThisIndex != null && tileIndex != onlyCheckThisIndex) continue;

			Tile tile = rows[tileIndex];
			if (tile == null) continue;

			horizon = new LinearEquation(0, tile.getCol(), tile.getRow());
			diagonal = new LinearEquation(tile.getCol(), tile.getRow());
			diagonal2 = new LinearEquation(-1, tile.getCol(), tile.getRow()); // negative slope

			columnIndex = tile.getCol();

			// for each piece placed after the current tile
			for (int i = checkLeftAndRight ? 0 : columnIndex + 1; i < rows.length; ++i) {
				if (i == columnIndex) continue;

				Tile tile2 = rows[i];
				if (tile2 == null) continue;

				int x = tile2.getCol();
				int y = tile2.getRow();

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
	public GameState(Tile... rows) {
		this.rows = rows;
		this.size = rows.length;
//		this.maxFitness = (int)(size * ((size - 1) / 2f));
//		this.maxFitness = size * (size - 1);
		this.maxFitness = getMaxFitness();
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
		Tile worstQueen = rows[0];
		ArrayList<Tile> worstQueens = new ArrayList<>();

		int index = 0;
		// iterate over rows
		for (Tile tile : rows) {
			int attackingCount = queensOnSameLines(rows, index);
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
		Tile[] rows = ArrayUtils.filter(this, ArrayUtils::filterOutZeroCostTiles);
		Tile tileToChange;

		if (rows.length > 0) tileToChange = ArrayUtils.sample(rows);
		else tileToChange = ArrayUtils.sample(this.rows);

		return createNewState(tileToChange.getCol(), random.nextInt(this.size));

//		GameState neighbor = null;
//		Tile tile = null;
//		int cost = 0;
//		do {
//			neighbor = (GameState)getTrulyRandomNeighbor();
//			ArrayList<Tile> disjointRightSet = disjointRightSet(neighbor.getColumns());
//
//			if (disjointRightSet.size() > 0) tile = disjointRightSet.get(0);
//			else tile = null;
//
//			cost = queensOnSameLines(neighbor.getColumns(), tile.getRow());
//		} while (cost == 0);
//
//		return neighbor;
	}

	/* select random Tile in board rows, move to a random spot in the same column TODO: be biased to pick a certain one to change ? */
	private State getTrulyRandomNeighbor() {
		int column = random.nextInt(this.size);
		int newRow = random.nextInt(this.size);
		return createNewState(column, newRow);
	}

	protected State createNewState(int col, int newRow) {
		Tile[] newColumns = Arrays.copyOf(rows, this.size);
		if (newColumns[col] == null) newColumns[col] = new Tile(newRow, col);
		newColumns[col].setRow(newRow);

		return new GameState(newColumns);
	}

	/**
	 * @return some negative int to decrement the score / energy further for having
	 * 1-2 queens on its L (1 vertical dist & 2 horizon dist, xor, 2 vertical dist & 1 horizon dist)
	 */
	protected int getReducedCostForQueensOnLOfAnotherQueen() {
		int reductionPerQueenOnL = (int)(this.size / 2f); // truncate / Math.floor

		int reducedCost = 0;
		for (Tile tile : rows) {
			int queensOnItsL = getNumberOfQueensOnItsL(tile);
			if (queensOnItsL == 1 || queensOnItsL == 2)
				reducedCost -= reductionPerQueenOnL * queensOnItsL;
		}

		return reducedCost;
	}

	private int getNumberOfQueensOnItsL(Tile tile) {
		int inRange = 0;
		if (tile == null) return inRange;

		int x = tile.getCol();
		int y = tile.getRow();

		int start = tile.getCol() - 2;
		if (start < 0) start = 0;

		int end = tile.getCol() + 2;
		if (end > this.size) end = this.size - 1;

		for (int i = start; i < end; ++i) {
			if (i == tile.getCol()) continue;
			Tile tile2 = rows[i];
			if (tile2 == null) continue;

			int otherX = tile2.getCol();
			int otherY = tile2.getRow();
			int distX = Math.abs(otherX - x);
			int distY = Math.abs(otherY - y);

			if (3 == distY + distX &&
				distY < 3 &&
				distX < 3 &&
				inSameQuadrant(x, y, otherX, otherY)
			) {
				++inRange;
			}
		}

		return inRange;
	}

	private boolean inSameQuadrant(int x1, int y1, int x2, int y2) {
		int boundary = (int)Math.ceil((double)this.size / 2);

		boolean sameLeftXorRight = x1 < boundary == x2 < boundary;
		boolean sameTopXorBottom = y1 < boundary == y2 < boundary;

		return sameLeftXorRight && sameTopXorBottom;
	}

	private boolean hasFewEnoughQueensInQuadrant(boolean leftSection, boolean topSection) {
		int boundary = (int)Math.ceil((double)this.size / 2);
		int start = leftSection ? 0 : boundary;
		int end = leftSection ? boundary : this.size;

		int piecesInSection = 0;
		for (int i = start; i < end; ++i) {
			Tile tile = rows[i];
			if (tile.getRow() < boundary == topSection)
				++piecesInSection;
		}

		if (this.size % 2 == 0) {
			return Math.ceil(this.size / 4f) <= piecesInSection;
		}
		return Math.ceil(this.size / 3f) <= piecesInSection;
	}

	/**
	 * delta energy is expected to be in [-15, 0]
	 * sigmoid domain can be [-inf, inf] but is ideally [-4, 4]
	 * init temps should simulatedAnnealing around -4 and go to 4
	 * so we're going to just use a linear equation
	 * y = 0.000004 * (x - 1000000)
	 * @param step the iteration count for simulated annealing. so it's 0, 1, 2, 3, ..., inf
	 */
	@Override
	public float temperatureScheduling(int step) {
		step -= 1000000000; // 1E9
		if (step == 0) ++step;
		else if (step > 2000000000) return 0;

		return (float)0.000000004 * (step); // m = 4E-9
	}

	@Override
	public int energy() {
		if (energy != null) return energy;

		int queenAttacks = queensOnSameLines(rows, false);
		if (queenAttacks == 0) energy = -Integer.MAX_VALUE;
		else energy = queenAttacks + getReducedCostForQueensOnLOfAnotherQueen();

		energy = energy == 0 ? -1 : energy;
		return energy;
	}

	@Override
	/* a measure of how close we are to solution. higher number means we're closer */
	public int fitness() {
		if (fitness != null) return fitness;

		int attacks = queensOnSameLines(rows, false, false);
		fitness = maxFitness - attacks; // non-attacking pairs

		return fitness;
	}

	@Override
	public State reproduce(State state) {
		GameState board = (GameState)state;
		assert(board.getColumns().length == size);

		int dividingIndex = random.nextInt(size - 1) + 1;
		Tile[] newColumns = new Tile[size];

		Tile tile;
		for (int i = 0; i < size; ++i) {
			if (i < dividingIndex) tile = rows[i];
			else tile = board.getColumns()[i];

			newColumns[i] = tile;
		}

		return new GameState(newColumns);
	}

	@Override
	public State mutate() {
		return createNewState(random.nextInt(size), random.nextInt(size));
	}

	@Override
	public boolean isSolution() {
		return queensOnSameLines(rows, true) == 0;
	}

	public ArrayList<Tile> disjointRightSet(Tile[] tiles) {
		ArrayList<Tile> disjointSet = new ArrayList<>();
		assert(tiles.length == this.size);

		for (int i = 0; i < this.size; ++i) {
			if (tiles[i].compareTo(rows[i]) == 0) continue;
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

		for (Tile tile : rows) {
			if (tile == null) stringBuilder.append("null");
			else stringBuilder.append(tile.getRow());
			stringBuilder.append(" ");
		}

		return stringBuilder.toString().trim();
	}

	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public int compareTo(State o) {
		return o.fitness() - fitness();
	}
}
