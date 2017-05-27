package dijkstra_path_finding;

import java.util.Dictionary;

public interface DataLoader {
	public abstract Dictionary<Integer, DataKey> getIndexToKey();
	public abstract Dictionary<DataKey, Data> getKeyToData();
	public abstract Float[][] getAdjMatrix();
}
