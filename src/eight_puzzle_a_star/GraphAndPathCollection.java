package eight_puzzle_a_star;

import java.util.ArrayList;

public class GraphAndPathCollection {
	public Graph graph;
	public Path path;
	public ArrayList<Byte> board_pointer;

	public GraphAndPathCollection(Graph graph, Path path, ArrayList<Byte> board_pointer) {
		this.graph = graph;
		this.path = path;
		this.board_pointer = board_pointer;
	}

	public String toStringResults() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(graph.toString());
		stringBuilder.append(path.toStringResults() + "\n");
		return stringBuilder.toString();
	}
}
