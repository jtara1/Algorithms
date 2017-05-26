package dijkstra_path_finding;

public class City extends Data {
	private int index;
	private String code;
	private String name;
	private int population;
	private float elevation;
	
	City(int cityIndex, String cityCode, String cityName, int cityPopulation, float cityElevation) {
		index = cityIndex;
		code = cityCode;
		name = cityName;
		population = cityPopulation;
		elevation = cityElevation;
	}
	
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
