package insurabook.testutil;

import static insurabook.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static insurabook.logic.parser.CliSyntax.PREFIX_NAME;

import insurabook.logic.commands.AddClientCommand;
import insurabook.logic.commands.EditCommand.EditClientDescriptor;
import insurabook.model.client.Client;

/**
 * A utility class for Person.
 */
public class ClientUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddClientCommand(Client client) {
        return AddClientCommand.COMMAND_WORD + " " + getClientDetails(client);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getClientDetails(Client client) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + client.getName().fullName + " ");
        sb.append(PREFIX_CLIENT_ID + client.getId().clientId + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditClientDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getClientId().ifPresent(clientId ->
                sb.append(PREFIX_CLIENT_ID).append(clientId.clientId).append(" "));
        return sb.toString();
    }
}
