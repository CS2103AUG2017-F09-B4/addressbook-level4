package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Meeting;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeeting;
import seedu.address.model.ReadOnlyMeetingList;
import seedu.address.model.UniqueMeetingList;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.AsanaAuthenticationException;
import seedu.address.model.exceptions.DuplicateMeetingException;
import seedu.address.model.exceptions.IllegalIdException;
import seedu.address.model.person.InternalId;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author Sri-vatsa
public class AddMeetingCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullMeeting_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddMeetingCommand(null);
    }

    //successful meeting added without Asana configuration
    @Test
    public void execute_addMeeting_success() throws Exception {
        ModelStubAcceptingMeetingAdded modelStub = new ModelStubAcceptingMeetingAdded();

        ArrayList<Index> ids = new ArrayList<>();
        ids.add(Index.fromOneBased(1));
        LocalDateTime localDateTime = LocalDateTime.of(2020, 10, 31, 18, 00);

        CommandResult commandResult = getAddMeetingCommand(localDateTime, "Computing", "Project meeting",
                ids, modelStub).execute();
        assertEquals(AddMeetingCommand.MESSAGE_SUCCESS_ASANA_NO_CONFIG, commandResult.feedbackToUser);
    }

    //Duplicate meeting
    @Test
    public void execute_duplicateMeeting_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateMeetingException();

        ArrayList<Index> ids = new ArrayList<>();
        ids.add(Index.fromOneBased(1));
        LocalDateTime localDateTime = LocalDateTime.of(2020, 10, 31, 18, 00);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMeetingCommand.MESSAGE_DUPLICATE_MEETING);

        getAddMeetingCommand(localDateTime, "NUS Computing", "CS2103", ids, modelStub).execute();
    }

    /**
     * Generates a new AddMeetingCommand with the details of the given person.
     */
    private AddMeetingCommand getAddMeetingCommand (LocalDateTime dateTime, String location, String notes,
                                                    ArrayList<Index> ids, Model model) {
        AddMeetingCommand command = new AddMeetingCommand(dateTime, location, notes, ids);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData, ReadOnlyMeetingList newMeetingData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyMeetingList getMeetingList() {
            fail("This method should not be called.");
            return null;
        }

        //@@author liuhang0213
        @Override
        /**
         * Returns an internal id identical to visible id
         */
        public InternalId visibleToInternalId(Index visibleId) throws IllegalValueException {
            return new InternalId(visibleId.getOneBased());
        }

        //@@author
        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public boolean deleteTag(Tag[] tags) throws PersonNotFoundException, DuplicatePersonException {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException, IllegalIdException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList() {
            updateFilteredPersonList();
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public UserPrefs getUserPrefs() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void recordSearchHistory() throws CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersonListBySearchCount() {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersonListLexicographically() {
            fail("This method should not be called.");
        }

        @Override
        public void authenticateAsanaUser() throws IOException, URISyntaxException {
            Assert.fail("This method should not be called.");
        }

        @Override
        public void checkAuthenticateAsanaUser() throws AsanaAuthenticationException {
            Assert.fail("This method should not be called.");
        }

        @Override
        public void storeAccessToken(String accessToken) throws IOException {
            Assert.fail("This method should not be called.");
        }

        @Override
        public void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            Assert.fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateMeetingException when trying to add a meeting.
     */
    private class ModelStubThrowingDuplicateMeetingException extends ModelStub {
        @Override
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException {
            throw new DuplicateMeetingException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyMeetingList getMeetingList() {
            return new UniqueMeetingList();
        }
    }

    /**
     * A Model stub that always accept the meeting being added.
     */
    private class ModelStubAcceptingMeetingAdded extends ModelStub {
        final ArrayList<ReadOnlyMeeting> meetingsAdded = new ArrayList<>();

        @Override
        public void addMeeting(ReadOnlyMeeting meeting) {
            meetingsAdded.add(new Meeting(meeting));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyMeetingList getMeetingList() {
            return getTypicalMeetingList();
        }
    }

}
