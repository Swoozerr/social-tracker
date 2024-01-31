// --== CS400 File Header Information ==--
// Name: Lucas Cheng
// Email: lcheng77@wisc.edu
// Group and Team: E03
// Group TA: Zheyang Xiong
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * @param map the map that the graph uses to map a data object to the node
     *        object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // when reaching end node, return that node
        PriorityQueue<SearchNode> queue = new PriorityQueue<>(); // make queue of search nodes

        //use as set, insert visited graph nodes as key val pairs and containskey to check if visited
        PlaceholderMap<NodeType, SearchNode> hashmap = new PlaceholderMap<>();

        // create instance of starting SearchNode and push to map
        Node startNode = this.nodes.get(start); // handles no such element exception for start
        SearchNode frontier = new SearchNode(startNode, 0, null);
        queue.add(frontier);
        hashmap.put(frontier.node.data, frontier);

        while(!queue.isEmpty()) {
            SearchNode currNode = queue.poll(); // remove lowest priority
            if (currNode.node.data.equals(end)) { // found end node, return this Search node as guaranteed shortest path
                return currNode;
            }
            // if (!hashmap.containsKey(currNode.node.data)) { don't need this because adjacent check guarantees +
            //    hashmap.put(currNode.node.data, currNode);        it conflicts with the other put
            for (Edge edge : currNode.node.edgesLeaving) { // get all edges stemming out from this pred node
                Node adjacentNode = edge.successor; // this is successor node
                double newTotalWeight = currNode.cost + edge.data.doubleValue(); // add pred + successor edge weight

                // if node not yet visited just add it
                if (!hashmap.containsKey(adjacentNode.data)) {
                    SearchNode adjacentSearchNode = new SearchNode(adjacentNode, newTotalWeight, currNode);
                    queue.add(adjacentSearchNode);
                    hashmap.put(adjacentSearchNode.node.data, adjacentSearchNode); // push to map with key: data, Value: node itself
                }
                // node visited but found better weight, push newly found to queue after removing original from queue
                //     effectively replacing the old shortest path code for that node
                else if (newTotalWeight <= hashmap.get(adjacentNode.data).cost) {
                    queue.remove(hashmap.get(adjacentNode.data));
                    SearchNode adjacentSearchNode = new SearchNode(adjacentNode, newTotalWeight, currNode);
                    queue.add(adjacentSearchNode);
                    hashmap.get(adjacentNode.data).cost = newTotalWeight;
                }
            }
        }
        throw new NoSuchElementException("end does exist in graph or no path found");
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        ArrayList<NodeType> nodeData = new ArrayList<>();
        SearchNode currNode = computeShortestPath(start,end); // returns ending SearchNode
        while(currNode != null) { // backtrack through shortest path and add to Arraylist
            nodeData.add(currNode.node.data);
            currNode = currNode.predecessor;
        }

        // reverse arraylist
        Collections.reverse(nodeData);

        return nodeData;
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        double totalCost = 0.0;

        SearchNode predNode = computeShortestPath(start,end);
        SearchNode succNode = predNode;
        predNode = predNode.predecessor;
        while(predNode != null) { // backtrack through shortest path and add edge weights
            // get edge weight of pred node and succesor node and add to total, then move both pointers back a node
            totalCost += getEdge(predNode.node.data, succNode.node.data).doubleValue();
            succNode = predNode;
            predNode = predNode.predecessor;
        }
        return totalCost;
    }

    // TODO: implement 3+ tests in step 4.1
    /**
     * tests for correct implementation of dijkstra on a graph used in lecture Week 09 11/2
     *     and correct shortest path cost for D -> I
     */
    @Test public void testShortestPath() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        graph.insertEdge("A","B",1);
        graph.insertEdge("A","M",5);
        graph.insertEdge("A","H",8);

        graph.insertEdge("B","M",3);

        graph.insertEdge("D","A",7);
        graph.insertEdge("D","G",2);

        graph.insertEdge("F","G",9);

        graph.insertEdge("G","L",7);

        graph.insertEdge("H","B",6);
        graph.insertEdge("H","I",2);

        graph.insertEdge("I","D",1);
        graph.insertEdge("I","L",5);
        graph.insertEdge("I","H",2);

        graph.insertEdge("M","F",4);
        graph.insertEdge("M","E",3);

        Assertions.assertEquals(17, graph.shortestPathCost("D", "I"), "expected 17 " +
                "but was "+ graph.shortestPathCost("D", "I"));

        Assertions.assertEquals("[D, A, H, I]", graph.shortestPathData("D", "I").toString());
    }

    /**
     * tests for correct implementation of dijkstra on a graph used in lecture Week 09 11/2
     *     and the sequencing is correct along a differing path A -> F and D -> L
     */
    @Test public void testSequenceAlongPath() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        graph.insertEdge("A","B",1);
        graph.insertEdge("A","M",5);
        graph.insertEdge("A","H",8);

        graph.insertEdge("B","M",3);

        graph.insertEdge("D","A",7);
        graph.insertEdge("D","G",2);

        graph.insertEdge("F","G",9);

        graph.insertEdge("G","L",7);

        graph.insertEdge("H","B",6);
        graph.insertEdge("H","I",2);

        graph.insertEdge("I","D",1);
        graph.insertEdge("I","L",5);
        graph.insertEdge("I","H",2);

        graph.insertEdge("M","F",4);
        graph.insertEdge("M","E",3);

        Assertions.assertEquals(4, graph.shortestPathCost("A", "M"), "expected 4 " +
                                "but was " + graph.shortestPathCost("A", "M"));
        Assertions.assertEquals(8, graph.shortestPathCost("A", "F"), "expected 8 " +
                                "but was " + graph.shortestPathCost("A", "F"));

        Assertions.assertEquals(9, graph.shortestPathCost("D", "L"), "expected 9 " +
                                "but was " + graph.shortestPathCost("D", "L"));

        try {
            graph.shortestPathCost("F", "I");
            Assertions.assertTrue(false, "exception not thrown when trying shortestPath on " +
                    "nonexisting path");
        }
        catch (NoSuchElementException e){
        }
        catch (Exception e){
            Assertions.assertTrue(false, "Incorrect exception thrown");
        }

    }

    /**
     * tests for correct implementation of dijkstra on a graph used in lecture Week 09 11/2
     *     and that a no such element exception is thrown when a path doesn't exist
     */
    @Test public void testUndirectedGraph() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        try {
            graph.shortestPathCost("F", "E");
            Assertions.assertTrue(false, "exception not thrown when trying shortestPath on " +
                                "nonexisting path");
        }
        catch (NoSuchElementException e){
        }
        catch (Exception e){
            Assertions.assertTrue(false, "Incorrect exception thrown");
        }

        // self made graph based off lecture graph with lots of dead ends and bad paths that should be updated
        graph.insertEdge("A","B",5);
        graph.insertEdge("A","M",8);
        graph.insertEdge("A","H",2);
        graph.insertEdge("H","B",2);
        graph.insertEdge("B","M",3);
        graph.insertEdge("M","E",12);
        graph.insertEdge("M","F",4);
        graph.insertEdge("M","L",1);
        graph.insertEdge("F","E",5);
        graph.insertEdge("F","G",5);
        graph.insertEdge("G","M",1);

        Assertions.assertEquals("[A, H, B, M, F, E]", graph.shortestPathData("A", "E").toString());
        Assertions.assertEquals(16, graph.shortestPathCost("A", "E"), "expected 4 " +
                "but was " + graph.shortestPathCost("A", "E"));

    }
}
