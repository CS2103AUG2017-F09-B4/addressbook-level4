package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToBrowserListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

public class ChooseCommand extends Command {

    public static final String COMMAND_WORD = "choose";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : selects the type of display in the main browser window.\n"
            + "Parameters: <TYPE>\n"
            + "Example: choose linkedin";


    public static final String MESSAGE_SUCCESS = "Selected type ";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " TYPE";

    private final String targetDisplay;

    public ChooseCommand(String targetDisplay) {
        this.targetDisplay = targetDisplay;
    }

    @Override
    public CommandResult execute() throws CommandException {
        /** Old code
        EventsCenter.getInstance().post(new BrowserPanelSelectionChangedEvent(targetDisplay));
         */
        EventsCenter.getInstance().post(new JumpToBrowserListRequestEvent(targetDisplay));
        return new CommandResult(MESSAGE_SUCCESS + targetDisplay);
    }
}
