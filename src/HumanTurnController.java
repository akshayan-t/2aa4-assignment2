import java.util.Scanner;

public class HumanTurnController extends PlayerTurnController {
    private ConsoleTextInputSource input = new ConsoleTextInputSource();
    private HumanCommandParser parser = new HumanCommandParser();

    public void takeTurn(Gameplay game, TurnController turnController) { //Runs turns after first 2 turns
        CommandManager commandManager = new CommandManager(game, turnController);
        Player player = game.getCurrentPlayer();
        System.out.println("Enter command: ");
        while (true) {
            if (game.isGameOver(player)) {
                return;
            }
            String line = input.readLine();
            PlayerCommand command = parser.parse(line); //Gets command
            if (command != null) {
                if (game.getTurnPhase().equals(TurnPhase.NOT_ROLLED)) { //If player hasn't rolled yet
                    if (command instanceof RollDiceCommand) { //Roll
                        commandManager.executeCommand(command);
                        game.setTurnPhase(TurnPhase.ROLLED); //Sets phase to rolled
                    } else if (command instanceof ListStatusCommand){ //List
                        commandManager.executeCommand(command);
                    } else {
                        System.out.print("Roll before continuing");
                    }
                } else {
                    if (command instanceof RollDiceCommand) { //Roll
                        System.out.print("Already rolled");
                    } else if (command instanceof GoCommand) { //Go
                        break;
                    } else {
                        commandManager.executeCommand(command); //Execute everything else
                    }
                }
                System.out.println();
            }
            else if (line.equals("Undo")) {
                if (commandManager.undo() == true) {
                    System.out.println("Command undone");
                }
                else {
                    System.out.println("No commands to undo");
                }
            }
            else if (line.equals("Redo")) {
                if (commandManager.redo() == false) {
                    System.out.println("No commands to redo");
                }
            }
            else { //Usage String for incorrect input
                System.out.println("Usage string: Roll|Go|List|[Build [settlement [nodeId] | city [nodeId] | road [fromNodeId, toNodeId]]]");
            }
        }
    }

    public void takeStartTurn(Gameplay game, TurnController turnController) { //Runs first 2 turns
        CommandManager commandManager = new CommandManager(game, turnController);
        int turn = game.getTurn();
        Player player = game.getCurrentPlayer();
        Board board =  game.getBoard();
        String line = null;
        int node = 0;
        boolean builtSettlement = false;
        boolean builtRoad = false;
        System.out.println("Enter command: ");
        while (true) {
            line = input.readLine();
            PlayerCommand command = parser.parse(line); //Gets commands
            if (command != null) {
                String lines[] = line.split(" ");
                if (command instanceof GoCommand) { //Go
                    if (builtSettlement == true && builtRoad == true) { //If player built settlement and road
                        break;
                    }
                    else {
                        System.out.print("Must finish building before proceeding");
                    }
                }
                else if (command instanceof RollDiceCommand) { //Roll
                    System.out.print("Can't roll during the first 2 turns");
                }
                else if (command instanceof ListStatusCommand){ //List
                    commandManager.executeCommand(command);;
                }
                else if (command instanceof BuildSettlementCommand) { //Build settlement yet
                    if (builtSettlement == false) { //If haven't build settlement
                        if (commandManager.executeCommand(command) != null) { //Builds settlement
                            node = Integer.valueOf(lines[2]); //Gets node number
                            builtSettlement = true; //Sets settlement to true
                        }
                    }
                    else System.out.print("Build unsuccessful");
                }
                else if (command instanceof BuildRoadCommand) { //If build r
                    if (builtSettlement == true && builtRoad == false) {
                        int start = Integer.valueOf(lines[2].replace(",", ""));
                        int end = Integer.valueOf(lines[3]);
                        if (node == start || node == end) {
                            if (commandManager.executeCommand(command) != null) {
                                builtRoad = true;
                            }
                        }
                        else System.out.print("Build unsuccessful");
                    }
                    else System.out.print("Build unsuccessful");
                }
                else {
                    commandManager.executeCommand(command); //Executes everything else
                }
            }
            else if (line.equals("Undo")) {
                if (commandManager.undo() == true) {
                    System.out.print("Command undone");
                    if (builtRoad == true) {
                        builtRoad = false;
                    }
                    else if (builtSettlement == true) {
                        builtSettlement = false;
                    }
                }
                else {
                    System.out.print("No commands to undo");
                }
            }
            else if (line.equals("Redo")) {
                if (commandManager.redo() == true) {
                    if (builtSettlement == false) {
                        builtSettlement = true;
                    }
                    else if (builtRoad == false) {
                        builtRoad = true;
                    }
                }
                else {
                    System.out.print("No commands to redo");
                }
            }
            else {
                System.out.print("Usage string: Undo|Redo|Roll|Go|List|[Build [settlement [nodeId] | city [nodeId] | road [fromNodeId, toNodeId]]]");
            }
            System.out.println();
        }
    }
}