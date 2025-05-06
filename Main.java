import java.io.*;
import java.util.*;

public class Main {

    static class Edge {
        int to;
        int weight;

        Edge(int t, int w) {
            to = t;
            weight = w;
        }
    }

    static Map<Integer, String> stations = new HashMap<>();
    static Map<Integer, List<Edge>> graph = new HashMap<>();

    public static void main(String[] args) {
        loadStations();
        loadConnections();

        Scanner sc = new Scanner(System.in);
        String currentUser = null;

        // Login or Signup
        while (true) {
            System.out.println("\n=== Welcome to Metro Route Finder ===");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Enter new username: ");
                String user = sc.nextLine();
                System.out.print("Enter password: ");
                String pass = sc.nextLine();

                try {
                    if (UserManager.signUp(user, pass)) {
                        System.out.println("Sign-up successful. You can now log in.");
                    } else {
                        System.out.println("Username already exists. Try another.");
                    }
                } catch (IOException e) {
                    System.out.println("Error during sign-up: " + e.getMessage());
                }

            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String user = sc.nextLine();
                System.out.print("Enter password: ");
                String pass = sc.nextLine();

                try {
                    if (UserManager.login(user, pass)) {
                        currentUser = user;
                        System.out.println("Login successful. Welcome, " + user + "!");
                        UserManager.logAction(currentUser, "Logged in.");
                        break;
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                } catch (IOException e) {
                    System.out.println("Error during login: " + e.getMessage());
                }

            } else if (choice == 3) {
                System.out.println("Exiting...");
                return;
            } else {
                System.out.println("Invalid choice!");
            }
        }

        // Main Menu after login
        while (true) {
            System.out.println("\n=== FASTEST METRO ROUTE FINDER ===");
            System.out.println("1. View All Stations");
            System.out.println("2. Find Shortest Route");
            System.out.println("3. Display Metro Map");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    displayStations();
                    UserManager.logAction(currentUser, "Viewed all stations.");
                    break;
                case 2:
                    System.out.print("Enter Source Station ID: ");
                    int src = sc.nextInt();
                    System.out.print("Enter Destination Station ID: ");
                    int dest = sc.nextInt();
                    UserManager.logAction(currentUser, "Finding shortest path from " + src + " to " + dest);
                    dijkstra(src, dest, currentUser);
                    break;
                case 3:
                    displayMap();
                    UserManager.logAction(currentUser, "Viewed Metro Map.");
                    break;
                case 4:
                    UserManager.logAction(currentUser, "Logged out and exited.");
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    static void loadStations() {
        try (BufferedReader br = new BufferedReader(new FileReader("stations.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                stations.put(id, name);
                graph.put(id, new ArrayList<>());
            }
        } catch (IOException e) {
            System.out.println("Error loading stations.txt: " + e.getMessage());
        }
    }

    static void loadConnections() {
        try (BufferedReader br = new BufferedReader(new FileReader("connections.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int from = Integer.parseInt(parts[0].trim());
                int to = Integer.parseInt(parts[1].trim());
                int dist = Integer.parseInt(parts[2].trim());

                graph.get(from).add(new Edge(to, dist));
                graph.get(to).add(new Edge(from, dist)); // undirected
            }
        } catch (IOException e) {
            System.out.println("Error loading connections.txt: " + e.getMessage());
        }
    }

    static void displayStations() {
        System.out.println("\n--- Available Metro Stations ---");
        for (Map.Entry<Integer, String> entry : stations.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }

    static void displayMap() {
        System.out.println("\n--- METRO MAP ---");
        File file = new File("metro_map.txt");

        if (!file.exists()) {
            System.out.println("Metro map file not found. Creating it...");
            MetroMapManager.createMetroMapFile();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error displaying map: " + e.getMessage());
        }
    }

    static void dijkstra(int source, int destination, String username) {
        if (!stations.containsKey(source) || !stations.containsKey(destination)) {
            System.out.println("Invalid Station ID(s)!");
            UserManager.logAction(username, "Failed path search: Invalid station ID(s)");
            return;
        }

        int n = stations.size();
        int[] dist = new int[n + 1];
        int[] prev = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        dist[source] = 0;
        pq.add(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int d = curr[1];

            if (d > dist[u]) continue;

            for (Edge e : graph.get(u)) {
                if (dist[e.to] > dist[u] + e.weight) {
                    dist[e.to] = dist[u] + e.weight;
                    prev[e.to] = u;
                    pq.add(new int[]{e.to, dist[e.to]});
                }
            }
        }

        if (dist[destination] == Integer.MAX_VALUE) {
            System.out.println("No path found between " + stations.get(source) + " and " + stations.get(destination));
            UserManager.logAction(username, "No path found from " + source + " to " + destination);
            return;
        }

        List<Integer> path = new ArrayList<>();
        for (int at = destination; at != -1; at = prev[at]) {
            path.add(at);
        }
        Collections.reverse(path);

        System.out.println("\nShortest path from " + stations.get(source) + " to " + stations.get(destination) + ":");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(stations.get(path.get(i)));
            if (i != path.size() - 1) System.out.print(" -> ");
        }
        System.out.println("\nTotal Distance: " + dist[destination] + " km");

        UserManager.logAction(username, "Displayed shortest route: " + stations.get(source) + " to " + stations.get(destination) +
                ", Distance: " + dist[destination] + " km");
    }
}
