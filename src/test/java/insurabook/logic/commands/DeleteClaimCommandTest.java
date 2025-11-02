package insurabook.logic.commands;

import static insurabook.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;
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
import insurabook.model.claims.exceptions.ClaimNotFoundException;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.testutil.ClaimBuilder;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class DeleteClaimCommandTest {

    private final ClaimId validClaimId = new ClaimId("CL001");
    private final ClientId validClientId = new ClientId("C101");
    private final PolicyId validPolicyId = new PolicyId("P101");
    private final ClaimAmount validClaimAmount = new ClaimAmount("1000");
    private final InsuraDate validClaimDate = new InsuraDate("2024-10-10");
    private final ClaimMessage validClaimMessage = new ClaimMessage("Test claim");

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        // Test null clientId
        assertThrows(NullPointerException.class, () -> new DeleteClaimCommand(
                null, validPolicyId, validClaimId, validClaimMessage));

        // Test null policyId
        assertThrows(NullPointerException.class, () -> new DeleteClaimCommand(
                validClientId, null, validClaimId, validClaimMessage));

        // Test null claimId
        assertThrows(NullPointerException.class, () -> new DeleteClaimCommand(
                validClientId, validPolicyId, null, validClaimMessage));
    }

    @Test
    public void execute_deleteClaimCommand_success() throws Exception {
        Claim toDelete = new Claim(validClaimId, validClientId, validPolicyId, validClaimAmount,
                validClaimDate, validClaimMessage);
        ModelStubWithClaim modelStub = new ModelStubWithClaim(toDelete);
        DeleteClaimCommand deleteClaimCommand = new DeleteClaimCommand(
                validClientId, validPolicyId, validClaimId, validClaimMessage);

        CommandResult commandResult = deleteClaimCommand.execute(modelStub);

        assertEquals(String.format(DeleteClaimCommand.MESSAGE_SUCCESS,
                        Messages.format(toDelete, 1, validClaimMessage.toString())),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_deleteClaimCommand_invalidClaim() {
        Claim defaultClaim = new ClaimBuilder().build();
        ModelStubWithClaim modelStub = new ModelStubWithClaim(defaultClaim);
        DeleteClaimCommand deleteClaimCommand = new DeleteClaimCommand(
                validClientId, validPolicyId, validClaimId, validClaimMessage);
        assertThrows(ClaimNotFoundException.class, () -> deleteClaimCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        DeleteClaimCommand deleteFirstCommand = new DeleteClaimCommand(
                validClientId, validPolicyId, validClaimId, validClaimMessage);
        DeleteClaimCommand deleteSecondCommand = new DeleteClaimCommand(
                validClientId, validPolicyId, new ClaimId("CL002"), validClaimMessage);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteClaimCommand deleteFirstCommandCopy = new DeleteClaimCommand(
                validClientId, validPolicyId, validClaimId, validClaimMessage);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(deleteFirstCommand, 1);

        // null -> returns false
        assertNotEquals(deleteFirstCommand, null);

        // different clientId -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        DeleteClaimCommand deleteFirstCommand = new DeleteClaimCommand(
                validClientId, validPolicyId, validClaimId, validClaimMessage);
        DeleteClaimCommand deleteFirstCommandCopy = new DeleteClaimCommand(
                validClientId, validPolicyId, validClaimId, validClaimMessage);
        assertEquals(deleteFirstCommand.hashCode(), deleteFirstCommandCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        DeleteClaimCommand deleteClaimCommand = new DeleteClaimCommand(
                validClientId, validPolicyId, validClaimId, validClaimMessage);
        String expected = "DeleteClaimCommand: ClientId=" + validClientId
                + ", PolicyId=" + validPolicyId + ", ClaimId=" + validClaimId;
        assertEquals(expected, deleteClaimCommand.toString());
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
     * A Model stub that contains a single claim.
     */
    private class ModelStubWithClaim extends ModelStub {
        private final Claim claim;

        ModelStubWithClaim(Claim claim) {
            this.claim = claim;
        }

        @Override
        public Claim deleteClaim(ClientId clientId, PolicyId policyId, ClaimId claimId) {
            if (claim.getClaimId().equals(claimId)
                    && claim.getPolicyId().equals(policyId)
                    && claim.getClientId().equals(clientId)) {
                return claim;
            } else {
                throw new ClaimNotFoundException();
            }
        }
    }
}
