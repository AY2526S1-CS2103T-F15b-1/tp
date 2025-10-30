package insurabook.testutil;

import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Email;
import insurabook.model.client.Name;
import insurabook.model.client.Phone;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "91111111";
    public static final String DEFAULT_EMAIL = "amybee@example.com";
    public static final String DEFAULT_BIRTHDAY = "1970-01-01";
    public static final String DEFAULT_CLIENT_ID = "1";

    private Name name;
    private Phone phone;
    private Email email;
    private InsuraDate birthday;
    private ClientId clientId;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        birthday = new InsuraDate(DEFAULT_BIRTHDAY);
        clientId = new ClientId(DEFAULT_CLIENT_ID);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        phone = clientToCopy.getPhone();
        email = clientToCopy.getEmail();
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
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
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
        return new Client(name, phone, email, birthday, clientId);
    }

}
