package insurabook.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.InsuraBook;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;
import insurabook.model.policies.Policy;

/**
 * Jackson-friendly version of {@link Client}.
 */
class JsonAdaptedClient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String birthday;
    private final String clientId;
    private final List<JsonAdaptedPolicy> policies;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedClient(@JsonProperty("name") String name,
                             @JsonProperty("birthday") String birthday,
                             @JsonProperty("clientId") String clientId,
                             @JsonProperty("policies") List<JsonAdaptedPolicy> policies) {
        this.name = name;
        this.birthday = birthday;
        this.clientId = clientId;
        this.policies = policies;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedClient(Client source) {
        name = source.getName().fullName;
        birthday = source.getBirthday().toString();
        clientId = source.getClientId().clientId;
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
        final List<Policy> modelPolicies = policies.stream()
                .map(jsonAdaptedPolicy -> {
                    try {
                        return jsonAdaptedPolicy.toModelType(insuraBook);
                    } catch (IllegalValueException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        return new Client(modelName, modelBirthday, modelClientId, modelPolicies);
    }

    public Client toModelTypeWithoutPolicies() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

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

        return new Client(modelName, modelBirthday, modelClientId);
    }

    public ClientId getClientId() {
        return new ClientId(clientId);
    }

    public List<JsonAdaptedPolicy> getPolicies() {
        return policies;
    }

}
