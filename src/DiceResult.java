public class DiceResult { //Class for calculating dice sum
    private int die1;
    private int die2;

    public DiceResult(int die1, int die2) {
        this.die1 = die1;
        this.die2 = die2;
    }

    public int getSum() { //Returns dice sum
        return die1 + die2;
    }
}
