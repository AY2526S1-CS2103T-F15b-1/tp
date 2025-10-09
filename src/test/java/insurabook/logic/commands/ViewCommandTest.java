package insurabook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import insurabook.model.Model;

class ViewCommandTest {

    private final Model model = mock(Model.class);

    @Test
    void execute_clientFlag_setsClientView() {
        ViewCommand command = new ViewCommand("-c");
        CommandResult result = command.execute(model);

        assertEquals(ViewCommand.MESSAGE_SUCCESS_CLIENT, result.getFeedbackToUser());
        assertEquals(ViewCommand.CLIENT_VIEW, result.getView());
    }

    @Test
    void execute_policyFlag_setsPolicyView() {
        ViewCommand command = new ViewCommand("-p");
        CommandResult result = command.execute(model);

        assertEquals(ViewCommand.MESSAGE_SUCCESS_POLICY, result.getFeedbackToUser());
        assertEquals(ViewCommand.POLICY_VIEW, result.getView());
    }

    @Test
    void execute_invalidFlag_returnsErrorMessage() {
        ViewCommand command = new ViewCommand("-x");
        CommandResult result = command.execute(model);

        assertEquals("Invalid flag! Use -c for Client View and -p for Policy View", result.getFeedbackToUser());
        assertEquals(result.getView(), "client"); // defaults to client view
    }
}
