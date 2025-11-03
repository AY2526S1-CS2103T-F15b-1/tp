package insurabook.logic.commands;

import static insurabook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import insurabook.commons.core.GuiSettings;
import insurabook.logic.Messages;
import insurabook.logic.commands.exceptions.CommandException;
import insurabook.model.InsuraBook;
import insurabook.model.Model;
import insurabook.model.ReadOnlyInsuraBook;
import insurabook.model.ReadOnlyUserPrefs;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.exceptions.ClientMissingException;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.exceptions.PolicyTypeMissingException;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class DeletePolicyCommandTest {
    private final ClientId validcClientId = new ClientId("C01");
    private final PolicyId validPolicyId = new PolicyId("001");

    @Test
    public void constructor_nullClientId_throwsNullPointerException() {
        // Test null client id
        assertThrows(NullPointerException.class, () -> new DeletePolicyCommand(null, validPolicyId));

        // Test null policy id
        assertThrows(NullPointerException.class, () -> new DeletePolicyCommand(validcClientId, null));
    }
}
