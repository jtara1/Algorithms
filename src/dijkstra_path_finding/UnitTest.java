package dijkstra_path_finding;

import java.util.Dictionary;
import java.util.Hashtable;

public class UnitTest {
	public static void main(String[] args) {
		UnitTest ut = new UnitTest();
		ut.basicTest2();
	}
	
	public void basicTest() {
		Dictionary<Integer, CityKey> d1 = new Hashtable<Integer, CityKey>();
		Dictionary<CityKey, City> d2 = new Hashtable<CityKey, City>();
		
		CityKey key1 = new CityKey("KV");
		CityKey key2 = new CityKey("LV");
		d1.put(0, key1);
		d1.put(1, key2);
		
		d2.put(key1, new City(0, key1.getCode(), "Kenny Ville", 10000, 1000));
		d2.put(key2, new City(1, key2.getCode(), "Las Vegas", 1000000, 0));
		
//		BidirectionGraph<Integer> graph = new BidirectionGraph(d1, d2, "road.dat");
	}
	
	public void basicTest2() {
		UnidirectionGraph graph = new UnidirectionGraph(new CityRoadLoader());
		System.out.println(graph);
	}
}
