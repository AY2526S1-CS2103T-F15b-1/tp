package insurabook.testutil;

import insurabook.logic.commands.EditCommand.EditPersonDescriptor;
import insurabook.model.client.Client;
import insurabook.model.client.Name;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Client client) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(client.getName());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }

}
