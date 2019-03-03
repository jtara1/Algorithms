package eight_puzzle_a_star;

public class NullPath extends Path {
	private String message = "null path; no path was found";

	public NullPath() {
		super(null);
	}

	public NullPath(Vertex rootVertex) {
		super(rootVertex);
	}

	public NullPath(Vertex rootVertex, String message) {
		this(rootVertex);
		this.message = message;
	}

	// Object overrides
	public String toString() {
		return "null path; no path was found";
	}

	public String toStringFullPath() {
		return super.toStringFullPath() + "\n" + message;
	}
}
