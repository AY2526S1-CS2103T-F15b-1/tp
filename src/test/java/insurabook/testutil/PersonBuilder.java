package insurabook.testutil;

import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_BIRTHDAY = "1970-01-01";
    public static final String DEFAULT_CLIENT_ID = "1";

    private Name name;
    private InsuraDate birthday;
    private ClientId clientId;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        birthday = new InsuraDate(DEFAULT_BIRTHDAY);
        clientId = new ClientId(DEFAULT_CLIENT_ID);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        birthday = clientToCopy.getBirthday();
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
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        this.birthday = new InsuraDate(birthday);
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
        return new Client(name, birthday, clientId);
    }

}
