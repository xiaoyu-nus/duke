package duke.command;

import duke.exception.DukeException;
import duke.exception.IllegalTaskIndexException;
import duke.exception.TaskNotFoundException;
import duke.sheet.Sheet;
import duke.storage.Storage;
import duke.task.Task;
import duke.ui.Ui;

/**
 * Encapsulates a command from user input String "delete".
 */
public class CommandDelete extends Command {

    public CommandDelete(String command) {
        super(command);
        super.type = "Delete: ";
    }

    @Override
    public void execute(Sheet sh, Ui ui, Storage stor) throws DukeException {
        try {
            int index = Integer.parseInt(command);
            if (index > sh.getNumOfTask()) {
                if (sh.isEmpty()) {
                    throw new TaskNotFoundException("> < Oops! The list is empty.");
                } else {
                    throw new TaskNotFoundException("> < Oops! The list contains only "
                            + sh.getNumOfTask()
                            + (sh.getNumOfTask() == 1 ? " task." : " tasks."));
                }
            }
            if (index < 1) {
                throw new TaskNotFoundException("> < Oops! Do we have non-positive tasks?");
            }
            Task removed = sh.delete(index);
            ui.showRemove(removed.toString().trim(), sh.getNumOfTask());
            stor.save(sh.getList());
        } catch (NumberFormatException e) {
            throw new IllegalTaskIndexException(
                    "> < Oops! Nezuko cannot recognise that task index. :-(");
        }
    }

    @Override
    public String toString() {
        return "Delete: " + command;
    }
}