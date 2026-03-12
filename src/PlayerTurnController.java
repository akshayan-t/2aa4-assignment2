public abstract class PlayerTurnController { //Abstract class for Player turn controllers
    public abstract void takeTurn(Gameplay game, TurnController turnController); //Runs turns after first 2

    public abstract void takeStartTurn(Gameplay game, TurnController turnController); //Runs first 2 turns
}
