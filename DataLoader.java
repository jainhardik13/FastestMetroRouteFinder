import java.io.*;

public class DataLoader {
    public static Station[] loadStations(String filename) {
        Station[] stations = new Station[11];

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                stations[id] = new Station(id,name);
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error reading stations: " + e.getMessage());
        }

        return stations;
    }

    public static MyGraph loadGraph(String filename, int totalStations) {
        MyGraph graph = new MyGraph(totalStations);

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                int weight = Integer.parseInt(parts[2]);

                graph.addEdge(from,to,weight);
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error reading connections: " + e.getMessage());
        }

        return graph;
    }
}
