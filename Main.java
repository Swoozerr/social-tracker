public class Main {
    public static void main(String[] args) {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend);
        frontend.runCmdLoop(); // TestUITester gets output from command loop
    }
}