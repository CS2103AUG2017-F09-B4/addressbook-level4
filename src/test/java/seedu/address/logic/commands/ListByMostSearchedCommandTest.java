package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/***
 * Class of tests for ListByMostSearchedCommandTest
 * @author Sri-vatsa
 */
public class ListByMostSearchedCommandTest {
    private Model model;
    private Model expectedModel;
    private ListByMostSearchedCommand lmsCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        lmsCommand = new ListByMostSearchedCommand();
        lmsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(lmsCommand, model, ListByMostSearchedCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(lmsCommand, model, ListByMostSearchedCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listInDescendingSearchCount_verification() {
        lmsCommand.execute();

        //In a list sorted in descending order of search count, SearchCountA refers to the search count A of the
        //person higher up on the list with a supposed search Count greater or equals to the search count of person
        //B who is lower in the list, with a lower search count
        int searchCountA, searchCountB;
        for(int i = 0; i < model.getFilteredPersonList().size(); i++) {
            for(int j = i+1; j < model.getFilteredPersonList().size(); j++) {
                searchCountA = Integer.parseInt(model.getFilteredPersonList().get(j).getSearchData().getSearchCount());
                searchCountB = Integer.parseInt(model.getFilteredPersonList().get(i).getSearchData().getSearchCount());
                assertTrue(searchCountA <= searchCountB);
            }
        }
    }

}