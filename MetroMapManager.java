import java.io.*;

public class MetroMapManager {

    public static void createMetroMapFile() {
        String[] mapLines = {
                "         Kashmere Gate (2)",
                "               |",
                "         Rajiv Chowk (1)",
                "              / \\",
                "             /   \\",
                "Central Secretariat (3)",
                "             |",
                "       Hauz Khas (4)",
                "         /      \\",
                "Huda City Centre (5)   Botanical Garden (6)",
                "                          |",
                "                    Shivaji Park (7)",
                "                          |",
                "                    Akshardham (8)",
                "                          |",
                "                     New Delhi (9)",
                "                          |",
                "               Dwarka Sector 21 (10)"
        };

        try {
            File file = new File("metro_map.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            for (String line : mapLines) {
                pw.println(line);
            }

            pw.close();
            System.out.println("metro_map.txt created successfully.");
        } catch (IOException e) {
            System.out.println("Error writing metro map: " + e.getMessage());
        }
    }

    public static void displayMetroMap() {
        try {
            File file = new File("metro_map.txt");
            if (!file.exists()) {
                System.out.println("Metro map file does not exist. Creating new one...");
                createMetroMapFile();
            }

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            System.out.println("\n\n************** DELHI METRO MAP **************\n");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("\n**********************************************\n");

            br.close();
        } catch (IOException e) {
            System.out.println("Error reading metro map: " + e.getMessage());
        }
    }
}
