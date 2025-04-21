public class Main {
    public static void main(String[] args) {
        FileIntializer.createFiles();

        Station[] stations = DataLoader.loadStations("stations.txt");

        MyGraph graph = DataLoader.loadGraph("connections.txt",10);


        System.out.println("\nList of Metro Stations:");
        for (int i = 1 ; i <= 10 ; i++) {
            System.out.println(i + ": " + stations[i].name);
        }

        System.out.println("\nMetro Connections:");
        for (int i = 1 ; i <= 10 ; i++) {
            MyNode current = graph.getAdjacencyList(i).getHead();
            while (current != null) {
                Edge edge = (Edge) current.data;
                System.out.println("From " + stations[i].name + " to " +
                        stations[edge.destination].name + " = " + edge.weight + " km");
                current = current.next;
            }
        }
    }
}
