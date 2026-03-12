import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RobberController {
    Random rand = new Random();

    public void activateRobber(Player turnPlayer, List<Player> players, Board board) { //Activates robbers
        discardResources(turnPlayer, players, board); //Discards half of other players' resources if they have over 7
        Player victim = pickVictim(turnPlayer, board); //Moves robber and picks adjacent victim
        if (victim != null) {
            robPlayer(turnPlayer, victim); //Robs victim
        }
    }

    private void discardResources(Player turnPlayer, List<Player> players, Board board) { //Discards half of other players' resources if they have over 7
        Resource resource = null;
        for (Player p : players) {
            if (p != turnPlayer) {
                if (p.getTotalResources() > 7) { //If player has over 7 resources
                    int newTotal = p.getTotalResources()/2; //New total is half
                    while (p.getTotalResources() > newTotal) { //Removes resources until they reach half
                        resource = chooseResource(p);
                        p.updateResources(resource, -1);
                        board.updateResources(resource, 1);
                    }
                }
            }
        }
    }

    private Player pickVictim(Player turnPlayer, Board board) { //Picks victim for robbing
        Tile tile = pickRandomTile(board); //Moves robber to random tile
        List<Player> owners = new ArrayList<>(tile.getOwners()); //Gets players with buildings connected to tile
        owners.remove(turnPlayer); //Removes turn player and nulls
        owners.remove(null);
        Iterator<Player> iterator = owners.iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getTotalResources() <= 0) { //If player doesn't have any resources, removes
                iterator.remove();
            }
        }
        if (owners.size() > 0) { //If victim exists
            Player robbedPlayer = owners.get(rand.nextInt(owners.size())); //Chooses victim
            if (robbedPlayer != null) {
                return robbedPlayer;
            }
        }
        return null;
    }

    private void robPlayer(Player turnPlayer, Player robbedPlayer) { //Steals one random resource from player
        Resource resource = null;
        int currentResources = robbedPlayer.getTotalResources();
        while (robbedPlayer.getTotalResources() == currentResources) {
            resource = chooseResource(robbedPlayer); //Robs 1 random resource from victim
            System.out.print(" Player " + robbedPlayer.getPlayerNumber() + " has been robbed! (-1 " + resource + ")");
            robbedPlayer.updateResources(resource, -1);
            turnPlayer.updateResources(resource, 1);
        }
    }

    private Resource chooseResource(Player player) { //Choose valid resource that can be robbed
        Resource[] resources = Resource.values();
        Resource resource = null;
        while (true) {
            resource = resources[rand.nextInt(resources.length)];
            if (player.getResources(resource) > 0) {
                break;
            }
        }
        return resource; //Returns robbable resource
    }

    private Tile pickRandomTile(Board board) { //Picks random tile
        Tile[] tiles = board.getTiles();
        Tile tile = tiles[rand.nextInt(tiles.length)];
        return tile;
    }
}
