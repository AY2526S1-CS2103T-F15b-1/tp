package insurabook.model.client;

import static insurabook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import insurabook.commons.util.ToStringBuilder;

/**
 * Represents a Client in InsuraBook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    // Identity fields
    private final Name name;
    private final ClientId clientId;

    // Data fields
    private final Portfolio portfolio;

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, ClientId clientId) {
        requireAllNonNull(name, clientId);
        this.name = name;
        this.clientId = clientId;
        this.portfolio = new Portfolio();
    }

    public Name getName() {
        return name;
    }

    public ClientId getId() {
        return clientId;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameClient(Client otherClient) {
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
        return clientId.equals(otherClient.getId());
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
                .toString();
    }

}
