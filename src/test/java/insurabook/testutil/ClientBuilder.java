package insurabook.testutil;

import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;
import insurabook.model.client.Portfolio;

/**
 * A utility class to help with building Client objects.
 */
public class ClientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_CLIENT_ID = "11111111";

    private Name name;
    private ClientId clientId;
    private Portfolio portfolio;

    /**
     * Creates a {@code ClientBuilder} with the default details.
     */
    public ClientBuilder() {
        name = new Name(DEFAULT_NAME);
        clientId = new ClientId(DEFAULT_CLIENT_ID);
        portfolio = new Portfolio();
    }

    /**
     * Initializes the ClientBuilder with the data of {@code clientToCopy}.
     */
    public ClientBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        clientId = clientToCopy.getId();
        portfolio = clientToCopy.getPortfolio();
    }

    /**
     * Sets the {@code Name} of the {@code Client} that we are building.
     */
    public ClientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code ClientId} of the {@code Client} that we are building.
     */
    public ClientBuilder withClientId(String clientId) {
        this.clientId = new ClientId(clientId);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public ClientBuilder withPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
        return this;
    }

    public Client build() {
        return new Client(name, clientId, portfolio);
    }

}
