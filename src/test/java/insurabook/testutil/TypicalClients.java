package insurabook.testutil;

import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import insurabook.model.AddressBook;
import insurabook.model.client.Client;
import insurabook.model.client.Portfolio;


/**
 * A utility class containing a list of {@code Client} objects to be used in tests.
 */
public class TypicalClients {

    public static final Client ALICE = new ClientBuilder()
            .withName("Alice Pauline")
            .withClientId("1")
            .withPortfolio(new Portfolio()).build();
    public static final Client BENSON = new ClientBuilder()
            .withName("Benson Meier")
            .withClientId("2")
            .withPortfolio(new Portfolio()).build();
    public static final Client CARL = new ClientBuilder()
            .withName("Carl Kurz")
            .withClientId("3")
            .withPortfolio(new Portfolio()).build();
    public static final Client DANIEL = new ClientBuilder()
            .withName("Daniel Meier")
            .withClientId("4")
            .withPortfolio(new Portfolio()).build();
    public static final Client ELLE = new ClientBuilder()
            .withName("Elle Meyer")
            .withClientId("5")
            .withPortfolio(new Portfolio()).build();
    public static final Client FIONA = new ClientBuilder()
            .withName("Fiona Kunz")
            .withClientId("6")
            .withPortfolio(new Portfolio()).build();
    public static final Client GEORGE = new ClientBuilder()
            .withName("George Best")
            .withClientId("7")
            .withPortfolio(new Portfolio()).build();

    // Manually added
    public static final Client HOON = new ClientBuilder()
            .withName("Hoon Meier")
            .withClientId("8")
            .withPortfolio(new Portfolio()).build();
    public static final Client IDA = new ClientBuilder()
            .withName("Ida Mueller")
            .withClientId("9")
            .withPortfolio(new Portfolio()).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Client AMY = new ClientBuilder()
            .withName(VALID_NAME_AMY)
            .withClientId(VALID_CLIENT_ID_AMY)
            .withPortfolio(new Portfolio()).build();
    public static final Client BOB = new ClientBuilder()
            .withName(VALID_NAME_BOB)
            .withClientId(VALID_CLIENT_ID_BOB)
            .withPortfolio(new Portfolio()).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalClients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Client client : getTypicalClients()) {
            ab.addClient(client);
        }
        return ab;
    }

    public static List<Client> getTypicalClients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
