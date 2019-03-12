package n_queens_local_search;

public class Tile {
	// attrs
	private int row; // y
	private int col; // x

	// get and set
	public int getRow() {
		return row;
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
}
