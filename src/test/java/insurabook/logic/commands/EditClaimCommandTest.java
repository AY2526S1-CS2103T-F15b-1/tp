package insurabook.logic.commands;

import static insurabook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static insurabook.testutil.Assert.assertThrows;
import static insurabook.testutil.TypicalClients.getTypicalInsuraBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import insurabook.logic.Messages;
import insurabook.logic.commands.EditClaimCommand.EditClaimDescriptor;
import insurabook.model.InsuraBook;
import insurabook.model.Model;
import insurabook.model.ModelManager;
import insurabook.model.UserPrefs;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.exceptions.ClientMissingException;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policies.exceptions.PolicyNotFoundException;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.testutil.ClaimBuilder;
import insurabook.testutil.EditClaimDescriptorBuilder;
import insurabook.testutil.PersonBuilder;

public class EditClaimCommandTest {

    private final Client validClient = new PersonBuilder().withName("Kevin").build();
    private final PolicyType validPolicyType = new PolicyType(
            new PolicyTypeName("Health Insurance"),
            new PolicyTypeId("PT1001"));
    private final Policy validPolicy = new Policy(
            new PolicyId("P12345"),
            validClient.getClientId(),
            validPolicyType.getPtId(),
            new InsuraDate("2025-12-31"));
    private final Claim validClaim = new ClaimBuilder().withClientId(validClient.getClientId().toString())
            .withPolicyId(validPolicy.getPolicyId().toString()).build();
    private final Model model = new ModelManager(getTypicalInsuraBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Claim editedClaim = new ClaimBuilder(validClaim).build();
        EditClaimDescriptor descriptor = new EditClaimDescriptorBuilder(editedClaim).build();
        EditClaimCommand editClaimCommand = new EditClaimCommand(
                validClient.getClientId(), validPolicy.getPolicyId(), validClaim.getClaimId(), descriptor);

        String expectedMessage = String.format(EditClaimCommand.MESSAGE_EDIT_CLAIM_SUCCESS,
                Messages.format(editedClaim, 0));
        Model expectedModel = new ModelManager(new InsuraBook(model.getInsuraBook()), new UserPrefs());
        model.addPolicyType(validPolicyType);
        model.addPolicy(validPolicy.getPolicyId(), validClient.getClientId(),
                validPolicy.getPolicyTypeId(), validPolicy.getExpiryDate());
        model.addClaim(validClaim.getClientId(), validClaim.getPolicyId(),
                validClaim.getAmount(), validClaim.getDate(), validClaim.getDescription());
        expectedModel.addPolicyType(validPolicyType);
        expectedModel.addPolicy(validPolicy.getPolicyId(), validClient.getClientId(),
                validPolicy.getPolicyTypeId(), validPolicy.getExpiryDate());
        expectedModel.setClaim(validClaim, editedClaim);
        assertCommandSuccess(editClaimCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        ClaimBuilder claimInList = new ClaimBuilder(validClaim);
        Claim editedClaim = claimInList.withClaimAmount("1500.00").build();

        EditClaimDescriptor descriptor = new EditClaimDescriptorBuilder().withAmount("1500.00").build();
        EditClaimCommand editClaimCommand = new EditClaimCommand(
                validClient.getClientId(), validPolicy.getPolicyId(), validClaim.getClaimId(), descriptor);

        String expectedMessage = String.format(EditClaimCommand.MESSAGE_EDIT_CLAIM_SUCCESS,
                Messages.format(editedClaim, 0));

        Model expectedModel = new ModelManager(new InsuraBook(model.getInsuraBook()), new UserPrefs());
        model.addPolicyType(validPolicyType);
        model.addPolicy(validPolicy.getPolicyId(), validClient.getClientId(),
                validPolicy.getPolicyTypeId(), validPolicy.getExpiryDate());
        model.addClaim(validClaim.getClientId(), validClaim.getPolicyId(),
                validClaim.getAmount(), validClaim.getDate(), validClaim.getDescription());
        expectedModel.addPolicyType(validPolicyType);
        expectedModel.addPolicy(validPolicy.getPolicyId(), validClient.getClientId(),
                validPolicy.getPolicyTypeId(), validPolicy.getExpiryDate());
        expectedModel.setClaim(validClaim, editedClaim);

        assertCommandSuccess(editClaimCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        EditClaimCommand editClaimCommand = new EditClaimCommand(
                validClient.getClientId(), validPolicy.getPolicyId(), validClaim.getClaimId(),
                new EditClaimDescriptor());

        String expectedMessage = String.format(EditClaimCommand.MESSAGE_EDIT_CLAIM_SUCCESS,
                Messages.format(validClaim, 0));

        Model expectedModel = new ModelManager(new InsuraBook(model.getInsuraBook()), new UserPrefs());
        model.addPolicyType(validPolicyType);
        model.addPolicy(validPolicy.getPolicyId(), validClient.getClientId(),
                validPolicy.getPolicyTypeId(), validPolicy.getExpiryDate());
        model.addClaim(validClaim.getClientId(), validClaim.getPolicyId(),
                validClaim.getAmount(), validClaim.getDate(), validClaim.getDescription());
        expectedModel.addPolicyType(validPolicyType);
        expectedModel.addPolicy(validPolicy.getPolicyId(), validClient.getClientId(),
                validPolicy.getPolicyTypeId(), validPolicy.getExpiryDate());
        expectedModel.setClaim(validClaim, validClaim);

        assertCommandSuccess(editClaimCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void invalidClientId_failure() {
        ClientId nonExistentClientId = new ClientId("99999");
        EditClaimDescriptor descriptor = new EditClaimDescriptorBuilder().withAmount("1500.00").build();
        EditClaimCommand editClaimCommand = new EditClaimCommand(
                nonExistentClientId, validPolicy.getPolicyId(), validClaim.getClaimId(), descriptor);

        assertThrows(ClientMissingException.class, () -> editClaimCommand.execute(model));
    }

    @Test
    public void invalidPolicyId_failure() {
        PolicyId nonExistentPolicyId = new PolicyId("P99999");
        EditClaimDescriptor descriptor = new EditClaimDescriptorBuilder().withAmount("1500.00").build();
        EditClaimCommand editClaimCommand = new EditClaimCommand(
                validClient.getClientId(), nonExistentPolicyId, validClaim.getClaimId(), descriptor);

        assertThrows(PolicyNotFoundException.class, () -> editClaimCommand.execute(model));
    }

    @Test
    public void equals() {
        final EditClaimCommand standardCommand = new EditClaimCommand(
                validClient.getClientId(), validPolicy.getPolicyId(), validClaim.getClaimId(),
                new EditClaimDescriptorBuilder().withAmount("2000.00").build());

        // same values -> returns true
        EditClaimDescriptor copyDescriptor = new EditClaimDescriptorBuilder()
                .withAmount("2000.00").build();
        EditClaimCommand commandWithSameValues = new EditClaimCommand(
                validClient.getClientId(), validPolicy.getPolicyId(), validClaim.getClaimId(), copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different clientId -> returns false
        assertNotEquals(standardCommand, new EditClaimCommand(
                new ClientId("99999"), validPolicy.getPolicyId(), validClaim.getClaimId(),
                new EditClaimDescriptorBuilder().withAmount("2000.00").build()));

        // different policyId -> returns false
        assertNotEquals(standardCommand, new EditClaimCommand(
                validClient.getClientId(), new PolicyId("P99999"), validClaim.getClaimId(),
                new EditClaimDescriptorBuilder().withAmount("2000.00").build()));

        // different claimId -> returns false
        assertNotEquals(standardCommand, new EditClaimCommand(
                validClient.getClientId(), validPolicy.getPolicyId(), new ClaimId("CL999"),
                new EditClaimDescriptorBuilder().withAmount("2000.00").build()));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditClaimCommand(
                validClient.getClientId(), validPolicy.getPolicyId(), validClaim.getClaimId(),
                new EditClaimDescriptorBuilder().withAmount("3000.00").build()));
    }

    @Test
    public void toStringMethod() {
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptor();
        ClientId clientId = validClient.getClientId();
        PolicyId policyId = validPolicy.getPolicyId();
        ClaimId claimId = validClaim.getClaimId();
        EditClaimCommand editClaimCommand = new EditClaimCommand(clientId, policyId, claimId, editClaimDescriptor);
        String expected = EditClaimCommand.class.getCanonicalName() + "{client id=" + clientId
                + ", policy id=" + policyId + ", claim id=" + claimId + ", editClaimDescriptor="
                + editClaimDescriptor + "}";
        assertEquals(expected, editClaimCommand.toString());
    }

    @Test
    public void hashCodeMethod() {
        EditClaimDescriptor editClaimDescriptor = new EditClaimDescriptorBuilder().withAmount("2000.00").build();
        ClientId clientId = validClient.getClientId();
        PolicyId policyId = validPolicy.getPolicyId();
        ClaimId claimId = validClaim.getClaimId();
        EditClaimCommand editClaimCommand = new EditClaimCommand(clientId, policyId, claimId, editClaimDescriptor);

        int expectedHashCode = java.util.Objects.hash(clientId, policyId, claimId, editClaimDescriptor);
        assertEquals(expectedHashCode, editClaimCommand.hashCode());
    }
}
