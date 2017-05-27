package dijkstra_path_finding;

import java.util.LinkedList;
import java.util.List;

public class Path {
	public interface Printer {
		public abstract String getString();
	}
	
	private LinkedList<Integer> path = new LinkedList<Integer>();
	private Float distance = null;
	private Printer printer = null;
	
	public Path(LinkedList<Integer> verticesSequence, Float totalDistance) {
		path = verticesSequence;
		distance = totalDistance;
	}
	
	public Path(Integer[] parents, int source, int destination, Float totalDistance) {
		int nextParent = destination;
		path.addFirst(destination);
		
		while (nextParent != source) {
			path.addFirst(parents[nextParent]);
			nextParent = parents[nextParent];
		}
		
		distance = totalDistance;
	}
	
	public LinkedList<Integer> getPath() {
		return path;
	}
	
	public Float getDistance() {
		return distance;
	}
	
	public Printer getPrinter() {
		return printer;
	}
	
	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
}
