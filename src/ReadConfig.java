import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadConfig { //Reads config file
    public static int readTurns() throws IOException {
        String filePath = "src/Config"; //Edit this to change file path if it gives a null parse error
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.toLowerCase().startsWith("turns: int")) { //If it matches format
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3) {
                        return Integer.parseInt(parts[2]);
                    } else {
                        throw new IllegalArgumentException("Invalid format for turns line.");
                    }
                }
            }
        }
        throw new IllegalArgumentException("No 'turns: int' line found in file.");
    }
}

