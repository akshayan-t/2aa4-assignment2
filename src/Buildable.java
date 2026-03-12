import java.util.HashMap;

public class Buildable { //Class for Building and Road to extend
    protected Player owner; //Owner
    protected HashMap<Resource, Integer> cost = new HashMap<>(); //Cost as hashmap

    public Player getOwner() { //Gets owner
        return owner;
    } //Getter
    public HashMap<Resource, Integer> getCost() {
        return cost;
    } //Gets required resources
}
