// --== CS400 File Header Information ==--
// Name: Jakson Amend
// Email: jamend2@wisc.edu
// Group and Team: E03
// Group TA: Zheyang Xiong
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Backend implements BackendInterface<String, Integer>{
	
	GraphADT<String, Integer> dataStructure;		// Store implementation of GraphADT

	/**
	 * Backend constructor that takes type DijkstraGraph
	 */
	public Backend(DijkstraGraph<String, Integer> graph) {
		this.dataStructure = graph;
	}
	
	/**
	 * Reads in user-specified data and loads into graph
	 * 
	 * @throws IOException when file cannot be read
	 */
	public void readData(String fileName) throws IOException {
		
		// first scan to insert all non-duplicate nodes into Dijkstra
		BufferedReader reader1 = new BufferedReader(new FileReader(fileName));
		String line;
		boolean isFirstLine = true;
		
		while((line = reader1.readLine()) != null) {
			// skip first line
			if(isFirstLine) {
				isFirstLine = false;
				continue;
			}
			// next loop if it doesn't contain the -- separator
			if (!line.contains("--")) {
				break;
			}
			String[] splitResult = new String[2];
			splitResult = line.split("--");
			
			// split array into two strings. trim, then replace quotations and semi-colon, as necessary
			String left = splitResult[0].trim().replace("\"", "");
			String right = splitResult[1].trim().replace("\"", "").replace(";", "");
			
			// add each unique user on first pass, generating the total # of nodes in the graph
			if(!dataStructure.containsNode(left)) {
				dataStructure.insertNode(left);
			}
			if(!dataStructure.containsNode(right)) {
				dataStructure.insertNode(right);
			}
		}
		reader1.close();
		
		// second scan to insert two-way friendships between users
		BufferedReader reader2 = new BufferedReader(new FileReader(fileName));
		String line2;
		boolean isFirstLine2 = true;
		
		while((line2 = reader2.readLine()) != null) {
			// skip first line
			if(isFirstLine2) {
				isFirstLine2 = false;
				continue;
			}
			// end when at bottom of file
			if(line2.contains("}")) {
				break;
			}
			String[] splitResult = new String[2];
			splitResult = line2.split("--");
			
			// split array into two strings. trim, then replace quotations and semi-colon, as necessary
			String left = splitResult[0].replace("\"", "").trim().replace("\r", "").replace("\n", "");
			String right = splitResult[1].replace("\"", "").replace(";", "").trim().replace("\r", "").replace("\n", "");
			
			// clear self-directed arrows for easier graph visualization
			if(left.equals(right)) {
				continue;
			}
			
			// insert two-way edges between nodes for an undirected arrow
			// costs are 1 for each node to 
			dataStructure.insertEdge(left, right, 1);
			dataStructure.insertEdge(right, left, 1);
		}
		reader2.close();
	}
	
	/**
	 *  Get a list of the shortest path of friends between two specified participants
	 * 
	 * @param part1 - first participant
	 * @param part2 - second participant
	 * @return Shortest path of friends between the two participants
	 */
	public shortestPathResult getClosestConnection(String part1, String part2){
		
		List<String> listOfFriends = dataStructure.shortestPathData(part1, part2);
		// Trim edges to get number of friends in between
		listOfFriends.remove(0);
		listOfFriends.remove(listOfFriends.size()-1);
		
		int shortestPathCost = (int)dataStructure.shortestPathCost(part1, part2) - 1; 	// # friends in between
		return new shortestPathResult(listOfFriends, shortestPathCost);
	}
	
	/**
	 * Returns dataset statistics 
	 * 
	 * @return String containing 1.) number of nodes 2.) number of edges 3.) average number of
	 * friends of participants.
	 */
	public String listDataSetInfo() {
		
		int numConnections = 0;							// counter used to store # connections for each node
		List<Integer> listInts = new ArrayList<>();		// store values in ArrayList
		
		
		// outer for shortestPathData's first parameter
		for(int i = 0; i < 99; i++) {
			// if node does not exist, skip
			if(!dataStructure.containsNode("user" + i)) {
				continue;
			}
			
			// sum to the total number of connections for user[i]
			for(int j = 0; j < 99; j++) {
				// if node does not exist, skip
				if(!dataStructure.containsNode("user" + j)) {
					continue;
				}
				
				// capture shortest paths with size 2... this shows a direct connection to another user
				if(dataStructure.shortestPathData("user" + i, "user" + j).size() == 2) {
					numConnections++;
				}
			}
			
			// build ArrayList with size equal to total number of users
			// containing number of friends for user0 -> user99
			listInts.add(numConnections);
			numConnections = 0;
		}
		
		// traverse ArrayList and sum the values
		int sum = 0;
		for(int i = 0; i < listInts.size(); i++) {
			sum = sum + listInts.get(i);
		}
		
		// calculate average
		int avg = sum/listInts.size();
		
		return "Total number of users: " + dataStructure.getNodeCount() + "\n"
				+ "Total number of connections: " + dataStructure.getEdgeCount()/2 + "\n"
				+ "Average number of connections per user: " + avg;
	}
}

