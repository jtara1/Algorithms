package dijkstra_path_finding;

import java.util.Objects;

/**
 * Represents an abbreviation for a city.
 * For all Cities, each associated CityKeys is unique
 * e.g.: CT, KV, LA, BO, PS, LV
 * @author j
 *
 */
public class CityKey implements DataKey {
	/**
	 * Two characters representing a city
	 */
	private String code;
	
	CityKey(String cityCode) {
		if (cityCode.length() != 2)
			throw new IllegalArgumentException("city code must be exactly 2 letters");
		code = cityCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		return code.equals(((CityKey)obj).getCode());
	}
	
	/**
	 * The hash code of this object will be created from the hash of the two character
	 * code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(code);
	}
	
	public String getCode() {
		return code;
	}
}
