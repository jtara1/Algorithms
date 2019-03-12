package n_queens_local_search;

public class LinearEquation {
	private int xIntercept;
	private int yIntercept;
	private int slope;

	public LinearEquation(int xIntercept, int yIntercept) {
		this.xIntercept = xIntercept;
		this.yIntercept = yIntercept;
		this.slope = 1;
	}

	public LinearEquation(int slope, int xIntercept, int yIntercept) {
		this(xIntercept, yIntercept);
		this.slope = slope;
	}

	/* 1 if it intersects, 0 if it doesn't */
	public int intersects(int x, int y) {
		return y == (slope * (x - xIntercept) + yIntercept) ?
				1 :
				0;
	}
}
