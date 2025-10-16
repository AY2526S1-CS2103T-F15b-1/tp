package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.assertCommandFailure;
//import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import insurabook.logic.Messages;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;
import insurabook.model.client.Client;
//import insurabook.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddClientCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Client validClient = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getInsuraBook(), new UserPrefs());
        expectedModel.addPerson(validClient);

        assertCommandSuccess(new AddClientCommand(validClient), model,
                String.format(AddClientCommand.MESSAGE_SUCCESS, Messages.format(validClient)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Client clientInList = model.getInsuraBook().getClientList().get(0);
        assertCommandFailure(new AddClientCommand(clientInList), model,
                AddClientCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
