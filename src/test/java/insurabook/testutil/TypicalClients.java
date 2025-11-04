package insurabook.testutil;

import static insurabook.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_CLIENT_ID_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static insurabook.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static insurabook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import insurabook.model.InsuraBook;
import insurabook.model.client.Client;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalClients {

    public static final Client ALICE = new PersonBuilder()
            .withName("Alice Pauline")
            .withPhone("81112222")
            .withEmail("alice@example.com")
            .withBirthday("2001-01-01")
            .withClientId("A1").build();
    public static final Client BENSON = new PersonBuilder()
            .withName("Benson Meier")
            .withPhone("92223333")
            .withEmail("benson@example.com")
            .withBirthday("2002-02-02")
            .withClientId("B2").build();
    public static final Client CARL = new PersonBuilder()
            .withName("Carl Kurz")
            .withPhone("83334444")
            .withEmail("carl@example.com")
            .withBirthday("1993-09-07")
            .withClientId("C3").build();
    public static final Client DANIEL = new PersonBuilder()
            .withName("Daniel Meier")
            .withPhone("94445555")
            .withEmail("daniel@example.com")
            .withBirthday("1999-12-31")
            .withClientId("D4").build();
    public static final Client ELLE = new PersonBuilder()
            .withName("Elle Meyer")
            .withPhone("85556666")
            .withEmail("elle@example.com")
            .withBirthday("2005-11-22")
            .withClientId("E5").build();
    public static final Client FIONA = new PersonBuilder()
            .withName("Fiona Kunz")
            .withPhone("96667777")
            .withEmail("fiona@example.com")
            .withBirthday("1997-08-09")
            .withClientId("F6").build();
    public static final Client GEORGE = new PersonBuilder()
            .withName("George Best")
            .withPhone("87778888")
            .withEmail("george@example.com")
            .withBirthday("2001-09-11")
            .withClientId("G7").build();

    // Manually added
    public static final Client HOON = new PersonBuilder()
            .withName("Hoon Meier")
            .withPhone("91234567")
            .withEmail("hoon@example.com")
            .withBirthday("1995-04-25")
            .withClientId("D8").build();
    public static final Client IDA = new PersonBuilder()
            .withName("Ida Mueller")
            .withPhone("97654321")
            .withEmail("ida@example.com")
            .withBirthday("1980-07-06")
            .withClientId("F9").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Client AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withBirthday(VALID_BIRTHDAY_AMY)
            .withClientId(VALID_CLIENT_ID_AMY).build();
    public static final Client BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB)
            .withClientId(VALID_CLIENT_ID_BOB).build();



    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalClients() {} // prevents instantiation

    /**
     * Returns an {@code InsuraBook} with all the typical clients.
     */
    public static InsuraBook getTypicalInsuraBook() {
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
