// --== CS400 Fall 2023 File Header Information ==--
// Name: Lucas Cheng
// Email: lcheng77@wisc.edu
// Group: E03
// TA: Zheyang Xiong
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.IOException;
import java.util.Scanner;

public class Frontend implements FrontendInterface{
    Backend backend;
    Scanner scanner;

    /**
     * Constructor for Frontend that takes in reference to backend and creates a scanner obj
     */
    public Frontend(Backend backend){
        this.backend = backend;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Used to run an iteration of the project, continually printing a menu of choices while project is running
     */
    @Override
    public void runCmdLoop() {
        Boolean flag = true;
        int choice = 0;

        System.out.println("--- Welcome to the Social Media Project --- \n");
        while (flag) {
            System.out.print("\nProgram Options: \n" +
                               "1.) Load a file\n" +
                               "2.) Find shortest path between 2 users\n" +
                               "3.) Display userbase stats\n" +
                               "4.) Exit the program \n\n " +
                               "--- Choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.println("File Reader: --- \n ");
                    System.out.print("Type the name of the file to be read: ");
                    try {
                        loadFile(scanner.nextLine());
                        System.out.println("File loaded successfully.");
                    }
                    catch (IOException e) {
                        System.out.println("No file found!");
                    }
                }
                case 2 -> {
                    System.out.println("Shortest Path Finder: --- \n ");
                    System.out.println("Type the name of the first user");
                    String user1 = scanner.nextLine();
                    System.out.println("Type the name of the second user");
                    String user2 = scanner.nextLine();

                    System.out.println("Here's the shortest path between those 2 users: " + "\n");
                    shortestPathResult shortestPath = backend.getClosestConnection(user1, user2);
                    System.out.println("Intermediary friends: " + shortestPath.getPath());
                    System.out.println("Length of path: " + shortestPath.getNumIntFriends());
                }
                case 3 ->{
                    System.out.println("Userbase statistics list: --- \n ");
                    printStats();
                }
                case 4 -> {
                    flag = false;
                    scanner.close();
                    exit();
                }
                default -> {
                    System.out.println("invalid input, choose an option from 1-4");
                }
            }
        }
    }

    /**
     * uses Backend's file loader method to read a dot file into a Graph ADT
     * @param file - name of file to be read
     */
    @Override
    public void loadFile(String file) throws IOException {
        backend.readData(file);
    }

    /**
     * command that asks the user for a start and destination user, then shows the closest connection between
     *     the two, including all intermediary friends
     * @param start - user Node to begin at
     * @param end - user Node to end at
     */
    @Override
    public void shortestPath(Comparable start, Comparable end) {
        // use GetClosestConnection from Backend, I think using String name of user as input ?
    }

    /**
     * displays to the user data from the social media dataset, in particular the: the number of participants
     *     (nodes), the number of edges (friendships), and the average number of friends
     */
    @Override
    public void printStats() {
        System.out.println(backend.listDataSetInfo());
    }

    /**
     * exits application
     */
    @Override
    public void exit() {
        System.out.println("Closing Program");
    }
}
