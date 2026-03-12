import java.util.Scanner;

public class HumanTurnController extends PlayerTurnController {
    private ConsoleTextInputSource input = new ConsoleTextInputSource();
    private HumanCommandParser parser = new HumanCommandParser();

    public void takeTurn(Gameplay game, TurnController turnController) { //Runs turns after first 2 turns
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
                        command.execute(game, turnController);
                        game.setTurnPhase(TurnPhase.ROLLED); //Sets phase to rolled
                    } else if (command instanceof ListStatusCommand){ //List
                        command.execute(game, turnController);
                    } else {
                        System.out.print("Roll before continuing");
                    }
                } else {
                    if (command instanceof RollDiceCommand) { //Roll
                        System.out.print("Already rolled");
                    } else if (command instanceof GoCommand) { //Go
                        break;
                    } else {
                        command.execute(game, turnController); //Execute everything else
                    }
                }
                System.out.println();
            }
            else { //Usage String for incorrect input
                System.out.println("Usage string: Roll|Go|List|[Build [settlement [nodeId] | city [nodeId] | road [fromNodeId, toNodeId]]]");
            }
        }
    }

    public void takeStartTurn(Gameplay game, TurnController turnController) { //Runs first 2 turns
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
                    command.execute(game, turnController);
                }
                else if (command instanceof BuildSettlementCommand) { //Build settlement yet
                    if (builtSettlement == false) { //If haven't build settlement
                        if (command.execute(game, turnController) != null) { //Builds settlement
                            node = Integer.valueOf(lines[2]); //Gets node number
                            builtSettlement = true; //Sets settlement to true
                            if (turn == 2) { //If second turn
                                for (Tile tile : board.getTiles()) { //Generates resources for settlement
                                    if (tile.getNodes().contains(board.getNodes()[node]) && tile.getResource() != null) {
                                        player.updateResources(tile.getResource(), 1, board);
                                    }
                                }
                            }
                        }
                    }
                    else System.out.print("Build unsuccessful");
                }
                else if (command instanceof BuildRoadCommand) { //If build r
                    if (builtSettlement == true && builtRoad == false) {
                        int start = Integer.valueOf(lines[2].replace(",", ""));
                        int end = Integer.valueOf(lines[3]);
                        if (node == start || node == end) {
                            if (command.execute(game, turnController) != null) {
                                builtRoad = true;
                            }
                        }
                        else System.out.print("Build unsuccessful");
                    }
                    else System.out.print("Build unsuccessful");
                }
                else {
                    command.execute(game, turnController); //Executes everything else
                }
            }
            else {
                System.out.print("Usage string: Roll|Go|List|[Build [settlement [nodeId] | city [nodeId] | road [fromNodeId, toNodeId]]]");
            }
            System.out.println();
        }
    }
}