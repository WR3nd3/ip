/**
 * Interprets user inputted String as Commands.
 *
 * @author WR3nd3
 */
public class Parser {

    /** Commands to interact with Duke program */
    public enum Duke_Command {
        TODO, EVENT, DEADLINE, MARK, UNMARK, DELETE, LIST, BYE
    }

    /**
     * Returns valid command for Duke to execute from recognised terms in input.
     *
     * @param input String input from user interacting with Duke.
     * @return Command to be executed.
     * @throws DukeException for invalid commands inputted.
     */
    public static Command parse(String input) throws DukeException{
        // Isolate individual strings in the input
        String[] inputArray = input.split(" ", 2);
        // The command should be the first string
        String cmd = inputArray[0];
        String content = null;
        int position;

        // Store the second string from the input if any
        if (inputArray.length == 2) {
            content = inputArray[1];
        }

        try {
            Duke_Command c = Duke_Command.valueOf(cmd.toUpperCase());
            switch (c) {
            case TODO:
                if (content == null) {
                    throw new DukeException("Input 'todo ABC' to add task ABC\n");
                }
                return new AddCommand(Task_Id.T, content);
            case EVENT:
                if (content == null || content.split(" /at ").length != 2) {
                    throw new DukeException("Input 'event ABC /at DATE' to add " +
                            "event ABC on DATE\n");
                }
                // Split content into event description and date
                String[] inputE = content.split(" /at ");
                return new AddCommand(Task_Id.E, inputE[0], inputE[1]);
            case DEADLINE:
                if (content == null || content.split(" /by ").length != 2) {
                    throw new DukeException("Input 'deadline ABC /by DATE' to add " +
                            "deadline ABC due by DATE\n");
                }
                // Split content into deadline description and date
                String[] inputD = content.split(" /by ");
                return new AddCommand(Task_Id.D, inputD[0], inputD[1]);
            case MARK:
                if (content == null) {
                    throw new DukeException("Input 'mark xxx' to mark task xxx" +
                            " as complete\n");
                }
                position = Integer.parseInt(content);
                return new MarkCommand(position);
            case UNMARK:
                if (content == null) {
                    throw new DukeException("Input 'unmark xxx' to mark task xxx" +
                            " as incomplete\n");
                }
                position = Integer.parseInt(content);
                return new UnmarkCommand(position);
            case DELETE:
                if (content == null) {
                    throw new DukeException("Input 'delete xxx' to delete task xxx" +
                            " from the list\n");
                }
                position = Integer.parseInt(content);
                return new DeleteCommand(position);
            case LIST:
                return new ListCommand();
            case BYE:
                return new ExitCommand();
            default:
                throw new DukeException();
            }
        } catch (IllegalArgumentException e) {
            throw new DukeException();
        }
    }
}
