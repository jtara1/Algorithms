package dijkstra_path_finding;

public class City extends Data {
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
		return String.format("index = {0}, code = {1}, name = {2}, population = {3}, elevation = {4}", 
				index, code, name, population, elevation);
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
