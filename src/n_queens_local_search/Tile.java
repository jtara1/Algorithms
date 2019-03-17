package n_queens_local_search;

import java.util.ArrayList;

public class Tile implements Comparable<Tile> {
	// attrs
	private int row; // y
	private int col; // x

	// get and set
	public int getRow() {
		return row;
	}

	public void setRow(int value) {
		row = value;
	}

	public int getCol() {
		return col;
	}

	// static
	public static Tile[] rowSequenceToTileArray(int ... rowIndices) {
		Tile[] tiles = new Tile[rowIndices.length];

		int index = 0;
		for (int row : rowIndices) {
			tiles[index] = new Tile(row, index);
			++index;
		}

		return tiles;
	}

	// constructors
	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
	}

	// method Object overrides
	@Override
	public int compareTo(Tile other) {
		return row - other.getRow();
	}

	public String toString() {
		return new Integer(row).toString();
	}
}
