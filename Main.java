import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Step 1: Create files
        FileInitializer.createFiles();

        // Step 2: Load station data
        Station[] stations = DataLoader.loadStations("stations.txt");

        // Step 3: Load graph data
        MyGraph graph = DataLoader.loadGraph("connections.txt", 10);

        // Step 4: Show the loaded data
        System.out.println("\nList of Metro Stations:");
        for (int i = 1; i <= 10; i++) {
            System.out.println(i + ": " + stations[i].name);
        }

        System.out.println("\nEnter Source Station ID:");
        Scanner sc = new Scanner(System.in);
        int source = sc.nextInt();

        System.out.println("Enter Destination Station ID:");
        int destination = sc.nextInt();

        // Step 5: Find shortest path
        Dijkstra.findShortestPath(graph, stations, source, destination);
    }
}
