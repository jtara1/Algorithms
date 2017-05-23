package dijkstra_path_finding;

/**
 * Represents an abbreviation for a city.
 * e.g.: CT, KV, LA, BO, PS
 * @author j
 *
 */
public class CityKey extends DataKey {
	private String code;
	
	CityKey(String cityCode) {
		code = cityCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		return code.equals(((CityKey)obj).getCode());
	}
	
	public String getCode() {
		return code;
	}
}
