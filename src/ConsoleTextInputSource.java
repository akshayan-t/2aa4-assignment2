import java.util.Scanner;

public class ConsoleTextInputSource implements TextInputSource { //Reads from command line
    public String readLine() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}
