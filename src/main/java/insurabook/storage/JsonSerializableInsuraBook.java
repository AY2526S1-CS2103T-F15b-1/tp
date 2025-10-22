package insurabook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.InsuraBook;
import insurabook.model.ReadOnlyInsuraBook;
import insurabook.model.client.Client;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "insurabook")
class JsonSerializableInsuraBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedClient> clients = new ArrayList<>();
    private final List<JsonAdaptedPolicyType> policyTypes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given clients.
     */
    @JsonCreator
    public JsonSerializableInsuraBook(@JsonProperty("clients") List<JsonAdaptedClient> clients,
                                      @JsonProperty("policyTypes") List<JsonAdaptedPolicyType> policyTypes) {
        this.clients.addAll(clients);
        this.policyTypes.addAll(policyTypes);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableInsuraBook(ReadOnlyInsuraBook source) {
        policyTypes.addAll(source.getPolicyTypeList().stream()
                .map(JsonAdaptedPolicyType::new).collect(Collectors.toList()));
        clients.addAll(source.getClientList().stream().map(JsonAdaptedClient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code InsuraBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public InsuraBook toModelType() throws IllegalValueException {
        InsuraBook insuraBook = new InsuraBook();
        for (JsonAdaptedPolicyType jsonAdaptedPolicyTypes : policyTypes) {
            insuraBook.addPolicyType(jsonAdaptedPolicyTypes.toModelType());
        }

        for (JsonAdaptedClient jsonAdaptedClient : clients) {
            Client client = jsonAdaptedClient.toModelTypeWithoutPolicies();
            if (insuraBook.hasClient(client)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            insuraBook.addClient(client);
        }

        for (JsonAdaptedClient jsonClient : clients) {
            Client client = insuraBook.getClient(jsonClient.getClientId());
            for (JsonAdaptedPolicy jsonPolicy : jsonClient.getPolicies()) {
                client.addPolicy(jsonPolicy.toModelType(insuraBook));
            }
        }
        return insuraBook;
    }

}
