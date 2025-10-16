package insurabook.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import insurabook.commons.exceptions.IllegalValueException;
import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeDescription;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.PolicyTypePremium;

/**
 * Jackson-friendly version of {@link PolicyType}.
 */
class JsonAdaptedPolicyType {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Policy Type's %s field is missing!";

    private final String ptName;
    private final String ptId;

    private final boolean isDescEmpty; // true if PolicyType has no description
    private final String ptDescription; // if isDescriptionEmpty is true, this value is not used

    private final boolean isPremEmpty; // true if PolicyType has no premium
    private final String ptPremium; // if isPremiumEmpty is true, this value is not used

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPolicyType(@JsonProperty("ptName") String ptName, @JsonProperty("ptId") String ptId,
                                 @JsonProperty("isDescEmpty") boolean isDescEmpty,
                                 @JsonProperty("ptDescription") String ptDescription,
                                 @JsonProperty("isPremEmpty") boolean isPremEmpty,
                                 @JsonProperty("ptPremium") String ptPremium) {
        this.ptName = ptName;
        this.ptId = ptId;
        this.isDescEmpty = isDescEmpty;
        this.ptDescription = ptDescription;
        this.isPremEmpty = isPremEmpty;
        this.ptPremium = ptPremium;
    }

    /**
     * Converts a given {@code PolicyType} into this class for Jackson use.
     */
    public JsonAdaptedPolicyType(PolicyType source) {
        ptName = source.getPtName().toString();
        ptId = source.getPtId().toString();
        isDescEmpty = source.getPtDescription().isEmpty;
        ptDescription = source.getPtDescription().toString();
        isPremEmpty = source.getPtPremium().isEmpty;
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
        final PolicyTypeDescription modelPtDescription =
                !isDescEmpty
                        ? new PolicyTypeDescription(ptDescription)
                        : new PolicyTypeDescription();

        if (ptPremium == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Integer.class.getSimpleName()));
        }
        final PolicyTypePremium modelPtPremium =
                !isPremEmpty
                        ? new PolicyTypePremium(ptPremium)
                        : new PolicyTypePremium();

        return new PolicyType(modelPtName, modelPtId, modelPtDescription, modelPtPremium);
    }

}
