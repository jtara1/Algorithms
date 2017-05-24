package dijkstra_path_finding;

import java.util.Dictionary;

public interface DataLoader {
	public abstract Dictionary<Integer, DataKey> getIndexToKeyDict();
	public abstract Dictionary<DataKey, Data> getKeyToData();
	public abstract Float[][] getAdjMatrix();
}
