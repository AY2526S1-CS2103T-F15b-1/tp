package insurabook.model.policies;

import static insurabook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import insurabook.model.policies.exceptions.DuplicatePolicyException;
import insurabook.model.policies.exceptions.PolicyNotFoundException;
import insurabook.model.policytype.PolicyType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of policies that enforces uniqueness between its elements and does not allow nulls.
 * A policy is considered unique by comparing using {@code Policy#isSamePolicy(Policy)}. As such, adding and updating of
 * policies uses Policy#isSamePolicy(Policy) for equality so as to ensure that the policy being added or updated is
 * unique in terms of identity in the UniquePolicyList. However, the removal of a policy uses Policy#equals(Object) so
 * as to ensure that the policy with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Policy#isSamePolicy(Policy)
 */
public class UniquePolicyList implements Iterable<Policy> {
    private final ObservableList<Policy> internalList = FXCollections.observableArrayList();
    private final ObservableList<Policy> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public UniquePolicyList() {}

    /**
     * Creates a UniquePolicyList using the Policies in the {@code policies} list.
     *
     * @param policies
     * @throws DuplicatePolicyException if there are any duplicate policies in the given list.
     */
    public UniquePolicyList(List<Policy> policies) {
        if (!policiesAreUnique(policies)) {
            throw new DuplicatePolicyException();
        }
        internalList.addAll(policies);
    }

    /**
     * Replaces the contents of this list with {@code policies}.
     */
    public void setPolicies(List<Policy> policies) {
        requireAllNonNull(policies);
        internalList.setAll(policies);
    }

    /**
     * Returns true if the list contains an equivalent policy as the given argument.
     *
     * @param toCheck
     * @return
     */
    public boolean contains(Policy toCheck) {
        return internalList.stream().anyMatch(toCheck::isSamePolicy);
    }

    /**
     * Adds a policy to the list.
     * The policy must not already exist in the list.
     *
     * @param toAdd
     */
    public void add(Policy toAdd) {
        if (contains(toAdd)) {
            throw new DuplicatePolicyException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent policy from the list.
     * The policy must exist in the list.
     *
     * @param toRemove
     */
    public void remove(Policy toRemove) {
        if (!internalList.remove(toRemove)) {
            throw new PolicyNotFoundException();
        }
    }

    /**
     * Replaces the policy {@code target} in the list with {@code editedPolicy}.
     * {@code target} must exist in the list.
     * The policy identity of {@code editedPolicy} must not be the same as another existing policy in the list.
     *
     * @param target
     * @param editedPolicy
     */
    public void setPolicy(Policy target, Policy editedPolicy) {
        requireAllNonNull(target, editedPolicy);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PolicyNotFoundException();
        }

        if (!target.isSamePolicy(editedPolicy) && contains(editedPolicy)) {
            throw new DuplicatePolicyException();
        }

        internalList.set(index, editedPolicy);
    }

    /**
     * Replaces all policies of a certain PolicyType {@code targetType} in the list with {@code editedType}.
     *
     * @param targetType
     * @param editedType
     */
    public void setPolicyType(PolicyType targetType, PolicyType editedType) {
        requireAllNonNull(targetType, editedType);

        for (int i = 0; i < internalList.size(); i++) {
            Policy policy = internalList.get(i);
            if (policy.getPolicyType().equals(targetType)) {
                Policy updatedPolicy = new Policy(
                        policy.getPolicyId(),
                        policy.getClientId(),
                        editedType,
                        policy.getExpiryDate(),
                        policy.getClaims()
                );
                internalList.set(i, updatedPolicy);
            }
        }
    }

    public ObservableList<Policy> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Policy> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniquePolicyList)) {
            return false;
        }

        UniquePolicyList otherUniquePolicyList = (UniquePolicyList) other;
        return internalList.equals(otherUniquePolicyList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code policies} contains only unique persons.
     */
    private boolean policiesAreUnique(List<Policy> policies) {
        for (int i = 0; i < policies.size() - 1; i++) {
            for (int j = i + 1; j < policies.size(); j++) {
                if (policies.get(i).isSamePolicy(policies.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets a policy from the list using its PolicyId
     *
     * @param policyId
     * @return Policy
     * @throws PolicyNotFoundException if no such policy could be found
     */
    public Policy getPolicy(PolicyId policyId) {
        return internalList.stream()
                .filter(policy -> policy.getPolicyId().equals(policyId))
                .findFirst()
                .orElseThrow(PolicyNotFoundException::new);
    }
}
