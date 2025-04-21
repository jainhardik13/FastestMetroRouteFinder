import java.io.*;

public class FileIntializer {
    public static void createFiles(){
        writeStations();
        writeConnections();
    }

    private static void writeStations(){
        String[] stations = {
                "1,Rajiv Chowk",
                "2,Kashmere Gate",
                "3,Central Secretariat",
                "4,Hauz Khas",
                "5,Huda City Centre",
                "6,Botanical Garden",
                "7,Shivaji Park",
                "8,Akshardham",
                "9,New Delhi",
                "10,Dwarka Sector 21"
        };

        try {
            File file = new File("stations.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            for (String line : stations) {
                pw.println(line);
            }

            pw.close();
            System.out.println("Stations.txt created successfully.");
        }
        catch (IOException e) {
            System.out.println("Error writing stations: " + e.getMessage());
        }
    }

    private static void writeConnections() {
        String[] connections = {
                "1,2,5",
                "1,3,10",
                "2,4,7",
                "3,4,8",
                "4,5,10",
                "5,6,6",
                "6,7,4",
                "7,8,5",
                "8,9,9",
                "9,10,12"
        };

        try {
            File file = new File("connections.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            for (String line : connections) {
                pw.println(line);
            }

            pw.close();
            System.out.println("connections.txt created successfully.");
        }
        catch (IOException e) {
            System.out.println("Error writing connections: " + e.getMessage());
        }
    }
}
