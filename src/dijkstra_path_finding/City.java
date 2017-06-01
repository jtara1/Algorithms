package dijkstra_path_finding;

/**
 * Representation of a city to contain the relevant info
 * @author j
 *
 */
public class City extends Data {
	/**
	 * The two character code for this city (see {@link CityKey})
	 */
	private String code;
	
	/**
	 * Name of city
	 */
	private String name;
	
	/**
	 * Population in city
	 */
	private int population;
	
	/**
	 * Elevation of city
	 */
	private float elevation;
	
	/**
	 * Construct a city assigning each attribute to arguments passed
	 * @param cityIndex
	 * @param cityCode
	 * @param cityName
	 * @param cityPopulation
	 * @param cityElevation
	 */
	public City(int cityIndex, String cityCode, String cityName, int cityPopulation, float cityElevation) {
		index = cityIndex;
		code = cityCode;
		name = cityName;
		population = cityPopulation;
		elevation = cityElevation;
	}
	
	/**
	 * Use for printing for clarity of attribute values of this city
	 */
	@Override
	public String toString() {
		return String.format("index = %d, code = %s, name = %s, population = %d, elevation = %.2f", 
				index, code, name, population, elevation);
	}
	
	public int getIndex() {
		return index;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getPopulation() {
		return population;
	}

	public float getElevation() {
		return elevation;
	}
}
