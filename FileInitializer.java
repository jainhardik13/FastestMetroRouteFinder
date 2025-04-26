import java.io.*;

public class FileInitializer {
    public static void createFiles() {
        writeStations();
        writeConnections();
    }

    private static void writeStations() {
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
            System.out.println("stations.txt created successfully.");
        } catch (IOException e) {
            System.out.println("Error writing stations: " + e.getMessage());
        }
    }

    private static void writeConnections() {
        String[] connections = {
                // Line 1 (Rajiv Chowk -> Kashmere Gate)
                "1,2,5",

                // Line 2 (Rajiv Chowk -> Central Secretariat)
                "1,3,3",

                // Line 3 (Central Secretariat -> Hauz Khas)
                "3,4,7",

                // Line 4 (Hauz Khas -> Huda City Centre)
                "4,5,8",

                // Line 5 (Hauz Khas -> Botanical Garden)
                "4,6,5",

                // Line 6 (Botanical Garden -> Shivaji Park)
                "6,7,4",

                // Line 7 (Shivaji Park -> Akshardham)
                "7,8,6",

                // Line 8 (Akshardham -> New Delhi)
                "8,9,10",

                // Line 9 (New Delhi -> Dwarka Sector 21)
                "9,10,14",

                // Line 10 (Kashmere Gate -> New Delhi)
                "2,9,4"
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
        } catch (IOException e) {
            System.out.println("Error writing connections: " + e.getMessage());
        }
    }
}
