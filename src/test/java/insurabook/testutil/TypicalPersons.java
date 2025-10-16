package insurabook.testutil;

import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import insurabook.model.InsuraBook;
import insurabook.model.client.Client;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Client ALICE = new PersonBuilder()
            .withName("Alice Pauline")
            .withClientId("1").build();
    public static final Client BENSON = new PersonBuilder()
            .withName("Benson Meier")
            .withClientId("2").build();
    public static final Client CARL = new PersonBuilder()
            .withName("Carl Kurz")
            .withClientId("3").build();
    public static final Client DANIEL = new PersonBuilder()
            .withName("Daniel Meier")
            .withClientId("4").build();
    public static final Client ELLE = new PersonBuilder()
            .withName("Elle Meyer")
            .withClientId("5").build();
    public static final Client FIONA = new PersonBuilder()
            .withName("Fiona Kunz")
            .withClientId("6").build();
    public static final Client GEORGE = new PersonBuilder()
            .withName("George Best")
            .withClientId("G7").build();

    // Manually added
    public static final Client HOON = new PersonBuilder()
            .withName("Hoon Meier")
            .withClientId("D8").build();
    public static final Client IDA = new PersonBuilder()
            .withName("Ida Mueller")
            .withClientId("F9").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Client AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY)
            .withClientId(VALID_CLIENT_ID_AMY).build();
    public static final Client BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB)
            .withClientId(VALID_CLIENT_ID_BOB).build();



    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static InsuraBook getTypicalAddressBook() {
        InsuraBook ib = new InsuraBook();
        for (Client client : getTypicalClients()) {
            ib.addClient(client);
        }
        return ib;
    }

    public static List<Client> getTypicalClients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
