package eight_puzzle_a_star;

import java.util.Comparator;

public class PathComparator implements Comparator<Path> {
	public int compare(Path path1, Path path2) {
		return path1.getCost() - path2.getCost();
	}
}
