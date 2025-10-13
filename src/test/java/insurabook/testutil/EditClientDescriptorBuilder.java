package insurabook.testutil;

import insurabook.logic.commands.EditCommand.EditClientDescriptor;
import insurabook.model.client.Client;
import insurabook.model.client.ClientId;
import insurabook.model.client.Name;
import insurabook.model.client.Portfolio;


/**
 * A utility class to help with building EditClientDescriptor objects.
 */
public class EditClientDescriptorBuilder {

    private EditClientDescriptor descriptor;

    public EditClientDescriptorBuilder() {
        descriptor = new EditClientDescriptor();
    }

    public EditClientDescriptorBuilder(EditClientDescriptor descriptor) {
        this.descriptor = new EditClientDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditClientDescriptorBuilder(Client client) {
        descriptor = new EditClientDescriptor();
        descriptor.setName(client.getName());
        descriptor.setClientId(client.getId());
        descriptor.setPortfolio(client.getPortfolio());
    }

    /**
     * Sets the {@code Name} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Client_Id} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withClientId(String clientId) {
        descriptor.setClientId(new ClientId(clientId));
        return this;
    }

    /**
     * Sets the {@code Portfolio} of the {@code EditClientDescriptor} that we are building.
     */
    public EditClientDescriptorBuilder withPortfolio(Portfolio portfolio) {
        descriptor.setPortfolio(portfolio);
        return this;
    }

    public EditClientDescriptor build() {
        return descriptor;
    }
}
