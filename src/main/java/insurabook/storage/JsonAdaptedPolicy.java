package insurabook.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.InsuraBook;
import insurabook.model.claims.Claim;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.policies.Policy;
import insurabook.model.policies.PolicyId;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeId;

/**
 * Jackson-friendly version of {@link Policy}.
 */
public class JsonAdaptedPolicy {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Policy's %s field is missing!";

    private final String policyId;
    private final String clientId;
    private final String policyTypeId;
    private final String expiryDate;
    private final List<JsonAdaptedClaim> claims;

    /**
     * Constructs a {@code JsonAdaptedPolicy} with the given policy details.
     */
    public JsonAdaptedPolicy(@JsonProperty("policyId") String policyId,
                             @JsonProperty("clientId") String clientId,
                             @JsonProperty("policyTypeId") String policyTypeId,
                            @JsonProperty("expiryDate") String expiryDate,
                            @JsonProperty("claims") List<JsonAdaptedClaim> claims) {
        this.clientId = clientId;
        this.policyId = policyId;
        this.policyTypeId = policyTypeId;
        this.expiryDate = expiryDate;
        this.claims = claims;
    }

    /**
     * Converts a given {@code Policy} into this class for Jackson use.
     */
    public JsonAdaptedPolicy(Policy policy) {
        clientId = policy.getClientId().toString();
        policyId = policy.getPolicyId().toString();
        policyTypeId = policy.getPolicyTypeId().toString();
        expiryDate = policy.getExpiryDate().toString();
        claims = policy.getClaims().stream()
                .map(JsonAdaptedClaim::new)
                .toList();
    }

    /**
     * Converts this Jackson-friendly adapted policy object into the model's {@code Policy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted policy.
     */
    public Policy toModelType(InsuraBook insuraBook) throws IllegalValueException {
        if (clientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "clientId"));
        }
        if (!ClientId.isValidClientId(clientId)) {
            throw new IllegalValueException(ClientId.MESSAGE_CONSTRAINTS);
        }
        final ClientId modelClientId = new ClientId(clientId);
        final Client modelClient = insuraBook.getClient(modelClientId);
        if (modelClient == null) {
            throw new IllegalValueException("Client with ID " + clientId + " not found in InsuraBook");
        }

        if (policyId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "policyId"));
        }
        if (!PolicyId.isValidPolicyId(policyId)) {
            throw new IllegalValueException(PolicyId.MESSAGE_CONSTRAINTS);
        }
        final PolicyId modelPolicyId = new PolicyId(policyId);

        if (policyTypeId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "policyTypeId"));
        }
        if (!PolicyTypeId.isValidPtId(policyTypeId)) {
            throw new IllegalValueException(PolicyTypeId.MESSAGE_CONSTRAINTS);
        }
        final PolicyTypeId modelPolicyTypeId = new PolicyTypeId(policyTypeId);
        final PolicyType modelPolicyType = insuraBook.getPolicyType(modelPolicyTypeId);
        if (modelPolicyType == null) {
            throw new IllegalValueException("PolicyType with ID " + policyTypeId + " not found in InsuraBook");
        }

        if (expiryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "expiry date"));
        }
        if (!InsuraDate.isValidInsuraDate(expiryDate)) {
            throw new IllegalValueException(InsuraDate.MESSAGE_CONSTRAINTS);
        }
        final InsuraDate modelDate = new InsuraDate(expiryDate);

        if (claims == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "claims"));
        }

        List<Claim> modelClaims = new ArrayList<>();
        for (JsonAdaptedClaim jsonClaim : claims) {
            modelClaims.add(jsonClaim.toModelType());
        }

        return new Policy(modelPolicyId, modelClient, modelPolicyType, modelDate, modelClaims);
    }
}
