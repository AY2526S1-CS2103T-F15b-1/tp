package insurabook.logic.commands;

import static insurabook.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
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
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.testutil.PolicyTypeBuilder;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class AddPolicyTypeCommandTest {

    @Test
    public void constructor_null_throwsNullPointer() {
        assertThrows(NullPointerException.class, () ->
                new AddPolicyTypeCommand(null, null, null, null));
    }

    @Test
    public void execute_policyTypeAcceptedByModel_addSuccessful() throws CommandException {
        ModelStubAcceptingPolicyTypeAdded acceptingModel = new ModelStubAcceptingPolicyTypeAdded();
        PolicyType validPolicyType = new PolicyTypeBuilder().build();

        CommandResult commandResult = new AddPolicyTypeCommand(
                validPolicyType.getPtName(),
                validPolicyType.getPtId(),
                validPolicyType.getPtDescription(),
                validPolicyType.getPtPremium()).execute(acceptingModel);

        assertEquals(String.format(AddPolicyTypeCommand.MESSAGE_SUCCESS, Messages.format(validPolicyType, 0)),
                commandResult.getFeedbackToUser());
        assertEquals(List.of(validPolicyType), acceptingModel.policyTypesAdded);
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
     * A Model stub that contains a single PolicyType.
     */
    private class ModelStubWithPolicyType extends ModelStub {
        private final PolicyType policyType;

        ModelStubWithPolicyType(PolicyType policyType) {
            requireNonNull(policyType);
            this.policyType = policyType;
        }

        public boolean hasPolicyType(PolicyType policyType) {
            requireNonNull(policyType);
            return this.policyType.equals(policyType);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPolicyTypeAdded extends ModelStub {
        final ArrayList<PolicyType> policyTypesAdded = new ArrayList<>();

        public boolean hasPolicyType(PolicyType policyType) {
            requireNonNull(policyType);
            return policyTypesAdded.stream().anyMatch(policyType::equals);
        }

        @Override
        public void addPolicyType(PolicyType policyType) {
            requireNonNull(policyType);
            policyTypesAdded.add(policyType);
        }

        @Override
        public ReadOnlyInsuraBook getInsuraBook() {
            return null;
        }
    }
}
