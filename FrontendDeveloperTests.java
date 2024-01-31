// --== CS400 Fall 2023 File Header Information ==--
// Name: Lucas Cheng
// Email: lcheng77@wisc.edu
// Group: E03
// TA: Zheyang Xiong
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FrontendDeveloperTests {

    /**
     * Tests for amount of nodes in graph by reading .dot file and using getNodeCount()
     */
    @Test
    public void testBackendNodeCount(){
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);

        try {
            backend.readData("src/socialnetwork.dot");
        } catch (IOException e) {
            System.out.println("couldn't read file");
        }

        assertEquals(100, graph.getNodeCount(), "returned " + graph.getNodeCount() + " but should be 100");
    }

    /**
     * Tests the reading of a .dot file and that the statistics are correct
     * @throws IOException - File cannot be read
     *
     */
    @Test
    public void testBackendReadData() throws IOException {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        try {
            // first 10 lines of socialnetwork.dot
            backend.readData("src/socialnetworktest.dot");
        }
        catch (Exception e) {
            fail("file failed to be read");
        }

        String listData = backend.listDataSetInfo();
        assertTrue(listData.contains("connections: 16"));
        assertTrue(listData.contains("users: 14"));
    }

    // tests the finding of shortestPath
    @Test
    public void integrationTestListDataInfo() throws IOException {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        try {
            backend.readData("src/socialnetworkFrontendTest.dot");
        }
        catch (Exception e) {
            fail("file failed to be read");
        }

        shortestPathResultInterface<String, Integer> shortestPath = backend.getClosestConnection("user0", "user8");
        assertEquals(1, shortestPath.getNumIntFriends());

        // only intermediary is user26 in test file
        assertEquals("user26", shortestPath.getPath().get(0));
    }

    /**
     * Tester for frontend correctly displaying the command loop options
     */
    @Test
    public void testFrontendDisplayCommands() {
        //tests for output from starting and quitting the program
        TextUITester uiTester = new TextUITester("1\nfakefileDNE\n4\n");

        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);
        frontend.runCmdLoop(); // TestUITester gets output from command loop

        // will contain output of menu
        String output = uiTester.checkOutput();

        assertTrue(output.contains("Closing Program"));
    }

    /**
     * Tester for frontend correctly ending the program immediately
     */
    @Test
    public void testFrontendExitApp() {
        TextUITester uiTester = new TextUITester("4\n"); //tests for input from option 1, 4

        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);
        frontend.runCmdLoop(); // TestUITester gets output from command loop

        String output = uiTester.checkOutput();

        assertTrue(output.contains("Closing Program"));
    }
    /**
     * Tester for frontend integration for correctly calling loadFile() and reading dot
     */
    @Test
    public void testIntegrationFrontendLoadFile() throws FileNotFoundException {
        TextUITester uiTester = new TextUITester("1\nsrc/socialnetwork.dot\n4\n"); //tests for input from option 1, 4

        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);
        frontend.runCmdLoop(); // TestUITester gets output from command loop

        String output = uiTester.checkOutput();

        assertTrue(output.contains("File loaded successfully."));
    }

    /**
     * Tester for frontend integration for displaying shortest path between user0 and user8 from frontend test file
     */
    @Test
    public void testIntegrationFrontendShortestPath() throws FileNotFoundException {
        TextUITester uiTester = new TextUITester("1\nsrc/socialnetworkFrontendTest.dot\n2\nuser0\nuser8\n4\n");

        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);
        frontend.runCmdLoop(); // TestUITester gets output from command loop

        String output = uiTester.checkOutput();
        System.out.println(output);

        assertTrue(output.contains("Here's the shortest path between those 2 users"));

        assertTrue(output.contains("[user26]"));
        assertTrue(output.contains("1"));
    }

    /**
     * Tester for frontend integration for displaying  data from the social media dataset, in particular the:
     *      the number of participants (nodes), the number of edges (friendships), and the avg number of friends
     *
     */
    @Test
    public void testIntegrationFrontendPrintStats() throws FileNotFoundException {
        TextUITester uiTester = new TextUITester("1\nsrc/socialnetworktest.dot\n3\n4\n");

        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);
        frontend.runCmdLoop(); // TestUITester gets output from command loop

        String output = uiTester.checkOutput();

        assertTrue(output.contains("Userbase statistics list:"));
        assertTrue(output.contains("users: 14"));
        assertTrue(output.contains("connections: 16"));
        assertTrue(output.contains("Average number of connections per user: 2"));
    }

}
