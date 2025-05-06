import java.io.*;
import java.util.*;

// Custom Data Structure 1: Metro Graph
class MetroGraph {
    public Map<String, Station> stations = new HashMap<>();

    // Custom Data Structure 2: Station
    class Station {
        String name, line;
        Map<Station, Integer> neighbors = new HashMap<>();

        Station(String name, String line) {
            this.name = name;
            this.line = line;
        }
    }

    public void addStation(String name, String line) {
        stations.put(name, new Station(name, line));
    }

    public void addConnection(String station1, String station2, int time) {
        Station s1 = stations.get(station1);
        Station s2 = stations.get(station2);
        s1.neighbors.put(s2, time);
        s2.neighbors.put(s1, time);
    }

    // Custom Data Structure 3: Route
    static class Route {
        List<Station> path = new ArrayList<>();
        int totalTime;

        Route() {}
        
        Route(Route other) {
            this.path = new ArrayList<>(other.path);
            this.totalTime = other.totalTime;
        }

        void addStation(Station station, int time) {
            path.add(station);
            totalTime += time;
        }

        void removeLast() {
            if (!path.isEmpty()) {
                totalTime -= getLast().neighbors.get(path.get(path.size()-2));
                path.remove(path.size()-1);
            }
        }

        Station getLast() {
            return path.get(path.size()-1);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) sb.append(" → ");
                sb.append(path.get(i).name).append(" (").append(path.get(i).line).append(")");
            }
            sb.append("\nTotal time: ").append(totalTime).append(" minutes");
            return sb.toString();
        }
    }

    // Custom Data Structure 4: Route Priority Queue
    class RoutePriorityQueue {
        PriorityQueue<Route> queue = new PriorityQueue<>(
            Comparator.comparingInt(r -> r.totalTime)
        );
    }

    // Recursive function to display all possible routes
    public void displayAllRoutes(Station current, Station end, Set<Station> visited, 
                               Route currentRoute, List<Route> allRoutes) {
        if (current.equals(end)) {
            allRoutes.add(new Route(currentRoute));
            return;
        }

        visited.add(current);
        for (Map.Entry<Station, Integer> entry : current.neighbors.entrySet()) {
            Station neighbor = entry.getKey();
            int time = entry.getValue();
            
            if (!visited.contains(neighbor)) {
                currentRoute.addStation(neighbor, time);
                displayAllRoutes(neighbor, end, visited, currentRoute, allRoutes);
                currentRoute.removeLast();
            }
        }
        visited.remove(current);
    }

    public Route findShortestPath(String start, String end) {
        Station startStation = stations.get(start);
        Station endStation = stations.get(end);
        
        RoutePriorityQueue pq = new RoutePriorityQueue();
        Route initialRoute = new Route();
        initialRoute.path.add(startStation);
        pq.queue.add(initialRoute);

        while (!pq.queue.isEmpty()) {
            Route current = pq.queue.poll();
            Station last = current.getLast();

            if (last.equals(endStation)) {
                return current;
            }

            for (Map.Entry<Station, Integer> entry : last.neighbors.entrySet()) {
                Station neighbor = entry.getKey();
                int time = entry.getValue();
                
                if (!current.path.contains(neighbor)) {
                    Route newRoute = new Route(current);
                    newRoute.addStation(neighbor, time);
                    pq.queue.add(newRoute);
                }
            }
        }
        return null;
    }

    public void displayFullMap() {
        System.out.println("\nMETRO MAP:");
        for (Station station : stations.values()) {
            System.out.print(station.name + " (" + station.line + ") → ");
            for (Station neighbor : station.neighbors.keySet()) {
                System.out.print(neighbor.name + " (" + station.neighbors.get(neighbor) + "min) ");
            }
            System.out.println();
        }
    }
}

public class MetroApp {
    public static void main(String[] args) {
        MetroGraph graph = new MetroGraph();
        
        // Load data from files
        try {
            BufferedReader stationReader = new BufferedReader(new FileReader("stations.txt"));
            String line;
            while ((line = stationReader.readLine()) != null) {
                String[] parts = line.split(",");
                graph.addStation(parts[0], parts[1]);
            }
            stationReader.close();

            BufferedReader connectionReader = new BufferedReader(new FileReader("connections.txt"));
            while ((line = connectionReader.readLine()) != null) {
                String[] parts = line.split(",");
                graph.addConnection(parts[0], parts[1], Integer.parseInt(parts[2]));
            }
            connectionReader.close();
        } catch (IOException e) {
            System.out.println("Error loading files: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Metro Route Finder ===");
            System.out.println("1. Display Full Metro Map");
            System.out.println("2. Find Shortest Route");
            System.out.println("3. Display All Possible Routes");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    graph.displayFullMap();
                    break;
                    
                case 2:
                    System.out.print("Enter start station: ");
                    String start = scanner.nextLine();
                    System.out.print("Enter end station: ");
                    String end = scanner.nextLine();
                    
                    MetroGraph.Route route = graph.findShortestPath(start, end);
                    if (route != null) {
                        System.out.println("\nFASTEST ROUTE:");
                        System.out.println(route);
                    } else {
                        System.out.println("No route found!");
                    }
                    break;
                    
                case 3:
                    System.out.print("Enter start station: ");
                    start = scanner.nextLine();
                    System.out.print("Enter end station: ");
                    end = scanner.nextLine();
                    
                    List<MetroGraph.Route> allRoutes = new ArrayList<>();
                    graph.displayAllRoutes(
                        graph.stations.get(start), 
                        graph.stations.get(end),
                        new HashSet<>(),
                        new MetroGraph.Route(),
                        allRoutes
                    );
                    
                    System.out.println("\nALL POSSIBLE ROUTES:");
                    for (int i = 0; i < allRoutes.size(); i++) {
                        System.out.println("\nRoute " + (i+1) + ":");
                        System.out.println(allRoutes.get(i));
                    }
                    break;
                    
                case 4:
                    System.out.println("Exiting...");
                    return;
                    
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}