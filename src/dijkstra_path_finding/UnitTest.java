package dijkstra_path_finding;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Contains a number of methods that test the functionality the {@link DirectedGraph}
 * it's features, and associated classes.
 * Relies on manual checking for confirmation of expected behavior.
 * @author j
 *
 */
public class UnitTest {
	public static void main(String[] args) {
		UnitTest ut = new UnitTest();
		ut.testCase1();
	}
	
	/**
	 * Testing DataKey comparisons that'll later be used in Hashtable
	 */
	public void testData() {
		DataKey k1 = new CityKey("AN");
		DataKey k2 = new CityKey("AN");
		
		System.out.printf("%d\n%d\n", k1.hashCode(), k2.hashCode());
		System.out.printf("Hash codes of two DataKeys of the same value are equal? %b\n", k1.hashCode() == k2.hashCode());
		System.out.printf("Two DataKeys containing same values are equal? %b\n", k1.equals(k2));
	}
	
	/**
	 * Partial testing of dictionary creation for use in directed graph
	 */
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
	
	/**
	 * Basic test case for inserting and removing an edge in the graph
	 */
	public void testCase1() {
		DirectedGraph graph = new DirectedGraph(new CityRoadLoader());
		System.out.println(graph);
		
		// remove edge (vertices linked: 0 -> 1)
		graph.removeEdge(new CityKey("AN"), new CityKey("BK"));
		System.out.println(graph);
		
		// add edge (vertices linked: 0 -> 2)
		graph.insertEdge(0, 2, 100f);
		System.out.println(graph);
	}
}
