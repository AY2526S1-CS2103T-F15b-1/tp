package insurabook.testutil;

import insurabook.model.InsuraBook;
import insurabook.model.client.Client;

/**
 * A utility class to help with building book objects.
 * Example usage: <br>
 *     {@code InsuraBook ab = new InsuraBookBuilder().withClient("John", "Doe").build();}
 */
public class InsuraBookBuilder {

    private InsuraBook insuraBook;

    public InsuraBookBuilder() {
        insuraBook = new InsuraBook();
    }

    public InsuraBookBuilder(InsuraBook insuraBook) {
        this.insuraBook = insuraBook;
    }

    /**
     * Adds a new {@code Client} to the {@code InsuraBook} that we are building.
     */
    public InsuraBookBuilder withClient(Client client) {
        insuraBook.addClient(client);
        return this;
    }

    public InsuraBook build() {
        return insuraBook;
    }
}
