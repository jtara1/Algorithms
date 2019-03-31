package n_queens_local_search;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/* array utils intended for use with n-queens project */
public class ArrayUtils {
	private static Random random = new Random();

	public static Tile sample(Tile[] objects) {
		return objects[random.nextInt(objects.length)];
	}
	public static State sample(ArrayList<State> objects) {
		return objects.get(random.nextInt(objects.size()));
	}

	public static Tile[] filter(GameState state_pointer, Function<Integer, Boolean> function) {
		ArrayList<Tile> list = new ArrayList<>();
		Tile[] rows = state_pointer.getColumns();

		for (int i = 0; i < rows.length; ++i) {
			int attackable = GameState.queensOnSameLines(rows, i);
			if (function.apply(attackable)) list.add(rows[i]);
		}

		return listToArray(list);
	}

	/* if cost is not 0, return true */
	public static Boolean filterOutZeroCostTiles(int cost) {
		return cost != 0;
	}

	public static Tile[] listToArray(List<Tile> tiles) {
		Tile[] array = new Tile[tiles.size()];

		int index = 0;
		for (Tile tile : tiles) {
			array[index] = tile;
			index++;
		}

		return array;
	}
}
