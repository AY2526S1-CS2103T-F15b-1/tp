package insurabook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.client.Client;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeDescription;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.PolicyTypePremium;

/**
 * Jackson-friendly version of {@link Client}.
 */
class JsonAdaptedPolicyType {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String ptName;
    private final String ptId;
    private final String ptDescription;
    private final String ptPremium;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPolicyType(@JsonProperty("ptName") String ptName, @JsonProperty("ptId") String ptId,
                                 @JsonProperty("ptDescription") String ptDescription,
                                 @JsonProperty("ptPremium") String ptPremium) {
        this.ptName = ptName;
        this.ptId = ptId;
        this.ptDescription = ptDescription;
        this.ptPremium = ptPremium;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPolicyType(PolicyType source) {
        ptName = source.getPtName().toString();
        ptId = source.getPtId().toString();
        ptDescription = source.getPtDescription().toString();
        ptPremium = source.getPtPremium().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public PolicyType toModelType() throws IllegalValueException {
        if (ptName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        final PolicyTypeName modelPtName = new PolicyTypeName(ptName);

        if (ptId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        final PolicyTypeId modelPtId = new PolicyTypeId(ptId);

        if (ptDescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    String.class.getSimpleName()));
        }
        final PolicyTypeDescription modelPtDescription = new PolicyTypeDescription(ptDescription);

        if (ptPremium == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Integer.class.getSimpleName()));
        }
        final PolicyTypePremium modelPtPremium = new PolicyTypePremium(ptPremium);

        return new PolicyType(modelPtName, modelPtId, modelPtDescription, modelPtPremium);
    }

}
