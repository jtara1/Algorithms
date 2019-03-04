package eight_puzzle_a_star;

import java.util.ArrayList;

public class GraphAndPathCollection {
	public Graph graph;
	public Path path;
	public ArrayList<Byte> board;

	public GraphAndPathCollection(Graph graph, Path path, ArrayList<Byte> board) {
		this.graph = graph;
		this.path = path;
		this.board = board;
	}

	public String toStringResults() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(graph.toString());
		stringBuilder.append(path.toStringResults() + "\n");
		return stringBuilder.toString();
	}
}
