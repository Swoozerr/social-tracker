import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface defines methods that will be used to implement a Frontend for a social media platform searcher
 */

public interface FrontendInterface <T extends Comparable<T>>{
    /* Constructor takes in backend reference and Scanner
     * public FrontendInterface(Backend){}
     */

    /**
     * Used to run an iteration of the project, continually printing a menu of choices while project
     *     is running
     */
    public void runCmdLoop();

    /**
     * uses Backend's file loader method to read a dot file into a Graph ADT
     * @param file - name of file to be read
     */
    public void loadFile(String file) throws IOException;

    /**
     * command that asks the user for a start and destination user, then shows the closest connection between
     *     the two, including all intermediary friends
     * @param start - user Node to begin at
     * @param end - user Node to end at
     */
    public void shortestPath(T start, T end);

    /**
     * displays to the user data from the social media dataset, in particular the: the number of participants
     *     (nodes), the number of edges (friendships), and the average number of friends
     */
    public void printStats();

    /**
     * exits application using Platform.exit()
     */
    public void exit();

}
