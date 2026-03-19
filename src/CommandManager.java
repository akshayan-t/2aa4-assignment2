import java.util.Stack;

public class CommandManager {
    private Stack<PlayerCommand> undoStack = new Stack<>();
    private Stack<PlayerCommand> redoStack = new Stack<>();
    private Gameplay game;
    private TurnController turnController;

    public CommandManager(Gameplay game, TurnController turnController) {
        this.game = game;
        this.turnController = turnController;
    }

    public CommandResult executeCommand(PlayerCommand command) {
        CommandResult result = command.execute(game, turnController);
        if (command instanceof ListStatusCommand || command instanceof GoCommand || result == null) {
            return result;
        }
        undoStack.push(command);
        redoStack.clear(); // Clear redo history
        return result;
    }

    public boolean undo() {
        if (!undoStack.isEmpty()) {
            PlayerCommand command = undoStack.pop();
            command.undo(game, turnController);
            redoStack.push(command);
            return true;
        }
        return false;
    }

    public boolean redo() {
        if (!redoStack.isEmpty()) {
            PlayerCommand command = redoStack.pop();
            command.execute(game, turnController);
            undoStack.push(command);
            System.out.println();
            return true;
        }
        return false;
    }
}