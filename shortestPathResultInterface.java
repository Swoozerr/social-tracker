import java.util.List;

/**
 * Interface for a method to store the results of a shortest path search
 */
public interface shortestPathResultInterface<NodeType, EdgeType extends Number> {
	
	/**
	 * Creates instance of shortestPathResult
	 * 
	 * @param list of friends in between
	 * @param number of friends in between
	 */
	// public shortestPathResult(List<NodeType> path, double cost){}
	
	/**
	 * Access list of intermediary friends from the result of a shortest path search
	 * 
	 * @return list of intermediary friends between the two participants
	 */
	public List<NodeType> getPath();
	
	/**
	 * Access number of intermediary friends from the result of a shortest path search
	 * 
	 * @return number of intermediary friends that connect the two specified participants
	 */
	public int getNumIntFriends();

}

