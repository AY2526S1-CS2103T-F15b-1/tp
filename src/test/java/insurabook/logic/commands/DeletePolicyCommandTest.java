package insurabook.logic.commands;

import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalPolicyTypes.getTypicalPtInsuraBook;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import insurabook.logic.Messages;
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

public class DeletePolicyCommandTest {
    private final Model model = new ModelManager(getTypicalPtInsuraBook(), new UserPrefs());
    private final Client validClient = new PersonBuilder().withName("Kevin").build();
    private final ClientId validClientId = new ClientId("1");
    private final PolicyId validPolicyId = new PolicyId("001");

    @Test
    public void constructor_nullClientId_throwsNullPointerException() {
        // Test null client id
        assertThrows(NullPointerException.class, () -> new DeletePolicyCommand(null, validPolicyId));

        // Test null policy id
        assertThrows(NullPointerException.class, () -> new DeletePolicyCommand(validClientId, null));
    }

    @Test
    public void execute_found_deleteSuccessful() throws CommandException {
        model.addClient(validClient);
        model.addPolicy(validPolicyId, validClient.getClientId(),
                new PolicyTypeId("PRU001"), new InsuraDate("2025-12-31"));
        Policy toRemove = new Policy(validPolicyId, validClientId,
                new PolicyTypeId("PRU001"), new InsuraDate("2025-12-31"));
        CommandResult commandResult = new DeletePolicyCommand(validClientId, validPolicyId).execute(model);
        assertEquals(String.format(DeletePolicyCommand.MESSAGE_SUCCESS, Messages.format(toRemove, 1)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_notFound_throwsCommandException() {
        model.addClient(validClient);
        DeletePolicyCommand command = new DeletePolicyCommand(validClientId, validPolicyId);
        assertThrows(PolicyNotFoundException.class, () -> command.execute(model));
    }
}
