package insurabook.model.client;

import static insurabook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import insurabook.commons.util.ToStringBuilder;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.InsuraDate;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;

/**
 * Represents a Client in InsuraBook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    // Identity fields
    private final ClientId clientId;
    private final Name name;
    private final InsuraDate birthday;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Portfolio portfolio;

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, InsuraDate birthday, ClientId clientId) {
        requireAllNonNull(name, birthday, clientId);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.clientId = clientId;
        this.portfolio = new Portfolio();
    }

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, InsuraDate birthday, ClientId clientId, List<Policy> policies) {
        requireAllNonNull(name, birthday, clientId, policies);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.clientId = clientId;
        this.portfolio = new Portfolio(policies);
    }

    /**
     * Copy constructor.
     */
    public Client(Client toCopy) {
        requireAllNonNull(toCopy);
        this.name = toCopy.getName();
        this.phone = toCopy.getPhone();
        this.email = toCopy.getEmail();
        this.birthday = toCopy.getBirthday();
        this.clientId = toCopy.getClientId();
        this.portfolio = new Portfolio(toCopy.getPortfolio());
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public InsuraDate getBirthday() {
        return birthday;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    /**
     * Returns true if both clients have the same name.
     * This defines a weaker notion of equality between two clients.
     */
    public boolean isSameClient(Client otherClient) {
        if (otherClient == this) {
            return true;
        }

        return otherClient != null
                && otherClient.getClientId().equals(getClientId());
    }

    /**
     * Returns true if both clients have the same identity and data fields.
     * This defines a stronger notion of equality between two clients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Client)) {
            return false;
        }

        Client otherClient = (Client) other;
        return clientId.clientId.equals(otherClient.clientId.clientId);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, clientId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("clientId", clientId)
                .add("phone", phone)
                .add("email", email)
                .toString();
    }

    public void addPolicy(Policy policy) {
        portfolio.addPolicy(policy);
    }

    public Policy removePolicy(PolicyId policyId) {
        return portfolio.removePolicy(policyId);
    }

    /**
     * Sets this Client's Portfolio to contain deep copies of all Policies
     * from a given Portfolio.
     *
     * @param portfolio given Portfolio to import from
     */
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio.importFrom(portfolio);
    }

    public void setPolicy(Policy target, Policy editedPolicy) {
        portfolio.setPolicy(target, editedPolicy);
    }

    /**
     * Adds a claim to the client's portfolio.
     *
     * @param claim
     */
    public void addClaim(Claim claim) {
        portfolio.addClaim(claim);
    }

    /**
     * Removes a claim from the client's portfolio.
     */
    public Claim removeClaim(PolicyId policyId, ClaimId claimId) {
        return portfolio.removeClaim(policyId, claimId);
    }

    /**
     * Replaces the given claim {@code target} with {@code editedClaim}.
     * {@code target} must exist in the client's portfolio.
     * The claim identity of {@code editedClaim} must not be the same as
     * another existing claim in the client's portfolio.
     */
    public void setClaim(Claim target, Claim editedClaim) {
        portfolio.setClaim(target, editedClaim);
    }
}
