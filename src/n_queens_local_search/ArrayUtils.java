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

	public static Tile[] filter(GameState state, Function<Integer, Boolean> function) {
		ArrayList<Tile> list = new ArrayList<>();
		Tile[] columns = state.getColumns();

		for (int i = 0; i < columns.length; ++i) {
			int attackable = GameState.queensOnSameLines(columns, i);
			if (function.apply(attackable)) list.add(columns[i]);
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
