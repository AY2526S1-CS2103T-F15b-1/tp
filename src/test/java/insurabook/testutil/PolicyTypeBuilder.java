package insurabook.testutil;

import insurabook.model.policytype.PolicyType;
import insurabook.model.policytype.PolicyTypeDescription;
import insurabook.model.policytype.PolicyTypeId;
import insurabook.model.policytype.PolicyTypeName;
import insurabook.model.policytype.PolicyTypePremium;

/**
 * A utility class to help with building PolicyType objects.
 */
public class PolicyTypeBuilder {

    public static final String DEFAULT_NAME = "PruDefault";
    public static final String DEFAULT_ID = "PRUDef1";
    public static final String DEFAULT_DESCRIPTION = "Default description.";
    public static final String DEFAULT_PREMIUM = "1000";

    private PolicyTypeName name;
    private PolicyTypeId id;
    private PolicyTypeDescription description;
    private PolicyTypePremium premium;

    /**
     * Creates a default {@code PolicyTypeBuilder}.
     */
    public PolicyTypeBuilder() {
        this.name = new PolicyTypeName(DEFAULT_NAME);
        this.id = new PolicyTypeId(DEFAULT_ID);
        this.description = new PolicyTypeDescription(DEFAULT_DESCRIPTION);
        this.premium = new PolicyTypePremium(DEFAULT_PREMIUM);
    }

    /**
     * Initializes a PolicyTypeBuilder with values of given {@code toCopy}.
     */
    public PolicyTypeBuilder(PolicyType toCopy) {
        this.name = toCopy.getPtName();
        this.id = toCopy.getPtId();
        this.description = toCopy.getPtDescription();
        this.premium = toCopy.getPtPremium();
    }

    /**
     * Sets the {@code PolicyTypeName} of the {@code PolicyType} we are building.
     */
    public PolicyTypeBuilder withName(String name) {
        this.name = new PolicyTypeName(name);
        return this;
    }

    /**
     * Sets the {@code PolicyTypeId} of the {@code PolicyType} we are building.
     */
    public PolicyTypeBuilder withId(String id) {
        this.id = new PolicyTypeId(id);
        return this;
    }

    /**
     * Sets the {@code PolicyTypeDescription} of the {@code PolicyType} we are building.
     */
    public PolicyTypeBuilder withDescription(String description) {
        this.description = new PolicyTypeDescription(description);
        return this;
    }

    /**
     * Sets {@code PolicyTypeDescription} to empty.
     */
    public PolicyTypeBuilder withoutDescription() {
        this.description = new PolicyTypeDescription();
        return this;
    }

    /**
     * Sets the {@code PolicyTypePremium} of the {@code PolicyType} we are building.
     * Sets {@code hasPremium} to true.
     */
    public PolicyTypeBuilder withPremium(String premium) {
        this.premium = new PolicyTypePremium(premium);
        return this;
    }

    /**
     * Sets the {@code PolicyTypePremium} to empty.
     */
    public PolicyTypeBuilder withoutPremium() {
        this.premium = new PolicyTypePremium();
        return this;
    }

    /**
     * Builds the {@code PolicyType}.
     */
    public PolicyType build() {
        return new PolicyType(name, id, description, premium);
    }
}
