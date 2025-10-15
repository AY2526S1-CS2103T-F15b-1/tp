package insurabook.testutil;

import java.util.HashSet;
import java.util.Set;

import insurabook.model.client.Address;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Email;
import insurabook.model.client.Name;
import insurabook.model.client.Phone;
import insurabook.model.tag.Tag;
import insurabook.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_CLIENT_ID = "1";

    private Name name;
    private ClientId clientId;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        clientId = new ClientId(DEFAULT_CLIENT_ID);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        clientId = clientToCopy.getClientId();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withClientId(String clientId) {
        this.clientId = new ClientId(clientId);
        return this;
    }

    public Client build() {
        return new Client(name, clientId);
    }

}
