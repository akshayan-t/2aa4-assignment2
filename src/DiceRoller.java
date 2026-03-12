import java.util.Random;

public class DiceRoller {
    public DiceResult roll() {
        Random rand = new Random();
        int die1 = rand.nextInt(6 - 1 + 1) + 1; //Creates two dice from 1-6
        int die2 = rand.nextInt(6 - 1 + 1) + 1;
        return new DiceResult(die1, die2);
    }
}
