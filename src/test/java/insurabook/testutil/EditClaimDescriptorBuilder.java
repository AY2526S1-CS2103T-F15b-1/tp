package insurabook.testutil;

import insurabook.logic.commands.EditClaimCommand.EditClaimDescriptor;
import insurabook.model.claims.Claim;
import insurabook.model.claims.ClaimAmount;
import insurabook.model.claims.ClaimMessage;
import insurabook.model.claims.InsuraDate;

/**
 * A utility class to help with building EditClaimDescriptor objects.
 */
public class EditClaimDescriptorBuilder {

    private EditClaimDescriptor descriptor;

    public EditClaimDescriptorBuilder() {
        descriptor = new EditClaimDescriptor();
    }

    public EditClaimDescriptorBuilder(EditClaimDescriptor descriptor) {
        this.descriptor = new EditClaimDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditClaimDescriptor} with fields containing {@code claim}'s details
     */
    public EditClaimDescriptorBuilder(Claim claim) {
        descriptor = new EditClaimDescriptor();
        descriptor.setAmount(claim.getAmount());
    }

    /**
     * Sets the {@code ClaimAmount} of the {@code EditClaimDescriptor} that we are building.
     */
    public EditClaimDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new ClaimAmount(amount));
        return this;
    }

    /**
     * Sets the {@code InsuraDate} of the {@code EditClaimDescriptor} that we are building
     */
    public EditClaimDescriptorBuilder withDate(String date) {
        descriptor.setDate(new InsuraDate(date));
        return this;
    }

    /**
     * Sets the {@code ClaimMessage} of the {@code EditClaimDescriptor} that we are building
     */
    public EditClaimDescriptorBuilder withDescription(String msg) {
        descriptor.setDescription(new ClaimMessage(msg));
        return this;
    }

    public EditClaimDescriptor build() {
        return descriptor;
    }

}

