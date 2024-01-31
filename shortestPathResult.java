// --== CS400 File Header Information ==--
// Name: Jakson Amend
// Email: jamend2@wisc.edu
// Group and Team: E03
// Group TA: Zheyang Xiong
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.List;

public class shortestPathResult implements shortestPathResultInterface<String, Integer> {
	
	private List<String> path; 		// Path of friends in between
	private int cost;				// Number of friends in between
	
	/**
	 * Creates instance of shortestPathResult
	 * 
	 * @param list of friends in between
	 * @param number of friends in between
	 */
	public shortestPathResult(List<String> path, int cost){
		this.path = path;
		this.cost = cost;
	}
	
	/**
	 * Access list of intermediary friends from the result of a shortest path search
	 * 
	 * @return list of intermediary friends between the two participants
	 */
	public List<String> getPath(){
		
		return path;
	}
	
	/**
	 * Access number of intermediary friends from the result of a shortest path search
	 * 
	 * @return number of intermediary friends that connect the two specified participants
	 */
	public int getNumIntFriends() {
		
		return cost;
	}

}

