package insurabook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import insurabook.model.Model;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.ui.enums.View;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class ViewCommandTest {

    private final Model model = mock(Model.class);

    @Test
    void execute_clientViewFlag_setsClientView() {
        ViewCommand command = new ViewCommand("-client", null);
        CommandResult result = command.execute(model);

        assertEquals(ViewCommand.MESSAGE_SUCCESS_CLIENT, result.getFeedbackToUser());
        assertEquals(View.CLIENT_VIEW, result.getView());
    }

    @Test
    void execute_policyViewFlag_setsPolicyView() {
        ViewCommand command = new ViewCommand("-policy", null);
        CommandResult result = command.execute(model);

        assertEquals(ViewCommand.MESSAGE_SUCCESS_POLICY, result.getFeedbackToUser());
        assertEquals(View.POLICY_TYPE_VIEW, result.getView());
    }

    @Test
    void execute_clientPolicyViewFlag_setsClientPolicyView() {
        Client mockClient = mock(Client.class);
        when(mockClient.getClientId()).thenReturn(new ClientId("1"));
        ObservableList<Client> personList = FXCollections.observableArrayList(mockClient);
        when(model.getFilteredPersonList()).thenReturn(personList);

        ViewCommand command = new ViewCommand("-c_id", new ClientId("1"));
        CommandResult result = command.execute(model);

        assertEquals(ViewCommand.MESSAGE_SUCCESS_CLIENT_POLICIES, result.getFeedbackToUser());
    }

    @Test
    void execute_clientPolicyViewFlag_returnsErrorMessage() {
        ViewCommand command = new ViewCommand("-c_id", null);
        CommandResult result = command.execute(model);

        assertEquals(ViewCommand.MESSAGE_NO_CLIENT_ID_PROVIDED, result.getFeedbackToUser());
    }

    @Test
    void execute_clientPolicyViewFlagWithNoSuchClient_returnsErrorMessage() {
        Client mockClient1 = mock(Client.class);
        when(mockClient1.getClientId()).thenReturn(new ClientId("1"));
        Client mockClient2 = mock(Client.class);
        when(mockClient2.getClientId()).thenReturn(new ClientId("2"));
        ObservableList<Client> mockClientList = FXCollections.observableArrayList(
                mockClient1,
                mockClient2
        );
        when(model.getFilteredPersonList()).thenReturn(mockClientList);

        ViewCommand command = new ViewCommand("-c_id", new ClientId("nonexistent"));
        CommandResult result = command.execute(model);

        assertEquals(ViewCommand.MESSAGE_CLIENT_NOT_FOUND.formatted("nonexistent"), result.getFeedbackToUser());
    }


    @Test
    void execute_invalidFlag_returnsErrorMessage() {
        ViewCommand command = new ViewCommand("-x", null);
        CommandResult result = command.execute(model);

        assertEquals("Invalid flag! Use -c for Client View and -p for Policy View", result.getFeedbackToUser());
        assertEquals(result.getView(), View.CLIENT_VIEW); // defaults to client view
    }
}
