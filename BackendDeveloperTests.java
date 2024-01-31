// --== CS400 File Header Information ==--
// Name: Jakson Amend
// Email: jamend2@wisc.edu
// Group and Team: E03
// Group TA: Zheyang Xiong
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class BackendDeveloperTests {
	
	/**
	 * Tests that readFile throws an exception
	 */
	@Test
	public void JUnitTest1(){
		
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
		Backend backend = new Backend(graph);
		
		assertThrows(Exception.class, () -> backend.readData("wrongfile"), "Exception should be thrown with wrong file input");
		
	}	

	/**
	 * Tests that readFile builds the correct DijkstraGraph
	 */
	@Test
	public void JUnitTest2(){
		
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
		Backend backend = new Backend(graph);
		
		try {
			backend.readData("socialnetwork.dot");
		} catch (IOException e) {
			System.out.println("Exception caught!");
		}
		
		assertEquals(488,graph.edgeCount, "Wrong number of edges!");
		assertEquals(100, graph.getNodeCount(), "Wrong number of nodes!");
	}
	
	/**
	 * Tests getClosestConnection method when direct friendship
	 */
	@Test
	public void JUnitTest3(){
		
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
		Backend backend = new Backend(graph);
		
		try {
			backend.readData("socialnetwork.dot");
		} catch (IOException e) {
			System.out.println("Exception caught!");
		}
		
		shortestPathResult expectedPath = backend.getClosestConnection("user5", "user69");
		assertTrue(expectedPath.getNumIntFriends() == 0, "Number of intermediary friends should be 0");
		assertTrue(expectedPath.getPath().isEmpty(), "getPath should be empty");
		
		
	}
	
	/**
	 * Tests getClosestConnection method with intermediary friends
	 */
	@Test
	public void JUnitTest4() {
		
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
		Backend backend = new Backend(graph);
		
		try {
			backend.readData("socialnetworktest.dot");
		} catch (IOException e) {
			System.out.println("Exception caught!");
		}
		shortestPathResult actualPath1 = backend.getClosestConnection("user17", "user55");
		
		assertEquals(1, actualPath1.getNumIntFriends(), "Wrong # of intermediary friends!");
		assertEquals("[user8]", actualPath1.getPath().toString(), "Wrong path data!");
		
		shortestPathResult actualPath2 = backend.getClosestConnection("user17", "user63");
		
		assertEquals(2, actualPath2.getNumIntFriends(), "Wrong # of intermediary friends!");
		assertEquals("[user87, user0]", actualPath2.getPath().toString(), "Wrong path data!");
	}
	
	/**
	 * Tests whether listDataSetInfo returns the correct String for the dataset. Uses socialnetworktest.dot
	 */
	@Test
	public void JUnitTest5(){
		
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
		Backend backend = new Backend(graph);
		
		try {
			backend.readData("socialnetworktest.dot");
		} catch (IOException e) {
			System.out.println("Exception caught!");
		}

		String expected = "Total number of users: 14\n"
				+ "Total number of connections: 16\n"
				+ "Average number of connections per user: 2";
		
		assertEquals(expected, backend.listDataSetInfo(), "Wrong info returned!");
	}	
	
	/**
	 * Tests whether listDataSetInfo returns the correct String for the dataset. Uses project dot file.
	 */
	@Test
	public void JUnitTest6(){
		
		DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
		Backend backend = new Backend(graph);
		
		try {
			backend.readData("socialnetwork.dot");
		} catch (IOException e) {
			System.out.println("Exception caught!");
		}
		
		String expected = "Total number of users: 100\n"
				+ "Total number of connections: 244\n"
				+ "Average number of connections per user: 4";
		
		assertEquals(expected, backend.listDataSetInfo(), "Wrong info returned!");
		
	}	
}

