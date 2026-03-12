import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HumanCommandParser { //Class for parsing command line
    /** Regex for command line **/
    private String regex = "^(?<command>Roll|Go|List|Build (?<building>settlement|city|road (?<fromNodeId>(\\d+,))?)? (?<nodeId>\\d+))?$";
    private Pattern pattern = Pattern.compile(regex);

    public PlayerCommand parse(String command) { //Parses string
        Matcher matcher = pattern.matcher(command);
        if (matcher.matches()) { //If string matches regex
            command = command.replace(",", ""); //Tidies string
            String line[] = command.split(" "); //Splits string
            if (line[0].equals("Roll")) { //If first word is Roll
                return new RollDiceCommand(); //Returns RollDice command
            } else if (line[0].equals("Go")) { //Go
                return new GoCommand();
            } else if (line[0].equals("List")) { //List
                return new ListStatusCommand();
            } else if (line[0].equals("Build")) { //Build
                int nodeId = Integer.valueOf(line[2].replace(",", ""));

                if (line[1].equals("settlement")) { //Build settlement
                    return new BuildSettlementCommand(nodeId);
                } else if (line[1].equals("city")) { //Build city
                    return new BuildCityCommand(nodeId);
                } else if (line[1].equals("road")) { //Build road
                    int toNodeId = Integer.valueOf(line[3]);
                    return new BuildRoadCommand(nodeId, toNodeId);
                }
            }
            System.out.println();
        }
        return null; //Else returns null
    }
}
