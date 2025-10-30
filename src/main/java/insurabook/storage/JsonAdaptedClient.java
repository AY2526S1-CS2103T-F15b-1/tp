package insurabook.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.InsuraBook;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Email;
import insurabook.model.client.Name;
import insurabook.model.client.Phone;
import insurabook.model.policies.Policy;

/**
 * Jackson-friendly version of {@link Client}.
 */
class JsonAdaptedClient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Client's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String birthday;
    private final String clientId;
    private final List<JsonAdaptedPolicy> policies;

    /**
     * Constructs a {@code JsonAdaptedClient} with the given client details.
     */
    @JsonCreator
    public JsonAdaptedClient(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("birthday") String birthday,
                             @JsonProperty("clientId") String clientId,
                             @JsonProperty("policies") List<JsonAdaptedPolicy> policies) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birthday = birthday;
        this.clientId = clientId;
        this.policies = policies;
    }

    /**
     * Converts a given {@code Client} into this class for Jackson use.
     */
    public JsonAdaptedClient(Client source) {
        name = source.getName().toString();
        phone = source.getPhone().toString();
        email = source.getEmail().toString();
        birthday = source.getBirthday().toString();
        clientId = source.getClientId().toString();
        policies = source.getPortfolio().getPolicies().asUnmodifiableObservableList().stream()
                .map(JsonAdaptedPolicy::new)
                .toList();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Client toModelType(InsuraBook insuraBook) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!InsuraDate.isValidInsuraDate(phone)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!InsuraDate.isValidInsuraDate(email)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(phone);

        if (birthday == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, InsuraDate.class.getSimpleName()));
        }
        if (!InsuraDate.isValidInsuraDate(birthday)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final InsuraDate modelBirthday = new InsuraDate(birthday);

        if (clientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClientId.class.getSimpleName()));
        }
        if (!ClientId.isValidClientId(clientId)) {
            throw new IllegalValueException(ClientId.MESSAGE_CONSTRAINTS);
        }
        final ClientId modelClientId = new ClientId(clientId);

        if (policies == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "polices"));
        }
        final List<Policy> modelPolicies = new ArrayList<>();
        for (JsonAdaptedPolicy jsonAdaptedPolicy : policies) {
            modelPolicies.add(convertToPolicy(jsonAdaptedPolicy, insuraBook));
        }

        return new Client(modelName, modelPhone, modelEmail, modelBirthday, modelClientId, modelPolicies);
    }

    private Policy convertToPolicy(JsonAdaptedPolicy jsonAdaptedPolicy, InsuraBook insuraBook)
            throws IllegalValueException {
        return jsonAdaptedPolicy.toModelType(insuraBook);
    }

    public Client toModelTypeWithoutPolicies() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, InsuraDate.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, InsuraDate.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (birthday == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, InsuraDate.class.getSimpleName()));
        }
        if (!InsuraDate.isValidInsuraDate(birthday)) {
            throw new IllegalValueException(InsuraDate.MESSAGE_CONSTRAINTS);
        }
        final InsuraDate modelBirthday = new InsuraDate(birthday);

        if (clientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClientId.class.getSimpleName()));
        }
        if (!ClientId.isValidClientId(clientId)) {
            throw new IllegalValueException(ClientId.MESSAGE_CONSTRAINTS);
        }
        final ClientId modelClientId = new ClientId(clientId);

        return new Client(modelName, modelPhone, modelEmail, modelBirthday, modelClientId);
    }

    public ClientId getClientId() {
        return new ClientId(clientId);
    }

    public List<JsonAdaptedPolicy> getPolicies() {
        return policies;
    }

}
