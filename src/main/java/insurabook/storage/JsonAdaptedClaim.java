package insurabook.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.ClaimId;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.claims.InsuraDate;
import insurabook.model.client.ClientId;
import insurabook.model.policies.PolicyId;

/**
 * Jackson-friendly version of {@link Claim}.
 */
public class JsonAdaptedClaim {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Claim's %s field is missing!";

    private final String clientId;
    private final String policyId;
    private final String claimId;
    private final String amount;
    private final String date;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedClaim} with the given claim details.
     */
    public JsonAdaptedClaim(@JsonProperty("clientId") String clientId,
                            @JsonProperty("policyId") String policyId,
                            @JsonProperty("claimId") String claimId,
                            @JsonProperty("amount") String amount,
                            @JsonProperty("date") String date,
                            @JsonProperty("description") String description) {
        this.clientId = clientId;
        this.policyId = policyId;
        this.claimId = claimId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    /**
     * Converts a given {@code Claim} into this class for Jackson use.
     */
    public JsonAdaptedClaim(Claim claim) {
        clientId = claim.getClientId().toString();
        policyId = claim.getPolicyId().toString();
        claimId = claim.getClaimId().toString();
        amount = claim.getAmount().toString();
        date = claim.getDate().toString();
        description = claim.getDescription().toString();
    }

    /**
     * Converts this Jackson-friendly adapted claim object into the model's {@code Claim} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted claim.
     */
    public Claim toModelType() throws IllegalValueException {
        if (clientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "clientId"));
        }
        if (!ClientId.isValidClientId(clientId)) {
            throw new IllegalValueException(ClientId.MESSAGE_CONSTRAINTS);
        }
        final ClientId modelClientId = new ClientId(clientId);

        if (policyId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "policyId"));
        }
        if (!PolicyId.isValidPolicyId(policyId)) {
            throw new IllegalValueException(PolicyId.MESSAGE_CONSTRAINTS);
        }
        final PolicyId modelPolicyId = new PolicyId(policyId);

        if (claimId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "id"));
        }
        if (!ClaimId.isValidClaimId(claimId)) {
            throw new IllegalValueException(ClaimId.MESSAGE_CONSTRAINTS);
        }
        final ClaimId modelClaimId = new ClaimId(claimId);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "amount"));
        }
        if (!ClaimAmount.isValidClaimAmount(amount)) {
            throw new IllegalValueException(ClaimAmount.MESSAGE_CONSTRAINTS);
        }
        final ClaimAmount modelAmount = new ClaimAmount(amount);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }
        if (!InsuraDate.isValidInsuraDate(date)) {
            throw new IllegalValueException(InsuraDate.MESSAGE_CONSTRAINTS);
        }
        final InsuraDate modelDate = new InsuraDate(date);

        final ClaimMessage modelDescription = new ClaimMessage(description != null ? description : "");

        return new Claim(modelClientId, modelPolicyId, modelAmount, modelDate, modelDescription);
    }
}
