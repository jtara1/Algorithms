package dijkstra_path_finding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		return String.format("index = {0}, code = {1}, name = {2}, population = {3}, elevation = {4}", 
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
