package dijkstra_path_finding;

/**
 * Represents an abbreviation for a city.
 * e.g.: CT, KV, LA, BO, PS
 * @author j
 *
 */
public class CityKey implements DataKey {
	private String code;
	
	CityKey(String cityCode) {
		code = cityCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		return code.equals(((CityKey)obj).getCode());
	}
	
	/**
	 * Overrides the {@link #compareTo(Object)} method as the
	 * parent class this class inherits from extends the interface
	 * Comparable<T>
	 * @param obj
	 * @return The comparison of the two {@link #code} 
	 */
	@Override
	public int compareTo(DataKey obj) {
		return code.compareTo(((CityKey)obj).getCode());
	}
	
	public String getCode() {
		return code;
	}
}
