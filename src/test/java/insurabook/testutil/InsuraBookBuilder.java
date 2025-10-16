package insurabook.testutil;

import insurabook.model.InsuraBook;
import insurabook.model.client.Client;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
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
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public InsuraBookBuilder withPerson(Client client) {
        insuraBook.addClient(client);
        return this;
    }

    public InsuraBook build() {
        return insuraBook;
    }
}
