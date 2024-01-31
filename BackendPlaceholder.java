// --== CS400 Fall 2023 File Header Information ==--
// Name: Lucas Cheng
// Email: lcheng77@wisc.edu
// Group: E03
// TA: Zheyang Xiong
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.IOException;
import java.util.List;

public class BackendPlaceholder <NodeType, EdgeType extends Number>
        extends DijkstraGraph<NodeType, EdgeType>
        implements BackendInterface<NodeType, EdgeType> {

    /**
     * Constructor for BaseGraph that provides the map the graph uses.
     *
     * @param map the map the graph uses to map a data object to the node object
     *            it is stored in
     */
    public BackendPlaceholder(MapADT<NodeType, Node> map){
        super(map);
    }

    /**
     * Default constructor for Backend
     */
    // public BackendPlaceholder(DijkstraGraph<String, Integer> graph) {}

    /**
     * Reads in user-specified data and loads into graph
     */
    @Override
    public void readData(String fileName) throws IOException {

    }

    /**
     *  Get a list of the shortest path of friends between two specified participants
     *
     * @param part1 - first participant
     * @param part2 - second participant
     * @return Instance of shortestPath between the two participants
     */
    @Override
    public shortestPathResultInterface<NodeType, EdgeType> getClosestConnection(NodeType part1, NodeType part2) {
        return null;
    }

    /**
     * Returns dataset statistics
     *
     * @return String containing 1.) number of nodes 2.) number of edges 3.) average number of
     * friends of participants.
     */
    @Override
    public String listDataSetInfo() {
        return null;
    }
}
