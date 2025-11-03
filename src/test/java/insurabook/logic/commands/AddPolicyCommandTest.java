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

public class AddPolicyCommandTest {
    private final PolicyId validPolicyId = new PolicyId("101");
    private final ClientId validClientId = new ClientId("12345");
    private final PolicyTypeId validPolicyTypeId = new PolicyTypeId("P11");
    private final InsuraDate validExpiryDate = new InsuraDate("2025-10-01");

    @Test
    public void constructor_null_throwsNullPointerException() {
        // Test null policyId
        assertThrows(NullPointerException.class, () ->
            new AddPolicyCommand(null, validClientId, validPolicyTypeId, validExpiryDate));

        //Test null clientId
        assertThrows(NullPointerException.class, () ->
            new AddPolicyCommand(validPolicyId, null, validPolicyTypeId, validExpiryDate));

        // Test null policyTypeId
        assertThrows(NullPointerException.class, () ->
            new AddPolicyCommand(validPolicyId, validClientId, null, validExpiryDate));

        // Test null expiryDate
        assertThrows(NullPointerException.class, () ->
            new AddPolicyCommand(validPolicyId, validClientId, validPolicyTypeId, null));
    }

    @Test
    public void execute_addPolicySuccessful() {
        ModelStubAcceptingPolicyAdded modelStub = new ModelStubAcceptingPolicyAdded();
        AddPolicyCommand addPolicyCommand =
                new AddPolicyCommand(validPolicyId, validClientId, validPolicyTypeId, validExpiryDate);
        Policy toAdd = new Policy(validPolicyId, validClientId, validPolicyTypeId, validExpiryDate);
        CommandResult commandResult = null;
        try {
            commandResult = addPolicyCommand.execute(modelStub);
        } catch (Exception e) {
            assert false : "Execution of addPolicyCommand should not fail.";
        }

        assertEquals(String.format(AddPolicyCommand.MESSAGE_SUCCESS, Messages.format(toAdd, 0)),
                commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.policiesAdded.size());
        assertEquals(validPolicyId, modelStub.policiesAdded.get(0).getPolicyId());
        assertEquals(validClientId, modelStub.policiesAdded.get(0).getClientId());
        assertEquals(validPolicyTypeId, modelStub.policiesAdded.get(0).getPolicyTypeId());
        assertEquals(validExpiryDate, modelStub.policiesAdded.get(0).getExpiryDate());
    }

    @Test
    public void execute_policyTypeMissing_throwsCommandException() {
        ModelStubPolicyTypeMissing modelStub = new ModelStubPolicyTypeMissing();
        AddPolicyCommand addPolicyCommand =
                new AddPolicyCommand(validPolicyId, validClientId, validPolicyTypeId, validExpiryDate);
        assertThrows(CommandException.class, () ->addPolicyCommand.execute(modelStub));
    }

    @Test
    public void execute_clientNotFound_throwsCommandException() {
        ModelStubClientMissing modelStub = new ModelStubClientMissing();
        AddPolicyCommand addPolicyCommand =
                new AddPolicyCommand(validPolicyId, validClientId, validPolicyTypeId, validExpiryDate);
        assertThrows(ClientMissingException.class, () ->addPolicyCommand.execute(modelStub));
    }

    /**
     * A default model stub that have all the methods failing.
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
         * @param name PolicyTypeName to check
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
     * A Model stub that always accept the policy being added.
     */
    private class ModelStubAcceptingPolicyAdded extends ModelStub {
        final ArrayList<Policy> policiesAdded = new ArrayList<>();


        @Override
        public Policy addPolicy(PolicyId policyId, ClientId clientId,
                                PolicyTypeId policyTypeId, InsuraDate expiryDate) {
            Policy policy = new Policy(policyId, clientId, policyTypeId, expiryDate);
            policiesAdded.add(policy);
            return policy;
        }
    }

    /**
     * A Model stub that always throw PolicyTypeMissingException when trying to add a policy.
     */
    private class ModelStubPolicyTypeMissing extends ModelStub {
        @Override
        public Policy addPolicy(PolicyId policyId, ClientId clientId,
                                PolicyTypeId policyTypeId, InsuraDate expiryDate) {
            throw new PolicyTypeMissingException(policyTypeId);
        }
    }

    /**
     * A Model stub that always throw ClientMissingException when trying to add a policy.
     */
    private class ModelStubClientMissing extends ModelStub {
        @Override
        public Policy addPolicy(PolicyId policyId, ClientId clientId,
                                PolicyTypeId policyTypeId, InsuraDate expiryDate) {
            throw new ClientMissingException();
        }
    }
}
