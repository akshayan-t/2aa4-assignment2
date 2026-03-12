public class CommandResult { //Stores results of commands
    private boolean success;
    private boolean endsTurn;
    private String message;

    public CommandResult() {}
    public CommandResult(boolean success, boolean endsTurn, String message) {
        this.success = success;
        this.endsTurn = endsTurn;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
    public boolean isEndsTurn() {
        return endsTurn;
    }
    public String getMessage() {
        return message;
    }
    public void printMessage() {
        System.out.print(message);
    }
}

