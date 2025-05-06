import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserManager {

    private static final String USER_FILE = "users.txt";
    private static final String LOG_DIR = "logs";

    static {
        File dir = new File(LOG_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    public static boolean signUp(String username, String password) throws IOException {
        File file = new File(USER_FILE);
        if (!file.exists()) file.createNewFile();

        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(username + ",")) {
                    return false; // Username already exists
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            bw.write(username + "," + password);
            bw.newLine();
        }
        return true;
    }

    public static boolean login(String username, String password) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void logAction(String username, String action) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_DIR + "/" + username + "_log.txt", true))) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            bw.write("[" + timeStamp + "] " + action);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Logging error: " + e.getMessage());
        }
    }
}
