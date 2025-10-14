package insurabook.model.client;

import static insurabook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import insurabook.commons.util.ToStringBuilder;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimId;
import insurabook.model.policies.PolicyId;
import insurabook.model.tag.Tag;

/**
 * Represents a Client in InsuraBook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    // Identity fields
    private final ClientId clientId;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Portfolio portfolio;

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  ClientId clientId) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.clientId = clientId;
        this.portfolio = new Portfolio();
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

    public Address getAddress() {
        return address;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Client otherClient) {
        if (otherClient == this) {
            return true;
        }

        return otherClient != null
                && otherClient.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("clientId", clientId)
                .toString();
    }

    /**
     * Adds a claim to the client's portfolio.
     *
     * @param claim
     */
    public void addClaim(Claim claim) {
        portfolio.addClaim(claim);
    }

    public Claim removeClaim(PolicyId policyId, ClaimId claimId) {
        return portfolio.removeClaim(policyId, claimId);
    }
}
