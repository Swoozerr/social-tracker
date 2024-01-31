import java.io.IOException;
import java.util.List;

/**
 * Backend Interface for P2 Social App. Communicates with GraphADT implementation and returns values for Frontend
 */
public interface BackendInterface<NodeType, EdgeType extends Number> {

	/**
	 * Default constructor for Backend
	 */
	// public Backend(DijkstraGraph<String, Integer> graph) {}
	
	/**
	 * Reads in user-specified data and loads into graph
	 */
	public void readData(String fileName) throws IOException;
	
	/**
	 *  Get a list of the shortest path of friends between two specified participants
	 * 
	 * @param part1 - first participant
	 * @param part2 - second participant
	 * @return Instance of shortestPath between the two participants
	 */
	public shortestPathResultInterface<NodeType, EdgeType> getClosestConnection(NodeType part1, NodeType part2);
	
	/**
	 * Returns dataset statistics 
	 * 
	 * @return String containing 1.) number of nodes 2.) number of edges 3.) average number of
	 * friends of participants.
	 */
	public String listDataSetInfo();
	
}


