package insurabook.logic.commands;

import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalPolicyTypes.getTypicalPtInsuraBook;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import insurabook.logic.Messages;
import insurabook.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policies.exceptions.PolicyNotFoundException;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.testutil.PersonBuilder;

public class EditPolicyCommandTest {
    private final Model model = new ModelManager(getTypicalPtInsuraBook(), new UserPrefs());
    private final Client validClient = new PersonBuilder().withName("Kevin").build();
    private final ClientId validClientId = new ClientId("1");
    private final PolicyId validPolicyId = new PolicyId("001");

    @Test
    public void execute_found_editSuccessful() throws CommandException {
        model.addClient(validClient);
        model.addPolicy(validPolicyId, validClient.getClientId(),
                new PolicyTypeId("PRU001"), new InsuraDate("2025-12-31"));
        EditPolicyDescriptor editDescriptor = new EditPolicyDescriptor();
        editDescriptor.setExpiryDate(new InsuraDate("2026-12-31"));
        CommandResult commandResult = new EditPolicyCommand(validClientId, validPolicyId, editDescriptor).execute(model);
        Policy editedPolicy = new Policy(validPolicyId, validClientId,
                new PolicyTypeId("PRU001"), new InsuraDate("2026-12-31"));
        assertEquals(String.format(EditPolicyCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPolicy, 0)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_policyNotFound_throwsCommandException() {
        model.addClient(validClient);
        EditPolicyDescriptor editDescriptor = new EditPolicyDescriptor();
        editDescriptor.setExpiryDate(new InsuraDate("2026-12-31"));
        EditPolicyCommand command = new EditPolicyCommand(validClientId, validPolicyId, editDescriptor);
        assertThrows(PolicyNotFoundException.class, () -> command.execute(model));
    }

    @Test
    public void execute_clientNotFound_throwsCommandException() {
        EditPolicyDescriptor editDescriptor = new EditPolicyDescriptor();
        editDescriptor.setExpiryDate(new InsuraDate("2026-12-31"));
        EditPolicyCommand command = new EditPolicyCommand(validClientId, validPolicyId, editDescriptor);
        assertThrows(CommandException.class, () -> command.execute(model));
    }
}
