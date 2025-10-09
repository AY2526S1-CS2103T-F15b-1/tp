package insurabook.model.client;

import static insurabook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import insurabook.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    // Identity fields
    private final Name name;
    private final ClientId clientId;
    private final Portfolio portfolio;

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, ClientId clientId) {
        requireAllNonNull(name, clientId);
        this.name = name;
        this.clientId = clientId;
        this.portfolio = new Portfolio();
        this.portfolio.setClient(this);
    }

    public Name getName() {
        return name;
    }

    public ClientId getId() {
        return this.clientId;
    }

    /**
     * Returns true if both clients have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Client otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both clients have the same clientId field.
     * clientId is the primary key of Client
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

        Client otherPerson = (Client) other;
        return clientId.equals(otherPerson.clientId);
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
                .add("id", clientId)
                .toString();
    }

    // PORTFOLIO CALLS

}
