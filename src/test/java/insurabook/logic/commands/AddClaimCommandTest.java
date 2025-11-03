package insurabook.logic.commands;

import static insurabook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import insurabook.commons.core.GuiSettings;
import insurabook.logic.Messages;
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
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class AddClaimCommandTest {

    private final ClaimId validClaimId = new ClaimId("CL001");
    private final ClientId validClientId = new ClientId("C101");
    private final PolicyId validPolicyId = new PolicyId("P101");
    private final ClaimAmount validClaimAmount = new ClaimAmount("1000");
    private final InsuraDate validClaimDate = new InsuraDate("2024-10-10");
    private final ClaimMessage validClaimMessage = new ClaimMessage("Test claim");

    @Test
    public void constrcutor_nullParameter_throwsNullPointerException() {
        // Test null client id
        assertThrows(NullPointerException.class, () -> new AddClaimCommand(
                null, validPolicyId, validClaimAmount, validClaimDate, validClaimMessage));

        // Test null policy id
        assertThrows(NullPointerException.class, () -> new AddClaimCommand(
                validClientId, null, validClaimAmount, validClaimDate, validClaimMessage));

        // Test null claim amount
        assertThrows(NullPointerException.class, () -> new AddClaimCommand(
                validClientId, validPolicyId, null, validClaimDate, validClaimMessage));

        // Test null claim date
        assertThrows(NullPointerException.class, () -> new AddClaimCommand(
                validClientId, validPolicyId, validClaimAmount, null, validClaimMessage));

        // Test null claim message
        assertThrows(NullPointerException.class, () -> new AddClaimCommand(
                validClientId, validPolicyId, validClaimAmount, validClaimDate, null));
    }

    @Test
    public void execute_addClaimSuccessful() throws Exception {
        ModelStubAcceptingClaimAdded modelStub = new ModelStubAcceptingClaimAdded();
        AddClaimCommand addClaimCommand = new AddClaimCommand(
                validClientId, validPolicyId, validClaimAmount, validClaimDate, validClaimMessage);
        Claim toAdd = new Claim(validClaimId, validClientId, validPolicyId,
                validClaimAmount, validClaimDate, validClaimMessage);

        CommandResult commandResult = addClaimCommand.execute(modelStub);

        assertEquals(String.format(AddClaimCommand.MESSAGE_SUCCESS, Messages.format(toAdd, 0)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(toAdd), modelStub.claimsAdded);
    }

    @Test
    public void execute_clientNotFound_throwsCommandException() {
        ModelStubClientMissing modelStub = new ModelStubClientMissing();
        ClientId nonExistentClientId = new ClientId("C999");
        AddClaimCommand addClaimCommand = new AddClaimCommand(
                nonExistentClientId, validPolicyId, validClaimAmount, validClaimDate, validClaimMessage);

        assertThrows(ClientMissingException.class, "Client does not exist!", () ->
                addClaimCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddClaimCommand addClaimCommand1 = new AddClaimCommand(
                validClientId, validPolicyId, validClaimAmount, validClaimDate, validClaimMessage
        );
        AddClaimCommand addClaimCommand2 = new AddClaimCommand(
                validClientId, validPolicyId, validClaimAmount, new InsuraDate("2025-11-11"), validClaimMessage
        );

        // same object -> returns true
        assertEquals(addClaimCommand1, addClaimCommand1);

        // same values -> returns true
        AddClaimCommand addClaimCommand1Copy = new AddClaimCommand(
                validClientId, validPolicyId, validClaimAmount, validClaimDate, validClaimMessage
        );
        assertEquals(addClaimCommand1, addClaimCommand1Copy);

        // different types -> returns false
        assertNotEquals(addClaimCommand1, 5);

        // null -> returns false
        assertNotEquals(addClaimCommand1, null);

        // different claim date -> returns false
        assertNotEquals(addClaimCommand1, addClaimCommand2);
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        AddClaimCommand addClaimCommand1 = new AddClaimCommand(
                validClientId, validPolicyId, validClaimAmount, validClaimDate, validClaimMessage
        );
        AddClaimCommand addClaimCommand1Copy = new AddClaimCommand(
                validClientId, validPolicyId, validClaimAmount, validClaimDate, validClaimMessage
        );
        assertEquals(addClaimCommand1.hashCode(), addClaimCommand1Copy.hashCode());
    }

    @Test
    public void toStringMethod() {
        AddClaimCommand addClaimCommand = new AddClaimCommand(
                validClientId, validPolicyId, validClaimAmount, validClaimDate, validClaimMessage
        );

        String expectedString = "AddClaimCommand: clientId=C101, policyId=P101, claimAmount=1000, "
                + "claimDate=2024-10-10, claimMessage=Test claim";
        assertEquals(expectedString, addClaimCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getInsuraBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInsuraBookFilePath(Path insuraBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setInsuraBook(InsuraBook insuraBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyInsuraBook getInsuraBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClient(Client target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClient(Client target, Client editedClient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<PolicyType> getFilteredPolicyTypeList() {
            return null;
        }

        @Override
        public void updateFilteredPolicyTypeList(Predicate<PolicyType> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FilteredList<Policy> getClientPolicyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateClientPolicyList(Predicate<Policy> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Claim addClaim(ClientId clientId, PolicyId policyId, ClaimAmount claimAmount,
                              InsuraDate claimDate, ClaimMessage claimDescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Policy addPolicy(PolicyId policyId, ClientId clientId,
                                PolicyTypeId policyTypeId, InsuraDate expiryDate) {
            return null;
        }

        @Override
        public Policy deletePolicy(ClientId clientId, PolicyId policyId) {
            return null;
        }

        @Override
        public void setPolicy(Policy target, Policy editedPolicy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPolicyType(PolicyType toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Integer> deletePolicyType(PolicyTypeName ptName, PolicyTypeId ptId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPolicyType(PolicyType target, PolicyType editedPolicyType) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns true if given PolicyTypeName already exists in list of PolicyTypes.
         *
         * @param name
         */
        @Override
        public boolean containsPolicyTypeName(PolicyTypeName name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Claim deleteClaim(ClientId clientId, PolicyId policyId, ClaimId claimId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClaim(Claim target, Claim editedClaim) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Claim getClaim(ClientId clientId, PolicyId policyId, ClaimId claimId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoInsuraBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoInsuraBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitInsuraBook() {}

        @Override
        public List<Client> getBirthdayClients() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Policy> getExpiringPolicies() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the claim being added.
     */
    private class ModelStubAcceptingClaimAdded extends ModelStub {
        final ArrayList<Claim> claimsAdded = new ArrayList<>();

        @Override
        public Claim addClaim(ClientId clientId, PolicyId policyId, ClaimAmount claimAmount,
                             InsuraDate claimDate, ClaimMessage claimDescription) {
            Claim claim = new Claim(validClaimId, clientId, policyId, claimAmount, claimDate, claimDescription);
            claimsAdded.add(claim);
            return claim;
        }

        @Override
        public ReadOnlyInsuraBook getInsuraBook() {
            return null;
        }
    }

    /**
     * A Model stub that always throw ClientMissingException when trying to add a claim.
     */
    private class ModelStubClientMissing extends ModelStub {
        @Override
        public Claim addClaim(ClientId clientId, PolicyId policyId, ClaimAmount claimAmount,
                             InsuraDate claimDate, ClaimMessage claimDescription) {
            throw new ClientMissingException();
        }
    }
}
