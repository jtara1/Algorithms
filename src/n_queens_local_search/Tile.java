package n_queens_local_search;

public class Tile {
	private int row; // y
	private int col; // x

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
	}
}
