package insurabook.logic.commands;

import static insurabook.logic.commands.DeletePolicyTypeCommand.MESSAGE_NONE_FOUND;
import static insurabook.logic.commands.DeletePolicyTypeCommand.MESSAGE_SUCCESS;
import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalPolicyTypes.PRU_ONE;
import static insurabook.testutil.TypicalPolicyTypes.PRU_TWO;
import static insurabook.testutil.TypicalPolicyTypes.getTypicalPtInsuraBook;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.testutil.PolicyTypeBuilder;

public class DeletePolicyTypeCommandTest {
    private final Model model = new ModelManager(getTypicalPtInsuraBook(), new UserPrefs());

    @Test
    public void constructor_null_throwsNullPointer() {
        PolicyTypeName validName = new PolicyTypeName("Policy A");
        PolicyTypeId validId = new PolicyTypeId("Pol001");

        assertThrows(NullPointerException.class, () -> new DeletePolicyTypeCommand(null, validId));
        assertThrows(NullPointerException.class, () -> new DeletePolicyTypeCommand(validName, null));
    }

    @Test
    public void execute_found_successfulRemove() throws CommandException {
        PolicyType toRemove = PRU_ONE;

        CommandResult result = new DeletePolicyTypeCommand(
                toRemove.getPtName(),
                toRemove.getPtId()
        ).execute(model);
        assertEquals(
                new CommandResult(String.format(MESSAGE_SUCCESS, toRemove.getPtName(), toRemove.getPtId())),
                result);
    }

    @Test
    public void execute_notFound_errorMessageProduced() throws CommandException {
        PolicyType nonExistent = new PolicyTypeBuilder(PRU_ONE)
                .withId(PRU_TWO.getPtId().toString())
                .build();

        CommandResult result = new DeletePolicyTypeCommand(
                nonExistent.getPtName(),
                nonExistent.getPtId()
        ).execute(model);
        assertEquals(
                new CommandResult(String.format(
                        MESSAGE_NONE_FOUND, nonExistent.getPtName(), nonExistent.getPtId())),
                result);
    }

}
